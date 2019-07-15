package com.varunsdave.personafinance.budget.service.transactions;

import com.varunsdave.personafinance.budget.model.Transaction;
import com.varunsdave.personafinance.budget.repository.AccountRepository;
import com.varunsdave.personafinance.budget.repository.TransactionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.security.InvalidParameterException;
import java.util.List;

@Service
@AllArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final TransactionProcessorFactory transactionProcessorFactory;

    public Transaction create(double amount, String accountId, String type) {
        if (accountRepository.findById(accountId).isEmpty()) {
            throw new InvalidParameterException();
        }
        return transactionProcessorFactory.getTransactionProcessorByType(type).create(amount, accountId);
    }

    public Transaction create(Transaction transaction, String accountId) {
        if (accountRepository.findById(accountId).isEmpty()) {
            throw new InvalidParameterException();
        }
        return transactionProcessorFactory.getTransactionProcessorByType(transaction.getType()).create(transaction);
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public List<Transaction> getTransactionsByType(String type, String accountId) {
        return transactionProcessorFactory.getTransactionProcessorByType(type).getAll(accountId);
    }

    public BigDecimal getCurrentBalance(final String accountId) {
        List<Transaction> incomeAmtList = transactionProcessorFactory.getTransactionProcessorByType("income")
                .getAll(accountId);
        List<Transaction> expenseAmtList = transactionProcessorFactory.getTransactionProcessorByType("expense")
                .getAll(accountId);
        List<Transaction> balanceAmtList = transactionProcessorFactory.getTransactionProcessorByType("balance")
                .getAll(accountId);

        return getBalance(incomeAmtList, expenseAmtList, balanceAmtList);
    }

    private BigDecimal getBalance(List<Transaction> income, List<Transaction> expense, List<Transaction> balance) {
        BigDecimal total = sumFromList(income).subtract(sumFromList(expense));
        return total;
    }

    private BigDecimal sumFromList(List<Transaction> tList) {
        BigDecimal sum = BigDecimal.ZERO;
        for (final Transaction t: tList) {
            sum = sum.add(t.getAmount());
        }
        return sum;
    }

}
