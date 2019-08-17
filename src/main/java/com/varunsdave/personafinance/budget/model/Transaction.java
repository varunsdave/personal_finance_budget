package com.varunsdave.personafinance.budget.model;

import lombok.Data;
import lombok.NonNull;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.Date;
@Data
@Document(collection = "transactions")
public class Transaction {

    @Id
    private String id;

    private BigDecimal amount;

    private BigDecimal accountBalance;

    private Date transactionDate;

    private String Description;

    private String type;

    private String categoryFilter;

    private String categoryName;

    @NonNull
    private final String accountId;
}
