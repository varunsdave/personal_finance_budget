package com.varunsdave.personafinance.budget.controller;

import com.varunsdave.personafinance.budget.model.Transaction;
import com.varunsdave.personafinance.budget.model.UiTransaction;
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
    private final String TYPE = "expense";

    @PostMapping("/account/{accountId}")
    public ResponseEntity<Transaction> createExpense(@PathVariable String accountId, @RequestBody UiTransaction transaction) {
        Transaction t = transactionService.create(transaction, accountId);
        return ResponseEntity.ok().body(t);
    }

    @GetMapping("transacations/account/{accountId}")
    public List<Transaction> getAllExpenseTransactions(@PathVariable String accountId) {
        return transactionService.getTransactionsByType(TYPE, accountId);
    }
}
