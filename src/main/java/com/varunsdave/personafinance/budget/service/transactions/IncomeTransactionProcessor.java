package com.varunsdave.personafinance.budget.service.transactions;

import com.varunsdave.personafinance.budget.model.Transaction;
import com.varunsdave.personafinance.budget.repository.TransactionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class IncomeTransactionProcessor implements TransactionProcessor {

    private final String TRANSACTION_TYPE = "income";
    private final TransactionRepository transactionRepository;

    @Override
    public Transaction create(double amount, String accountId) {
        final Transaction createdTransaction = new Transaction(accountId);
        createdTransaction.setAmount(BigDecimal.valueOf(amount));
        createdTransaction.setDescription("");
        createdTransaction.setTransactionDate(new Date());
        createdTransaction.setType(TRANSACTION_TYPE);
        return transactionRepository.save(createdTransaction);
    }

    @Override
    public Transaction create(Transaction transaction) {
        final Transaction createdTransaction = new Transaction(transaction.getAccountId());
        createdTransaction.setAmount(transaction.getAmount());
        createdTransaction.setDescription(transaction.getDescription());
        createdTransaction.setTransactionDate(transaction.getTransactionDate());
        createdTransaction.setType(TRANSACTION_TYPE);
        return transactionRepository.save(createdTransaction);   }

    @Override
    public void delete(String id) {
    }

    @Override
    public List<Transaction> getAll(final String accountId) {
        final List<Transaction> incomeTransactions =transactionRepository.findByAccountIdAndType(accountId, TRANSACTION_TYPE);
        System.out.println(incomeTransactions.size());
        return incomeTransactions;
    }

    @Override
    public List<Transaction> getAllAfterDate(String accountId, Date date) {
        final List<Transaction> incomeTransactions = transactionRepository
                .findByAccountIdAndType(accountId, TRANSACTION_TYPE)
                .stream()
                .filter(transaction -> transaction.getTransactionDate().compareTo(date) >= 0 )
                .collect(Collectors.toList());
        return incomeTransactions;
    }

    @Override
    public Transaction update(String id, Transaction newTransaction) {
        return null;
    }

    @Override
    public Transaction getMostRecent(String accountId) {
        return null;
    }


}
