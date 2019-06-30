package com.varunsdave.personafinance.budget.controller;

import com.varunsdave.personafinance.budget.model.Transaction;
import com.varunsdave.personafinance.budget.service.transactions.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/expense")
public class ExpenseController {

    private final TransactionService transactionService;

    @PostMapping("")
    public ResponseEntity<Transaction> createExpense(@RequestBody int expenseAmount) {
        Transaction t = transactionService.create(expenseAmount, "expense");
        return ResponseEntity.ok().body(t);
    }

    @GetMapping("transacations")
    public List<Transaction> getAllExpenseTransactions() {
        return transactionService.getTransactionsByType("expense");
    }
}
