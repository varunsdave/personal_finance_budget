package com.varunsdave.personafinance.budget.controller;

import com.varunsdave.personafinance.budget.model.Transaction;
import com.varunsdave.personafinance.budget.service.transactions.TransactionService;
import io.swagger.annotations.ApiOperation;
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

    /**
     * Creates a record of new balance type of transaction. This is used to update
     * a balance for a given date.
     * @param balanceAmount the new balance amount
     * @return Transaction record of the balance amount
     */
    @PostMapping("")
    @ApiOperation("Creates a record of new balance type of transaction.")
    public ResponseEntity<Transaction> createBalance(@RequestBody int balanceAmount) {
        return ResponseEntity.ok().body( transactionService.create(balanceAmount, TYPE));
    }

    /**
     * Retrieves a list of all balance updates.
     * @return List of balance transaction updates
     */
    @GetMapping("balanceTransactions")
    @ApiOperation("Retrieves a list of all balance updates.")
    public List<Transaction> getAllBalanceTransactions() {
        return transactionService.getTransactionsByType(TYPE);
    }

    /**
     * Returns the current balance based on previous transactions
     * @return an amount representing current transaction
     */
    @GetMapping("/currentBalance")
    @ApiOperation("Returns the current balance based on previous transactions")
    public double currentBalance() {
        return transactionService.getCurrentBalance();
    }
}
