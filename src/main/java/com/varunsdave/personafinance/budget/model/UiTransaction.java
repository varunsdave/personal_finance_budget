package com.varunsdave.personafinance.budget.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Data
@RequiredArgsConstructor
public class UiTransaction {

    @Id
    private String id;

    private double amount;

    private Date transactionDate;

    private String Description;

    private String type;

    private double accountBalance;

    private UiCategory category;
}
