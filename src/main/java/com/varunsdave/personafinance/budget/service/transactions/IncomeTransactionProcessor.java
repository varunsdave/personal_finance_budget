package com.varunsdave.personafinance.budget.service.transactions;

import com.varunsdave.personafinance.budget.model.Transaction;
import com.varunsdave.personafinance.budget.model.UiTransaction;
import com.varunsdave.personafinance.budget.repository.TransactionRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class IncomeTransactionProcessor implements TransactionProcessor {

    private static final String TRANSACTION_TYPE = "income";
    private final TransactionRepository transactionRepository;

    @Override
    public Transaction create(UiTransaction transaction, String accountId) {
        final Transaction createdTransaction = new Transaction(accountId);
        createdTransaction.setAmount(BigDecimal.valueOf(transaction.getAmount()));
        createdTransaction.setDescription(transaction.getDescription());
        createdTransaction.setTransactionDate(transaction.getTransactionDate());
        createdTransaction.setType(TRANSACTION_TYPE);
        final List<Transaction> previousTransactions = transactionRepository.findByAccountId(accountId,  new Sort(Sort.Direction.DESC, "transactionDate"));
        if (previousTransactions.isEmpty()) {
            createdTransaction.setAccountBalance(createdTransaction.getAmount());
        } else {
            createdTransaction.setAccountBalance(previousTransactions.get(0).getAccountBalance().add(createdTransaction.getAmount()));
        }

        return transactionRepository.save(createdTransaction);
    }

    @Override
    public void delete(String id) {
        // not implemented yet.
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Transaction> getAll(final String accountId) {
        return transactionRepository.findByAccountIdAndType(accountId, TRANSACTION_TYPE);
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
