package com.varunsdave.personafinance.budget.service.transactions;

import com.varunsdave.personafinance.budget.model.Transaction;
import com.varunsdave.personafinance.budget.repository.TransactionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class BalanceTransactionProcessor implements TransactionProcessor {
    private final String TRANSACTION_TYPE = "balance";
    private final TransactionRepository transactionRepository;

    @Override
    public Transaction create(double amount, String accountId) {
        final Transaction transaction = new Transaction(accountId);
        transaction.setType(TRANSACTION_TYPE);
        transaction.setTransactionDate(new Date());
        transaction.setDescription("");
        transaction.setAmount(BigDecimal.valueOf(amount));
        return transactionRepository.save(transaction);
    }

    @Override
    public Transaction create(Transaction transaction) {
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
}
