package com.varunsdave.personafinance.budget.controller;

import com.varunsdave.personafinance.budget.model.Account;
import com.varunsdave.personafinance.budget.model.UiAccount;
import com.varunsdave.personafinance.budget.service.account.AccountService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/account/")
public class AccountController {

    private final AccountService accountService;

    @PostMapping("")
    @ApiOperation("Creates an account with the specified id")
    public Account createAccount(@RequestBody UiAccount uiAccount) {
        return accountService.create(uiAccount);
    }

    @GetMapping("")
    @ApiOperation("Retrieves all accounts")
    public List<Account> getAccounts() {
        return accountService.getAccounts();
    }

}
