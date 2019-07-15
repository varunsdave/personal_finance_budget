package com.varunsdave.personafinance.budget.repository;

import com.varunsdave.personafinance.budget.model.Transaction;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface TransactionRepository extends MongoRepository<Transaction, String> {

    List<Transaction> findByAccountId(final String accountId);

    List<Transaction> findByAccountIdAndType(final String accountId, final String type);
}
