package com.varunsdave.personafinance.budget.controller;

import com.varunsdave.personafinance.budget.model.Transaction;
import com.varunsdave.personafinance.budget.model.UiTransaction;
import com.varunsdave.personafinance.budget.service.transactions.TransactionService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/income")
public class IncomeController {

    private static final String TYPE = "income";
    private final TransactionService transactionService;

    @PostMapping("/account/{accountId}")
    public ResponseEntity<Transaction> createIncome(@PathVariable String accountId, @RequestBody UiTransaction transaction) {
       return ResponseEntity.ok().body( transactionService.create(transaction, accountId));
    }

    @GetMapping("transacations/account/{accountId}")
    @ApiOperation("Retrieve all income transactions for a given account")
    public List<Transaction> getAllIncomeTransactions(@PathVariable String accountId) {
        return transactionService.getTransactionsByType(TYPE, accountId);
    }
}
