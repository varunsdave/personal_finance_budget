package com.varunsdave.personafinance.budget.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document(collection = "Account")
public class Account {
    @Id
    String id;

    String name;

    Date createdAt;

    Date lastUpdated;

    String bank;

    String purpose;
}
