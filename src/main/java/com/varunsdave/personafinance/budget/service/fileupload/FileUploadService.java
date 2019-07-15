package com.varunsdave.personafinance.budget.service.fileupload;

import com.varunsdave.personafinance.budget.model.Transaction;
import com.varunsdave.personafinance.budget.model.UiTransaction;
import com.varunsdave.personafinance.budget.service.transactions.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FileUploadService {

    private final TransactionService transactionService;

    public List<Transaction> processFile(List<UiTransaction> fileContents, String accountId) {

        List<Transaction> transactions = fileContents.stream().map((transaction) -> {
            Transaction t = new Transaction(accountId);
            t.setDescription(transaction.getDescription());
            t.setType(transaction.getType().toLowerCase());
            t.setAmount(BigDecimal.valueOf(transaction.getAmount()));
            t.setTransactionDate(new Date(transaction.getTransactionDate()));
            return transactionService.create(t, accountId);
        }).collect(Collectors.toList());

        return transactions;
    }

}
