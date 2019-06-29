package com.varunsdave.personafinance.budget.repository;

import com.varunsdave.personafinance.budget.model.Transaction;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TransactionRepository extends MongoRepository<Transaction, String> {
}
