package com.varunsdave.personafinance.budget.service.transactions;

import com.varunsdave.personafinance.budget.model.Transaction;
import com.varunsdave.personafinance.budget.repository.TransactionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class ExpenseTransactionProcessor implements TransactionProcessor {

    private final String TRANSACTION_TYPE = "expense";
    private final TransactionRepository transactionRepository;

    @Override
    public Transaction create(double amount, String accountId) {
        final Transaction createdTransaction = new Transaction(accountId);
        createdTransaction.setAmount(amount);
        createdTransaction.setDescription("");
        createdTransaction.setTransactionDate(new Date());
        createdTransaction.setType(TRANSACTION_TYPE);
        return transactionRepository.save(createdTransaction);
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
    public Transaction update(String id, Transaction newTransaction) {
        return null;
    }
}
