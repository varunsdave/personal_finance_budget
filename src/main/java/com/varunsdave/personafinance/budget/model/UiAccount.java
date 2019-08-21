package com.varunsdave.personafinance.budget.model;

import lombok.Data;

import java.util.Date;

@Data
public class UiAccount {
    String id;

    String name;

    Date createdAt;

    Date lastUpdated;

    String bank;

    String purpose;
}
