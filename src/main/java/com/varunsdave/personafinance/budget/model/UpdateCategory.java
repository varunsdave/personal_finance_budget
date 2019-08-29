package com.varunsdave.personafinance.budget.model;

import lombok.Data;

import java.util.List;

@Data
public class UpdateCategory {

    private final UiCategory uiCategory;
    private final String[] transactionIds;
}
