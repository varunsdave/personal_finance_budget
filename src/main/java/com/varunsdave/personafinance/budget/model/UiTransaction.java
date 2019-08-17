package com.varunsdave.personafinance.budget.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Data
public class UiTransaction {

    @Id
    private String id;

    private double amount;

    private Date transactionDate;

    private String Description;

    private String type;

    private double balance;
}
