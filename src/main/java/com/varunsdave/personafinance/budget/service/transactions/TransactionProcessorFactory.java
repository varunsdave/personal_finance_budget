package com.varunsdave.personafinance.budget.service.transactions;

import com.varunsdave.personafinance.budget.repository.TransactionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class TransactionProcessorFactory {

    private final TransactionRepository transactionRepository;
    private final IncomeTransactionProcessor incomeTransactionProcessor;
    private final ExpenseTransactionProcessor expenseTransactionProcessor;
    private final BalanceTransactionProcessor balanceTransactionProcessor;

    public TransactionProcessor getTransactionProcessorByType(String type) {
        switch (type) {
            case "income":
                return incomeTransactionProcessor;
            case "expense":
                return expenseTransactionProcessor;
            case "balance":
                return balanceTransactionProcessor;
            default:
                return null;
        }
    }
}
