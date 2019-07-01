package com.varunsdave.personafinance.budget.model;

import lombok.Data;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Data
@Document(collection = "transactions")
public class Transaction {

    @Id
    private String id;

    private double amount;

    private Date transactionDate;

    private String Description;

    private String type;

    @NonNull
    private final String accountId;
}
