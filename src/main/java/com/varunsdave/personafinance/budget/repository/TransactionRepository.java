package com.varunsdave.personafinance.budget.repository;

import com.varunsdave.personafinance.budget.model.Transaction;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Date;
import java.util.List;

public interface TransactionRepository extends MongoRepository<Transaction, String> {

    List<Transaction> findByAccountId(final String accountId);

    List<Transaction> findByIdInAndAccountId(final List<String> ids, final String accountId);

    List<Transaction> findByAccountId(final String accountId, final Sort sort);

    List<Transaction> findByAccountIdAndType(final String accountId, final String type);

    List<Transaction> findByAccountIdAndTransactionDateIsGreaterThanEqual(final String accountId, final Date transactionDate);

    Transaction findTopByAccountIdAndTransactionDateIsLessThanEqual(final String accountId, final Date transactionDate);

    /**
     * Finds all previous transactions
     * @param accountId
     * @param transactionDate
     * @return
     */
    List<Transaction> findByAccountIdAndTransactionDateIsLessThanEqual(final String accountId, final Date transactionDate);

}

