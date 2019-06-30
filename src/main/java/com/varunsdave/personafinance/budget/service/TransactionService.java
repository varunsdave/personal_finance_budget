package com.varunsdave.personafinance.budget.service;

import com.varunsdave.personafinance.budget.model.Transaction;
import com.varunsdave.personafinance.budget.repository.TransactionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final TransactionProcessorFactory transactionProcessorFactory;

    public Transaction create(int amount, String type) {
        return transactionProcessorFactory.getTransactionProcessorByType(type).create(amount);
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public List<Transaction> getTransactionsByType(String type) {
        return transactionProcessorFactory.getTransactionProcessorByType(type).getAll();
    }

}
