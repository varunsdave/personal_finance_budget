package com.varunsdave.personafinance.budget.controller;

import com.varunsdave.personafinance.budget.model.Transaction;
import com.varunsdave.personafinance.budget.service.transactions.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

    private final TransactionService transactionService;

    @GetMapping("/{userId}/transactions")
    public List<Transaction> getAllUserTransations(@PathVariable String userId) {
        return transactionService.getAllTransactions();
    }
}
