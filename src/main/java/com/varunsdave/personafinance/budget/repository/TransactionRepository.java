package com.varunsdave.personafinance.budget.repository;

import com.varunsdave.personafinance.budget.model.Transaction;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Date;
import java.util.List;

public interface TransactionRepository extends MongoRepository<Transaction, String> {

    List<Transaction> findByAccountId(final String accountId);

    List<Transaction> findByIdInAndAccountId(List<String> ids, final String accountId);

    List<Transaction> findByAccountId(final String accountId, final Sort sort);

    List<Transaction> findByAccountIdAndType(final String accountId, final String type);

    List<Transaction> findByAccountIdAndTransactionDateIsGreaterThanEqual(final String accountId, final Date transactionDate);

    Transaction findTopByAccountIdAndTransactionDateIsLessThanEqual(final String accountId, final Date transactionDate);

//    Transaction findTopOrderByTransactionDateDesc();
}

