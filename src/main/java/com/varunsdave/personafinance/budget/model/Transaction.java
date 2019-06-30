package com.varunsdave.personafinance.budget.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Data
@Document(collection = "transactions")
public class Transaction {

    @Id
    private String id;

    private int amount;

    private Date transacationDate;

    private String Description;

    private String type;
}