package com.varunsdave.personafinance.budget.service.account;

import com.varunsdave.personafinance.budget.model.Account;
import com.varunsdave.personafinance.budget.repository.AccountRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    public Account create(Account account) {
        Date createdAndUpdatedDate = new Date();
        Account newAccount  = new Account();
        newAccount.setId(UUID.randomUUID().toString());
        newAccount.setCreatedAt(account.getCreatedAt());
        newAccount.setName(account.getName());
        newAccount.setLastUpdated(createdAndUpdatedDate);
        newAccount.setBank(account.getName());
        newAccount.setPurpose(account.getPurpose());
        return accountRepository.save(newAccount);
    }

    public List<Account> getAccounts() {
        return accountRepository.findAll();
    }

}
