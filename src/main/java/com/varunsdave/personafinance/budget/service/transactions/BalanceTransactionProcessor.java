package com.varunsdave.personafinance.budget.service.transactions;

import com.varunsdave.personafinance.budget.model.Transaction;
import com.varunsdave.personafinance.budget.model.UiTransaction;
import com.varunsdave.personafinance.budget.repository.TransactionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class BalanceTransactionProcessor implements TransactionProcessor {
    private final String TRANSACTION_TYPE = "balance";
    private final TransactionRepository transactionRepository;

    @Override
    public Transaction create(final UiTransaction uiTransaction, final String accountId) {
        Transaction transaction = new Transaction(accountId);
        transaction.setTransactionDate(uiTransaction.getTransactionDate());
        transaction.setId(UUID.randomUUID().toString());
        transaction.setAmount(BigDecimal.valueOf(uiTransaction.getAmount()));
        transaction.setType(TRANSACTION_TYPE);
        transaction.setAccountBalance(transaction.getAmount());

        updateTransactionBalances(transaction);

        return transactionRepository.save(transaction);
    }

    @Override
    public void delete(String id) {

    }

    @Override
    public List<Transaction> getAll(final String accountId) {
        final List<Transaction> expenseTransactions = new ArrayList<>();
        for (final Transaction t : transactionRepository.findAll()) {
            if (t.getType().equals(TRANSACTION_TYPE)) {
                expenseTransactions.add(t);
            }
        }
        return expenseTransactions;
    }

    @Override
    public List<Transaction> getAllAfterDate(String accountId, Date date) {
        return null;
    }

    @Override
    public Transaction update(String id, Transaction newTransaction) {
        return null;
    }

    @Override
    public Transaction getMostRecent(String accountId) {
        List<Transaction> transactions = transactionRepository.findByAccountIdAndType(accountId, TRANSACTION_TYPE);
        return (transactions.size() > 0) ? transactions.get(transactions.size() - 1 ) : null;
    }

    private void updateTransactionBalances(Transaction balanceTransaction) {
        List<Transaction> transactions = transactionRepository.findByAccountIdAndTransactionDateIsGreaterThanEqual(balanceTransaction.getAccountId(),
                balanceTransaction.getTransactionDate());
        BigDecimal previousBalance = balanceTransaction.getAccountBalance();
        for (Transaction transaction: transactions) {
            if (transaction.getType().equals("income")) {
                transaction.setAccountBalance(previousBalance.add(transaction.getAmount()));
                previousBalance = transaction.getAccountBalance();
            } else if (transaction.getType().equals("expense")) {
                transaction.setAccountBalance(previousBalance.subtract(transaction.getAmount()));
                previousBalance = transaction.getAccountBalance();
            } else {
                break; // assume the current latest balance is correct.
            }

        }
        transactionRepository.saveAll(transactions);
    }
}
