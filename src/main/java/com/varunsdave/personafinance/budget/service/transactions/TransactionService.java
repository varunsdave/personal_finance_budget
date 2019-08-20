package com.varunsdave.personafinance.budget.service.transactions;

import com.varunsdave.personafinance.budget.model.Transaction;
import com.varunsdave.personafinance.budget.model.UiCategory;
import com.varunsdave.personafinance.budget.model.UiTransaction;
import com.varunsdave.personafinance.budget.repository.AccountRepository;
import com.varunsdave.personafinance.budget.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final TransactionProcessorFactory transactionProcessorFactory;

    private final String INCOME = "income";
    private final String EXPENSE = "expense";
    private final String BALANCE = "balance";

    public Transaction create(UiTransaction uiTransaction, String accountId) {
        if (accountRepository.findById(accountId).isEmpty()) {
            throw new InvalidParameterException();
        }
        final Transaction t = convertFromUiTransaction(uiTransaction, accountId);
        // get last transaction from that date
        final List<Transaction> transactions = transactionRepository.findByAccountIdAndTransactionDateIsGreaterThanEqual(accountId, uiTransaction.getTransactionDate());
        final Transaction previousTransactions = transactionRepository.findTopByAccountIdAndTransactionDateIsLessThanEqual(accountId, uiTransaction.getTransactionDate());

        if (previousTransactions == null) {
            // this is the first transaction
            BigDecimal accountBalance = t.getType().equals(INCOME) ? BigDecimal.valueOf(uiTransaction.getAmount()) :
                    t.getType().equals(EXPENSE) ? BigDecimal.valueOf(uiTransaction.getAmount()).multiply(BigDecimal.valueOf(-1.0)) :
                            t.getAmount();
            t.setAccountBalance(accountBalance);
        } else {
            BigDecimal accountBalance = t.getType().equals(INCOME) ? previousTransactions.getAccountBalance().add(BigDecimal.valueOf(uiTransaction.getAmount())) :
                    t.getType().equals(EXPENSE) ? previousTransactions.getAccountBalance().subtract(BigDecimal.valueOf(uiTransaction.getAmount()).multiply(BigDecimal.valueOf(-1.0))) :
                            t.getAmount();
            t.setAccountBalance(accountBalance);
        }

        updateTransactionBalances(t.getAccountBalance(), transactions);
        final List<Transaction> updatedTransactions = new ArrayList<>();
        updatedTransactions.add(t);
        updatedTransactions.addAll(transactions);
        transactionRepository.saveAll(updatedTransactions);
        return t;
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public List<Transaction> getAllTransactionsByAccount(final String accountId) {
        return transactionRepository.findByAccountId(accountId, getTransactionDateDescendingSort());
    }

    public List<Transaction> getTransactionsByType(String type, String accountId) {
        return transactionProcessorFactory.getTransactionProcessorByType(type).getAll(accountId);
    }

    public List<Transaction> updateTransactionCategory(final String accountId, final List<String> transactionIds, final UiCategory category) {
        final List<Transaction> dbTransactions = transactionRepository.findByIdInAndAccountId(transactionIds, accountId);
        dbTransactions.stream().map(dbTransaction ->  {
            dbTransaction.setCategoryName(category.getShortDescription());
            dbTransaction.setCategoryFilter(category.getFilter());
            return dbTransaction;
        }).collect(Collectors.toList());
        transactionRepository.saveAll(dbTransactions);
        return dbTransactions;
    }

    public List<Transaction> uploadTransactions(List<UiTransaction> transactionList, String accountId) {

        // insert middle documents
        Transaction lastTransaction = transactionRepository.
                findTopByAccountIdAndTransactionDateIsLessThanEqual(accountId, transactionList.get(0).getTransactionDate());
        List<Transaction> convertedList = transactionList.stream()
                .map((uiTransaction) -> convertFromUiTransaction(uiTransaction, accountId))
                .collect(Collectors.toList());

        // if first transaction the
        if (lastTransaction == null) {
            lastTransaction = new Transaction(accountId);
            lastTransaction.setAccountBalance(BigDecimal.valueOf(0));
        }

        BigDecimal previousBalance = lastTransaction.getAccountBalance();

        updateTransactionBalances(previousBalance, convertedList);

        // all later documents;
        List<Transaction> existingDocuments = transactionRepository.findByAccountIdAndTransactionDateIsGreaterThanEqual(accountId, convertedList.get(convertedList.size()-1).getTransactionDate());

        if (!existingDocuments.isEmpty()) {
            updateTransactionBalances(previousBalance, existingDocuments);
        }

        convertedList.addAll(existingDocuments);

        transactionRepository.saveAll(convertedList);

        return convertedList;
    }

    public BigDecimal getCurrentBalance(final String accountId) {

        Transaction balanceAmtList = transactionProcessorFactory.getTransactionProcessorByType(BALANCE)
                .getMostRecent(accountId);
        if (balanceAmtList == null) {
            List<Transaction> incomeAmtList = transactionProcessorFactory.getTransactionProcessorByType(INCOME)
                    .getAll(accountId);
            List<Transaction> expenseAmtList = transactionProcessorFactory.getTransactionProcessorByType(EXPENSE)
                    .getAll(accountId);

            return getBalance(incomeAmtList, expenseAmtList, BigDecimal.ZERO);
        } else {
            List<Transaction> incomeAmtList = transactionProcessorFactory.getTransactionProcessorByType(INCOME)
                    .getAllAfterDate(accountId, balanceAmtList.getTransactionDate());
            List<Transaction> expenseAmtList = transactionProcessorFactory.getTransactionProcessorByType(EXPENSE)
                    .getAllAfterDate(accountId, balanceAmtList.getTransactionDate());

            return getBalance(incomeAmtList, expenseAmtList,  balanceAmtList.getAmount());
        }

    }

    public Transaction getLatestTransactionByAccount(String accountId) {
        return transactionRepository.findByAccountId(accountId, getTransactionDateDescendingSort()).get(0);
    }

    protected BigDecimal getBalance(List<Transaction> income, List<Transaction> expense, BigDecimal startingValue) {
        return startingValue.add(sumFromList(income)).subtract(sumFromList(expense));
    }


    protected BigDecimal sumFromList(List<Transaction> tList) {
        BigDecimal sum = BigDecimal.ZERO;
        for (final Transaction t: tList) {
            sum = sum.add(t.getAmount());
        }
        return sum;
    }

    public Transaction convertFromUiTransaction(UiTransaction uiTransaction, String accountId) {
        Transaction t = new Transaction(accountId);
        t.setAmount(BigDecimal.valueOf(uiTransaction.getAmount()));
        t.setDescription(uiTransaction.getDescription());
        t.setType(uiTransaction.getType());
        t.setId(UUID.randomUUID().toString());
        t.setTransactionDate(uiTransaction.getTransactionDate());
        t.setCategoryFilter(uiTransaction.getCategory().getFilter().trim().toLowerCase());
        t.setCategoryName(uiTransaction.getCategory().getShortDescription().trim().toLowerCase());
        return t;
    }

    public Sort getTransactionDateDescendingSort() {
        return new Sort(Sort.Direction.DESC, "transactionDate");
    }


    public void updateTransactionBalances(final BigDecimal initialBalance, final List<Transaction> transactions) {
        BigDecimal previousBalance = initialBalance;

        for (Transaction trns: transactions) {
            if (trns.getType().equals(INCOME)) {
                trns.setAccountBalance(previousBalance.add(trns.getAmount()));
                previousBalance = trns.getAccountBalance();
            } else if (trns.getType().equals(EXPENSE)) {
                trns.setAccountBalance(previousBalance.subtract(trns.getAmount()));
                previousBalance = trns.getAccountBalance();
            } else  {
                previousBalance = trns.getAccountBalance();
            }
        }
    }
}
