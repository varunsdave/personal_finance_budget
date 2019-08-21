package com.varunsdave.personafinance.budget.service.transactions;

import com.varunsdave.personafinance.budget.model.Transaction;
import com.varunsdave.personafinance.budget.repository.TransactionRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class IncomeTransactionProcessor implements TransactionProcessor {

    private static final String INCOME_TRANSACTION_TYPE = "income";
    private final TransactionRepository transactionRepository;

    @Override
    public Transaction create(Transaction transaction, String accountId) {
        transaction.setType(INCOME_TRANSACTION_TYPE);
        final List<Transaction> previousTransactions = transactionRepository.findByAccountId(accountId,  new Sort(Sort.Direction.DESC, "transactionDate"));
        if (previousTransactions.isEmpty()) {
            transaction.setAccountBalance(transaction.getAmount());
        } else {
            transaction.setAccountBalance(previousTransactions.get(0).getAccountBalance().add(transaction.getAmount()));
        }

        return transactionRepository.save(transaction);
    }

    @Override
    public void delete(String id) {
        // not implemented yet.
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Transaction> getAll(final String accountId) {
        return transactionRepository.findByAccountIdAndType(accountId, INCOME_TRANSACTION_TYPE);
    }

    @Override
    public List<Transaction> getAllAfterDate(String accountId, Date date) {
        return transactionRepository
                .findByAccountIdAndType(accountId, INCOME_TRANSACTION_TYPE)
                .stream()
                .filter(transaction -> transaction.getTransactionDate().compareTo(date) >= 0 )
                .collect(Collectors.toList());
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
