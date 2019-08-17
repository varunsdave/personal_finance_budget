package com.varunsdave.personafinance.budget.controller;

import com.varunsdave.personafinance.budget.model.Transaction;
import com.varunsdave.personafinance.budget.model.UiCategory;
import com.varunsdave.personafinance.budget.model.UiTransaction;
import com.varunsdave.personafinance.budget.service.transactions.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

    private final TransactionService transactionService;

    @GetMapping("/{userId}/transactions")
    public List<Transaction> getAllUserTransations(@PathVariable String userId) {
        return transactionService.getAllTransactions();
    }

    @GetMapping("/{userId}/transactions/account/{accountId}")
    public List<UiTransaction> getAllUserTransationsByAccount(@PathVariable String userId, @PathVariable String accountId) {
        return transactionService.getAllTransactionsByAccount(accountId).stream().map(t -> {
            UiTransaction uiTransaction = new UiTransaction();
            uiTransaction.setTransactionDate(t.getTransactionDate());
            uiTransaction.setAmount(t.getAmount().doubleValue());
            uiTransaction.setAccountBalance(t.getAccountBalance().doubleValue());
            uiTransaction.setDescription(t.getDescription());
            uiTransaction.setId(t.getId());
            uiTransaction.setType(t.getType());
            uiTransaction.setCategory(new UiCategory(t.getCategoryFilter(), t.getCategoryName()));
            return uiTransaction;
        }).collect(Collectors.toList());
    }
}
