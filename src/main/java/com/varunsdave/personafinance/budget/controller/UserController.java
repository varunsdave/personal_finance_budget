package com.varunsdave.personafinance.budget.controller;

import com.varunsdave.personafinance.budget.model.Transaction;
import com.varunsdave.personafinance.budget.model.UiCategory;
import com.varunsdave.personafinance.budget.model.UiTransaction;
import com.varunsdave.personafinance.budget.model.UpdateCategory;
import com.varunsdave.personafinance.budget.service.transactions.TransactionService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
        return transactionService.getAllTransactionsByAccount(accountId).stream().map(this::mapTransactionToUiTransaction).collect(Collectors.toList());
    }


    @PutMapping("/{userId}/transactionsCategory/account/{accountId}")
    @ApiOperation("Updates transaction category for this account")
    public List<UiTransaction> updateTransactions(@PathVariable String userId, @PathVariable String accountId,
                                                  @RequestBody UpdateCategory updateCategories) {
        return transactionService.updateTransactionCategory(accountId, updateCategories.getTransactionIds(), updateCategories.getUiCategory())
                .stream().map(this::mapTransactionToUiTransaction).collect(Collectors.toList());
    }

    private UiTransaction mapTransactionToUiTransaction(Transaction t) {
        UiTransaction uiTransaction = new UiTransaction();
        uiTransaction.setTransactionDate(t.getTransactionDate());
        uiTransaction.setAmount(t.getAmount().doubleValue());
        uiTransaction.setAccountBalance(t.getAccountBalance().doubleValue());
        uiTransaction.setDescription(t.getDescription());
        uiTransaction.setId(t.getId());
        uiTransaction.setType(t.getType());
        uiTransaction.setCategory(new UiCategory(t.getCategoryFilter(), t.getCategoryName()));
        return uiTransaction;
    }
}
