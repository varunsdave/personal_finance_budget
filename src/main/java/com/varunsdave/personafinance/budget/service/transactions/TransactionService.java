package com.varunsdave.personafinance.budget.service.transactions;

import com.varunsdave.personafinance.budget.model.Transaction;
import com.varunsdave.personafinance.budget.repository.TransactionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final TransactionProcessorFactory transactionProcessorFactory;

    public Transaction create(double amount, String type) {
        return transactionProcessorFactory.getTransactionProcessorByType(type).create(amount);
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public List<Transaction> getTransactionsByType(String type) {
        return transactionProcessorFactory.getTransactionProcessorByType(type).getAll();
    }

    public double getCurrentBalance() {
        List<Transaction> incomeAmtList = transactionProcessorFactory.getTransactionProcessorByType("income").getAll();
        List<Transaction> expenceAmtList = transactionProcessorFactory.getTransactionProcessorByType("expense").getAll();
        List<Transaction> balanceAmtList = transactionProcessorFactory.getTransactionProcessorByType("balance").getAll();

        return getBalance(incomeAmtList, expenceAmtList, balanceAmtList);
    }

    private double getBalance(List<Transaction> income, List<Transaction> expense, List<Transaction> balance) {
        return sumFromList(income) - sumFromList(expense);
    }

    private double sumFromList(List<Transaction> tList) {
        double sum = 0.0;
        for (final Transaction t: tList) {
            sum += t.getAmount();
        }
        return sum;
    }

}
