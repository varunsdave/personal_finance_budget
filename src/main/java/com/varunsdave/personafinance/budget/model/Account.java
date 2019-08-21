package com.varunsdave.personafinance.budget.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.UUID;

@Data
@Document(collection = "Account")
public class Account {
    @Id
    UUID id;

    String name;

    Date createdAt;

    Date lastUpdated;

    String bank;

    String purpose;
}
