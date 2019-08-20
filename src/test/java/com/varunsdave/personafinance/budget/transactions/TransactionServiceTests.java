package com.varunsdave.personafinance.budget.transactions;

import com.varunsdave.personafinance.budget.model.Transaction;
import com.varunsdave.personafinance.budget.model.UiCategory;
import com.varunsdave.personafinance.budget.repository.AccountRepository;
import com.varunsdave.personafinance.budget.repository.TransactionRepository;
import com.varunsdave.personafinance.budget.service.transactions.TransactionProcessor;
import com.varunsdave.personafinance.budget.service.transactions.TransactionService;
import org.assertj.core.api.Assertions;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTests {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private TransactionProcessor transactionProcessor;

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private TransactionService transactionService;

    private EasyRandom randomObject;

    @BeforeEach
    public void setup() {
        randomObject = new EasyRandom();
    }

    @Test
    public void testUpdateTransactionCategory() {

        final List<String> transactionIds = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            transactionIds.add(UUID.randomUUID().toString());
        }
        final String accountId = UUID.randomUUID().toString();
        final List<Transaction> mockTransactions = generateMockTransactions(transactionIds, accountId);
        when(transactionRepository.findByIdInAndAccountId(transactionIds, accountId))
                .thenReturn(mockTransactions);

        UiCategory uiCategory = randomObject.nextObject(UiCategory.class);
        List<Transaction> updatedTransactions = transactionService.updateTransactionCategory(accountId, transactionIds, uiCategory);

        for (int i = 0; i < transactionIds.size(); i++) {
            Assertions.assertThat(updatedTransactions.get(i).getCategoryName()).isEqualTo(uiCategory.getShortDescription());
            Assertions.assertThat(updatedTransactions.get(i).getCategoryFilter()).isEqualTo(uiCategory.getFilter());
            Assertions.assertThat(updatedTransactions.get(i).getId()).isEqualTo(transactionIds.get(i));
        }

    }

    private List<Transaction> generateMockTransactions(final List<String> transactionIds, final String accountId) {
        final List<Transaction> mockTransactions = new ArrayList<>();
        for (int i = 0; i < transactionIds.size(); i++) {
            Transaction mockT = new Transaction(accountId);
            mockT.setId(transactionIds.get(i));
            mockTransactions.add(mockT);
        }

        return mockTransactions;
    }
}
