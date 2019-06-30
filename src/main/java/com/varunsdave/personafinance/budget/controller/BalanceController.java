package com.varunsdave.personafinance.budget.controller;

import com.varunsdave.personafinance.budget.model.Transaction;
import com.varunsdave.personafinance.budget.service.transactions.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/balance")
public class BalanceController {
    private final TransactionService transactionService;
    private final String TYPE = "balance";

    @PostMapping("")
    public ResponseEntity<Transaction> createBalance(@RequestBody int balanceAmount) {
        return ResponseEntity.ok().body( transactionService.create(balanceAmount, TYPE));
    }

    @GetMapping("balances")
    public List<Transaction> getAllBalanceTransactions() {
        return transactionService.getTransactionsByType(TYPE);
    }
}
