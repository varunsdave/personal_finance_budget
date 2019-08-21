package com.varunsdave.personafinance.budget.service.account;

import com.varunsdave.personafinance.budget.model.Account;
import com.varunsdave.personafinance.budget.model.UiAccount;
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

    public Account create(UiAccount account) {
        Date createdAndUpdatedDate = new Date();
        Account newAccount = mapUiAccountToAccount(account);
        newAccount.setId(UUID.randomUUID());
        newAccount.setCreatedAt(createdAndUpdatedDate);
        newAccount.setLastUpdated(createdAndUpdatedDate);
        accountRepository.save(newAccount);
        return newAccount;
    }

    public Account mapUiAccountToAccount(UiAccount account) {
        final Account newAccount  = new Account();
        newAccount.setId(UUID.fromString(account.getId()));
        newAccount.setCreatedAt(account.getCreatedAt());
        newAccount.setName(account.getName());
        newAccount.setLastUpdated(account.getLastUpdated());
        newAccount.setBank(account.getBank());
        newAccount.setPurpose(account.getPurpose());
        return newAccount;
    }

    public List<Account> getAccounts() {
        return accountRepository.findAll();
    }

}
