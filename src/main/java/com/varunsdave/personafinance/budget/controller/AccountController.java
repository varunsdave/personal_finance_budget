package com.varunsdave.personafinance.budget.controller;

import com.varunsdave.personafinance.budget.model.Account;
import com.varunsdave.personafinance.budget.service.account.AccountService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/account/")
public class AccountController {

    private final AccountService accountService;

    @PostMapping("")
    @ApiOperation("Creates an account with the specified id")
    public Account createAccount(@RequestBody String name) {
        return accountService.create(name);
    }


}
