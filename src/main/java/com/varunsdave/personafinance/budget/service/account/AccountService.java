package com.varunsdave.personafinance.budget.service.account;

import com.varunsdave.personafinance.budget.model.Account;
import com.varunsdave.personafinance.budget.repository.AccountRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@AllArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    public Account create(String name) {
        Date createdAndUpdatedDate = new Date();
        Account newAccount  = new Account();
        newAccount.setCreatedAt(createdAndUpdatedDate);
        newAccount.setName(name);
        newAccount.setLastUpdated(createdAndUpdatedDate);
        return accountRepository.save(newAccount);
    }

}
