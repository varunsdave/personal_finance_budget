package com.varunsdave.personafinance.budget.service.account;

import com.varunsdave.personafinance.budget.model.Account;
import com.varunsdave.personafinance.budget.model.UiAccount;
import com.varunsdave.personafinance.budget.repository.AccountRepository;
import org.assertj.core.api.Assertions;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTests {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountService accountService;

    private EasyRandom randomObject;

    @BeforeEach
    public void setup() {
        randomObject = new EasyRandom();
    }

    @Test
    public void testCreateAccount() {
        UiAccount mockAccount = randomObject.nextObject(UiAccount.class);
        mockAccount.setId(UUID.randomUUID().toString());
        Mockito.when(accountRepository.save(Mockito.any(Account.class))).thenReturn(Mockito.any(Account.class));
        Account createdAccount = accountService.create(mockAccount);

        Assertions.assertThat(createdAccount.getBank()).isEqualTo(mockAccount.getBank());
        Assertions.assertThat(createdAccount.getId().toString()).isNotEqualTo(mockAccount.getId());
        Assertions.assertThat(createdAccount.getName()).isEqualTo(mockAccount.getName());
        Assertions.assertThat(createdAccount.getPurpose()).isEqualTo(mockAccount.getPurpose());
        Assertions.assertThat(createdAccount.getCreatedAt()).isEqualTo(createdAccount.getLastUpdated());

        Mockito.verify(accountRepository, Mockito.times(1)).save(Mockito.any(Account.class));
    }

    @Test
    public void testMappingUiAccountToAccount() {
        UiAccount mockAccount = randomObject.nextObject(UiAccount.class);
        mockAccount.setId(UUID.randomUUID().toString());
        Account actualAccount = accountService.mapUiAccountToAccount(mockAccount);

        Assertions.assertThat(actualAccount.getBank()).isEqualTo(mockAccount.getBank());
        Assertions.assertThat(actualAccount.getId().toString()).isEqualTo(mockAccount.getId());
        Assertions.assertThat(actualAccount.getName()).isEqualTo(mockAccount.getName());
        Assertions.assertThat(actualAccount.getPurpose()).isEqualTo(mockAccount.getPurpose());
        Assertions.assertThat(actualAccount.getCreatedAt()).isEqualTo(mockAccount.getCreatedAt());
        Assertions.assertThat(actualAccount.getLastUpdated()).isEqualTo(mockAccount.getLastUpdated());

    }

    @Test
    public void testGetAll() {
        accountService.getAccounts();
        Mockito.verify(accountRepository, Mockito.times(1)).findAll();
    }

}
