package com.varunsdave.personafinance.budget.repository;

import com.varunsdave.personafinance.budget.model.Account;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface AccountRepository extends MongoRepository<Account, String> {
}
