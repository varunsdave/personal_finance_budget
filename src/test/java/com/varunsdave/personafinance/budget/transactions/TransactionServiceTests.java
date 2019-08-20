package com.varunsdave.personafinance.budget.transactions;

import com.varunsdave.personafinance.budget.model.Transaction;
import com.varunsdave.personafinance.budget.model.UiCategory;
import com.varunsdave.personafinance.budget.model.UiTransaction;
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
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
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
    public void getAllTransactions() {
        final List<String> transactionIds = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            transactionIds.add(UUID.randomUUID().toString());
        }
        final String accountId = UUID.randomUUID().toString();
        final List<Transaction> mockTransactions = generateMockTransactions(transactionIds, accountId);
        when(transactionRepository.findAll())
                .thenReturn(mockTransactions);

        List<Transaction> actualTransactions = transactionService.getAllTransactions();

        for (int i = 0; i < transactionIds.size(); i++) {
            Assertions.assertThat( actualTransactions.get(i).getCategoryName()).isEqualTo(mockTransactions.get(i).getCategoryName());
            Assertions.assertThat( actualTransactions.get(i).getCategoryFilter()).isEqualTo(mockTransactions.get(i).getCategoryFilter());
            Assertions.assertThat( actualTransactions.get(i).getId()).isEqualTo(transactionIds.get(i));
        }
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

    @Test
    public void testConvertFromUiTransactionToTransaction() {

        final String uuidString = UUID.randomUUID().toString();
        final Date transactionDate = new Date(2019,10,03);
        final String accountId = UUID.randomUUID().toString();
        UiTransaction uiTransaction = new UiTransaction();
        uiTransaction.setCategory(new UiCategory("test", "test category description"));
        uiTransaction.setType("balance");
        uiTransaction.setId(uuidString);
        uiTransaction.setDescription("test description");
        uiTransaction.setAmount(20.0);
        uiTransaction.setTransactionDate(transactionDate);

        final Transaction actualTransaction = transactionService.convertFromUiTransaction(uiTransaction, accountId);

        Assertions.assertThat(actualTransaction.getId()).isNotEqualTo(uuidString);
        Assertions.assertThat(actualTransaction.getCategoryFilter()).isEqualTo("test");
        Assertions.assertThat(actualTransaction.getCategoryName()).isEqualTo("test category description");
        Assertions.assertThat(actualTransaction.getTransactionDate()).isEqualTo(transactionDate);
        Assertions.assertThat(actualTransaction.getType()).isEqualTo("balance");
        Assertions.assertThat(actualTransaction.getAmount()).isEqualTo(BigDecimal.valueOf(20.0));
        Assertions.assertThat(actualTransaction.getAccountId()).isEqualTo(accountId);
    }

    @Test
    public void testGetDescendingSort() {
        Sort expectedSort = new Sort(Sort.Direction.DESC, "transactionDate");

        Sort actualSort = transactionService.getTransactionDateDescendingSort();

        Assertions.assertThat(actualSort.getOrderFor("transactionDate")).isEqualTo(expectedSort.getOrderFor("transactionDate"));
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
