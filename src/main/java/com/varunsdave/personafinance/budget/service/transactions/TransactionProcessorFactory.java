package com.varunsdave.personafinance.budget.service.transactions;

import com.varunsdave.personafinance.budget.repository.TransactionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class TransactionProcessorFactory {

    private final TransactionRepository transactionRepository;
    public TransactionProcessor getTransactionProcessorByType(String type) {
        switch (type) {
            case "income":
                return new IncomeTransactionProcessor(transactionRepository);
            case "expense":
                return new ExpenseTransactionProcessor(transactionRepository);
            case "balance":
                return new BalanceTransactionProcessor(transactionRepository);
            default:
                return null;
        }
    }
}
