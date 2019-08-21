package com.varunsdave.personafinance.budget.service.transactions;

import com.varunsdave.personafinance.budget.model.Transaction;
import com.varunsdave.personafinance.budget.repository.TransactionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
@AllArgsConstructor
public class BalanceTransactionProcessor implements TransactionProcessor {
    private static final String BALANCE_TRANSACTION_TYPE = "balance";
    private final TransactionRepository transactionRepository;

    @Override
    public Transaction create(Transaction transaction, final String accountId) {
        transaction.setType(BALANCE_TRANSACTION_TYPE);
        updateTransactionBalances(transaction);
        return transactionRepository.save(transaction);
    }

    @Override
    public void delete(String id) {
        // not implemented yet.
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Transaction> getAll(final String accountId) {
        final List<Transaction> expenseTransactions = new ArrayList<>();
        for (final Transaction t : transactionRepository.findAll()) {
            if (t.getType().equals(BALANCE_TRANSACTION_TYPE)) {
                expenseTransactions.add(t);
            }
        }
        return expenseTransactions;
    }

    @Override
    public List<Transaction> getAllAfterDate(String accountId, Date date) {
        return Collections.emptyList();
    }

    @Override
    public Transaction update(String id, Transaction newTransaction) {
        return null;
    }

    @Override
    public Transaction getMostRecent(String accountId) {
        List<Transaction> transactions = transactionRepository.findByAccountIdAndType(accountId, BALANCE_TRANSACTION_TYPE);
        return (transactions.isEmpty()) ? transactions.get(transactions.size() - 1 ) : null;
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
