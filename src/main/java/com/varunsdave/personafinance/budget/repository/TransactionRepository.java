package com.varunsdave.personafinance.budget.repository;

import com.varunsdave.personafinance.budget.model.Transaction;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface TransactionRepository extends MongoRepository<Transaction, String> {

    @Query("{'accountId' : 0}")
    List<Transaction> findByAccountId(final String accountId);


}
