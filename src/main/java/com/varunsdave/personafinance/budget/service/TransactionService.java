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

    public Transaction createIncome(int amount) {
        final Transaction newTransaction = new Transaction();
        newTransaction.setAmount(amount);
        newTransaction.setTransacationDate(new Date());
        newTransaction.setDescription("");
        newTransaction.setType("income");

        return transactionRepository.save(newTransaction);
    }

    public Transaction createExpense(int amount) {
        final Transaction newTransaction = new Transaction();
        newTransaction.setAmount(amount);
        newTransaction.setTransacationDate(new Date());
        newTransaction.setDescription("");
        newTransaction.setType("expense");

        return transactionRepository.save(newTransaction);
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

}
