package com.varunsdave.personafinance.budget.service.transactions;

import com.varunsdave.personafinance.budget.model.Transaction;
import com.varunsdave.personafinance.budget.model.UiCategory;
import com.varunsdave.personafinance.budget.model.UiTransaction;
import com.varunsdave.personafinance.budget.repository.AccountRepository;
import com.varunsdave.personafinance.budget.repository.TransactionRepository;
import org.assertj.core.api.Assertions;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTests {

    @Mock
    private TransactionRepository transactionRepository;

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
        final List<Transaction> mockTransactions = generateMockTransactionsWithIds(transactionIds, accountId);
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
        final List<Transaction> mockTransactions = generateMockTransactionsWithIds(transactionIds, accountId);
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

    @Test
    public void testUpdateBalances() {
        // tests increasing balance order
        List<Transaction> mockTransactions = new ArrayList<>();
        final String accountId = UUID.randomUUID().toString();

        Transaction t1 = new Transaction(accountId);
        t1.setId(UUID.randomUUID().toString());
        t1.setType("income");
        t1.setAmount(BigDecimal.valueOf(10));

        // when initial
        transactionService.updateTransactionBalances(BigDecimal.ZERO, mockTransactions);

        Assertions.assertThat(mockTransactions.size()).isEqualTo(0);
        mockTransactions.add(t1);

        // when starting
        transactionService.updateTransactionBalances(BigDecimal.ZERO, mockTransactions);
        Assertions.assertThat(mockTransactions.get(0).getAccountBalance()).isEqualTo(BigDecimal.valueOf(10.0));

        mockTransactions.remove(0);

        t1.setType("expense");
        t1.setAccountBalance(BigDecimal.ZERO);
        mockTransactions.add(t1);
        transactionService.updateTransactionBalances(BigDecimal.ZERO, mockTransactions);
        Assertions.assertThat(mockTransactions.get(0).getAccountBalance()).isEqualTo(BigDecimal.valueOf(-10.0));

        // test balances one's do not get updated.
        mockTransactions.remove(0);

        t1.setType("balance");
        t1.setAccountBalance(BigDecimal.ZERO);
        mockTransactions.add(t1);

        transactionService.updateTransactionBalances(BigDecimal.ZERO, mockTransactions);
        Assertions.assertThat(mockTransactions.get(0).getAccountBalance()).isEqualTo(BigDecimal.valueOf(0));

        // test initial amount is not changed.
        mockTransactions.remove(0);

        t1.setType("income");
        t1.setAccountBalance(BigDecimal.ZERO);
        mockTransactions.add(t1);
        BigDecimal previousBalance = BigDecimal.valueOf(5);
        transactionService.updateTransactionBalances(previousBalance, mockTransactions);
        Assertions.assertThat(mockTransactions.get(0).getAccountBalance()).isEqualTo(BigDecimal.valueOf(15.0));
        Assertions.assertThat(previousBalance.doubleValue()).isEqualTo(5.0);
    }

    @Test
    public void testTransactionBalanceUpdateWithMultipleListItems() {
        List<Transaction> mockTransactions = new ArrayList<>();
        final String accountId = UUID.randomUUID().toString();

        Transaction t1 = new Transaction(accountId);
        t1.setId(UUID.randomUUID().toString());
        t1.setType("income");
        t1.setAmount(BigDecimal.valueOf(10));
        Transaction t2 = new Transaction(accountId);
        t2.setType("expense");
        t2.setAmount(BigDecimal.valueOf(30));

        Transaction t3 = new Transaction(accountId);
        t3.setType("income");
        t3.setAmount(BigDecimal.valueOf(10));

        mockTransactions.add(t1);
        mockTransactions.add(t2);
        mockTransactions.add(t3);

        BigDecimal initialValues = BigDecimal.valueOf(5.0);

        transactionService.updateTransactionBalances(initialValues, mockTransactions);
        Assertions.assertThat(t1.getAccountBalance()).isEqualTo(BigDecimal.valueOf(15.0));
        Assertions.assertThat(t2.getAccountBalance()).isEqualTo(BigDecimal.valueOf(-15.0));
        Assertions.assertThat(t3.getAccountBalance()).isEqualTo(BigDecimal.valueOf(-5.0));
        Assertions.assertThat(initialValues.doubleValue()).isEqualTo(5.0);
    }

    @Test
    public void testGetCurrentBalanceNoTransactions()
    {
        final String mockAccountId =  UUID.randomUUID().toString();
        final Sort mockSort = transactionService.getTransactionDateDescendingSort();
        Mockito.when(transactionRepository.findByAccountId(mockAccountId, mockSort)).thenReturn(Collections.emptyList());

        final BigDecimal actualValue = transactionService.getCurrentBalance(mockAccountId);
        Assertions.assertThat(actualValue).isEqualTo(BigDecimal.ZERO);

    }

    @Test
    public void testGetCurrentBalanceWithTransactions() {
        List<Transaction> mockTransactions = new ArrayList<>();
        mockTransactions.add(randomObject.nextObject(Transaction.class));
        mockTransactions.add(randomObject.nextObject(Transaction.class));
        mockTransactions.add(randomObject.nextObject(Transaction.class));
        final String mockAccountId =  UUID.randomUUID().toString();
        final Sort mockSort = transactionService.getTransactionDateDescendingSort();

        Mockito.when(transactionRepository.findByAccountId(mockAccountId, mockSort)).thenReturn(mockTransactions);

        final BigDecimal actualValue = transactionService.getCurrentBalance(mockAccountId);
        Assertions.assertThat(actualValue).isEqualTo(mockTransactions.get(0).getAccountBalance());
        Assertions.assertThat(actualValue).isNotEqualTo(mockTransactions.get(1).getAccountBalance());
        Assertions.assertThat(actualValue).isNotEqualTo(mockTransactions.get(2).getAccountBalance());
    }

    @Test
    public void testUpdateCategories() {
        final String filterString = "testFilter";
        final String shortDescription = "test short description";
        final UiCategory category = new UiCategory(filterString, shortDescription);
        final List<String> transactionIds = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            transactionIds.add(UUID.randomUUID().toString());
        }
        final String accountId = UUID.randomUUID().toString();
        List<Transaction> mockTransactions = generateMockTransactionsWithIds(transactionIds, accountId);

        Mockito.when(transactionRepository.findByIdInAndAccountId(transactionIds, accountId)).thenReturn(mockTransactions);

        final List<Transaction> actualTransactions = transactionService.updateTransactionCategory(accountId, transactionIds, category);

        for (int i = 0; i < transactionIds.size(); i++) {
            Assertions.assertThat(actualTransactions.get(i).getCategoryName()).isEqualTo(shortDescription);
            Assertions.assertThat(actualTransactions.get(i).getCategoryFilter()).isEqualTo(filterString);
        }
    }

    /**
     * Tests upload of transactions with no previous transactions for the account.
     * Verify all balances are correct.
     */
    @Test
    public void testUploadTransactionListNoPreviousTransactions() {
        final String accountId = UUID.randomUUID().toString();
        final List<UiTransaction> mockUiTransactions = generateMockUiTransactions(10);

        Mockito.when(transactionRepository
                .findTopByAccountIdAndTransactionDateIsLessThanEqual(accountId, mockUiTransactions.get(0).getTransactionDate()))
                .thenReturn(null);
        Mockito.when(transactionRepository
                .findByAccountIdAndTransactionDateIsGreaterThanEqual(any(String.class), any(Date.class)))
                .thenReturn(Collections.EMPTY_LIST);
        List<Transaction> actualList = transactionService.uploadTransactions(mockUiTransactions, accountId);

        BigDecimal expected = BigDecimal.valueOf(0.0);
        for (int i = 0; i < mockUiTransactions.size(); i++) {
            expected = expected.add(BigDecimal.valueOf(i));
            Assertions.assertThat(actualList.get(i).getAccountBalance()).isEqualTo(expected);
        }

        Assertions.assertThat(actualList.size()).isEqualTo(10);

    }

    @Test
    public void testUploadTransactionListWithNewTransactionsBeforeExistingTransactions() {
        final String accountId = UUID.randomUUID().toString();
        final List<UiTransaction> mockUiTransactions = generateMockUiTransactions(10);
        final Transaction mockPreviousTransaction = randomObject.nextObject(Transaction.class);
        BigDecimal previousBalance = BigDecimal.valueOf(4000.0);
        mockPreviousTransaction.setAccountBalance(previousBalance);
        Mockito.when(transactionRepository
                .findTopByAccountIdAndTransactionDateIsLessThanEqual(accountId, mockUiTransactions.get(0).getTransactionDate()))
                .thenReturn(mockPreviousTransaction);

        Mockito.when(transactionRepository
                .findByAccountIdAndTransactionDateIsGreaterThanEqual(any(String.class), any(Date.class)))
                .thenReturn(Collections.EMPTY_LIST);

        List<Transaction> actualList = transactionService.uploadTransactions(mockUiTransactions, accountId);

        BigDecimal expected = BigDecimal.valueOf(previousBalance.doubleValue());
        for (int i = 0; i < mockUiTransactions.size(); i++) {
            expected = expected.add(BigDecimal.valueOf(i));
            Assertions.assertThat(actualList.get(i).getAccountBalance()).isEqualTo(expected);
        }

        Assertions.assertThat(actualList.size()).isEqualTo(10);
    }

    @Test
    public void testUploadTransactionListWithNewTransactionsAfterExistingTransactions() {
        final String accountId = UUID.randomUUID().toString();
        final List<UiTransaction> mockUiTransactions = generateMockUiTransactions(10);
        final Transaction mockPreviousTransaction = randomObject.nextObject(Transaction.class);
        BigDecimal previousBalance = BigDecimal.valueOf(990.0);
        mockPreviousTransaction.setAccountBalance(previousBalance);
        Mockito.when(transactionRepository
                .findTopByAccountIdAndTransactionDateIsLessThanEqual(accountId, mockUiTransactions.get(0).getTransactionDate()))
                .thenReturn(mockPreviousTransaction);

        final List<Transaction> mockExistingTransactions = new ArrayList<>();
        Transaction existingT1 = randomObject.nextObject(Transaction.class);
        existingT1.setType("income");
        existingT1.setAmount(BigDecimal.valueOf(10.0));
        existingT1.setAccountBalance(BigDecimal.valueOf(1000.0));

        Transaction existingT2 = randomObject.nextObject(Transaction.class);
        existingT2.setType("expense");
        existingT2.setAmount(BigDecimal.valueOf(100.0));
        existingT2.setAccountBalance(BigDecimal.valueOf(900.0));

        mockExistingTransactions.add(existingT1);
        mockExistingTransactions.add(existingT2);

        Mockito.when(transactionRepository
                .findByAccountIdAndTransactionDateIsGreaterThanEqual(any(String.class), any(Date.class)))
                .thenReturn(mockExistingTransactions);

        List<Transaction> actualList = transactionService.uploadTransactions(mockUiTransactions, accountId);

        BigDecimal expected = BigDecimal.valueOf(previousBalance.doubleValue());
        for (int i = 0; i < mockUiTransactions.size(); i++) {
            expected = expected.add(BigDecimal.valueOf(i));
            Assertions.assertThat(actualList.get(i).getAccountBalance()).isEqualTo(expected);
        }

        Assertions.assertThat(actualList.size()).isEqualTo(12);

        Assertions.assertThat(actualList.get(10).getAccountBalance()).isEqualTo(BigDecimal.valueOf(1045.0)); // 990 + 45 + 10
        Assertions.assertThat(actualList.get(11).getAccountBalance()).isEqualTo(BigDecimal.valueOf(945.0)); // 990 + 45 + 10 - 100
    }

    @Test
    public void testGetAllTransactionsByAccount() {
        final String accountId = UUID.randomUUID().toString();

        List<Transaction> transactions = generateMockTransactions(accountId);
        Mockito.when(transactionRepository.findByAccountId(accountId, transactionService.getTransactionDateDescendingSort()))
                .thenReturn(transactions);

        List<Transaction> actual = transactionService.getAllTransactionsByAccount(accountId);

        Assertions.assertThat(actual.size()).isEqualTo(transactions.size());
        Mockito.verify(transactionRepository).findByAccountId(accountId, transactionService.getTransactionDateDescendingSort());
    }

    @Test
    public void testGetAllTransactionsByType() {
        final String accountId = UUID.randomUUID().toString();
        final String type = "income";
        List<Transaction> transactions = generateMockTransactions(accountId);
        Mockito.when(transactionRepository.findByAccountIdAndType(accountId, type))
                .thenReturn(transactions);

        List<Transaction> actual = transactionService.getTransactionsByType(type, accountId);

        Assertions.assertThat(actual.size()).isEqualTo(transactions.size());
        Mockito.verify(transactionRepository).findByAccountIdAndType(accountId, type);
    }

    private List<UiTransaction> generateMockUiTransactions(final int size) {
        final List<UiTransaction> transactionList = new ArrayList<>();
        final UiCategory category = randomObject.nextObject(UiCategory.class);
        final Date transactionDate = Date.from(Instant.now());
        for (int i = 0; i < size; i++) {
            UiTransaction transaction = randomObject.nextObject(UiTransaction.class);
            transaction.setId(UUID.randomUUID().toString());
            transaction.setAmount(i);
            transaction.setTransactionDate(Date.from(Instant.ofEpochSecond(transactionDate.getTime() + i)));
            transaction.setCategory(category);
            transaction.setType("income");
            transactionList.add(transaction);
        }
        return transactionList;
    }


    private List<Transaction> generateMockTransactions(final String accountId) {
        final List<String> transactionIds = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            transactionIds.add(UUID.randomUUID().toString());
        }
        return generateMockTransactionsWithIds(transactionIds, accountId);
    }


    private List<Transaction> generateMockTransactionsWithIds(final List<String> transactionIds, final String accountId) {
        final List<Transaction> mockTransactions = new ArrayList<>();
        for (int i = 0; i < transactionIds.size(); i++) {
            Transaction mockT = new Transaction(accountId);
            mockT.setId(transactionIds.get(i));
            mockTransactions.add(mockT);
        }

        return mockTransactions;
    }
}
