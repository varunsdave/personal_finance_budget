package com.varunsdave.personafinance.budget.service.transactions;

import com.varunsdave.personafinance.budget.model.Transaction;
import com.varunsdave.personafinance.budget.model.UiTransaction;
import com.varunsdave.personafinance.budget.repository.AccountRepository;
import com.varunsdave.personafinance.budget.repository.TransactionRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.security.InvalidParameterException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final TransactionProcessorFactory transactionProcessorFactory;


    public Transaction create(UiTransaction transaction, String accountId) {
        if (accountRepository.findById(accountId).isEmpty()) {
            throw new InvalidParameterException();
        }
        return transactionProcessorFactory.getTransactionProcessorByType(transaction.getType()).create(transaction, accountId);
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public List<Transaction> getAllTransactionsByAccount(final String accountId) {
        return transactionRepository.findByAccountId(accountId, new Sort(Sort.Direction.DESC, "transactionDate"));
    }

    public List<Transaction> getTransactionsByType(String type, String accountId) {
        return transactionProcessorFactory.getTransactionProcessorByType(type).getAll(accountId);
    }

    public List<Transaction> uploadTransactions(List<UiTransaction> transactionList, String accountId) {
        Transaction lastTransaction = transactionRepository.findByAccountId(accountId, new Sort(Sort.Direction.DESC, "transactionDate")).get(0);

        List<Transaction> convertedList = transactionList.stream().map((uiTransaction) -> {
            Transaction t = new Transaction(accountId);
            t.setAmount(BigDecimal.valueOf(uiTransaction.getAmount()));
            t.setDescription(uiTransaction.getDescription());
            t.setType(uiTransaction.getType());
            t.setId(UUID.randomUUID().toString());
            t.setTransactionDate(uiTransaction.getTransactionDate());
            return t;
        }).collect(Collectors.toList());
        // if first transaction the
        if (lastTransaction == null) {
            lastTransaction = new Transaction(accountId);
            lastTransaction.setAccountBalance(BigDecimal.valueOf(transactionList.get(0).getAmount()));
        }

        BigDecimal previousBalance = lastTransaction.getAccountBalance();

        for (Transaction t: convertedList) {
            if (t.getType().equals("income")) {
                t.setAccountBalance(previousBalance.add(t.getAmount()));
                previousBalance = t.getAccountBalance();
            } else if (t.getType().equals("expense")) {
                t.setAccountBalance(previousBalance.subtract(t.getAmount()));
                previousBalance = t.getAccountBalance();
            } else  {
                previousBalance = t.getAccountBalance();
            }
        }

        transactionRepository.saveAll(convertedList);
        return convertedList;
    }

    public BigDecimal getCurrentBalance(final String accountId) {

        Transaction balanceAmtList = transactionProcessorFactory.getTransactionProcessorByType("balance")
                .getMostRecent(accountId);
        if (balanceAmtList == null) {
            List<Transaction> incomeAmtList = transactionProcessorFactory.getTransactionProcessorByType("income")
                    .getAll(accountId);
            List<Transaction> expenseAmtList = transactionProcessorFactory.getTransactionProcessorByType("expense")
                    .getAll(accountId);

            return getBalance(incomeAmtList, expenseAmtList, BigDecimal.ZERO);
        } else {
            List<Transaction> incomeAmtList = transactionProcessorFactory.getTransactionProcessorByType("income")
                    .getAllAfterDate(accountId, balanceAmtList.getTransactionDate());
            List<Transaction> expenseAmtList = transactionProcessorFactory.getTransactionProcessorByType("expense")
                    .getAllAfterDate(accountId, balanceAmtList.getTransactionDate());

            return getBalance(incomeAmtList, expenseAmtList,  balanceAmtList.getAmount());
        }

    }

    public Transaction getLatestTransactionByAccount(String accountId) {
        return transactionRepository.findByAccountId(accountId, new Sort(Sort.Direction.DESC, "transactionDate")).get(0);
    }

    private BigDecimal getBalance(List<Transaction> income, List<Transaction> expense, BigDecimal startingValue) {
        BigDecimal total = startingValue.add(sumFromList(income)).subtract(sumFromList(expense));
        return total;
    }


    private BigDecimal sumFromList(List<Transaction> tList) {
        BigDecimal sum = BigDecimal.ZERO;
        for (final Transaction t: tList) {
            sum = sum.add(t.getAmount());
        }
        return sum;
    }

}
