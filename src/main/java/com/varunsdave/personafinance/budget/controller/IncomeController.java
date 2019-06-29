package com.varunsdave.personafinance.budget.controller;

import com.varunsdave.personafinance.budget.model.Transaction;
import com.varunsdave.personafinance.budget.service.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/income")
public class IncomeController {

    private final TransactionService transactionService;

    @PostMapping("")
    public ResponseEntity<Transaction> createIncome(@RequestBody int incomeAmt) {

        return ResponseEntity.ok().body( transactionService.createIncome(incomeAmt));
    }

}
