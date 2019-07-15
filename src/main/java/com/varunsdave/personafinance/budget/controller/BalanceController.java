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
    @PostMapping("/account/{accountId}")
    @ApiOperation("Creates a record of new balance type of transaction.")
    public ResponseEntity<Transaction> createBalance(@PathVariable String accountId, @RequestBody double balanceAmount) {
        return ResponseEntity.ok().body( transactionService.create(balanceAmount, accountId, TYPE));
    }

    /**
     * Retrieves a list of all balance updates.
     * @return List of balance transaction updates
     */
    @GetMapping("balanceTransactions/account/{accountId}")
    @ApiOperation("Retrieves a list of all balance updates.")
    public List<Transaction> getAllBalanceTransactions(@PathVariable String accountId) {
        return transactionService.getTransactionsByType(TYPE, accountId);
    }

    /**
     * Returns the current balance based on previous transactions
     * @return an amount representing current transaction
     */
    @GetMapping("/currentBalance/account/{accountId}")
    @ApiOperation("Returns the current balance based on previous transactions")
    public double currentBalance(@PathVariable String accountId) {
        return transactionService.getCurrentBalance(accountId).doubleValue();
    }
}
