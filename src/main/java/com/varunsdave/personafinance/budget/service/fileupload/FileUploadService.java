package com.varunsdave.personafinance.budget.service.fileupload;

import com.varunsdave.personafinance.budget.model.Transaction;
import com.varunsdave.personafinance.budget.model.UiTransaction;
import com.varunsdave.personafinance.budget.service.transactions.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@AllArgsConstructor
public class FileUploadService {

    private final TransactionService transactionService;

    public List<Transaction> processFile(List<UiTransaction> fileContents, String accountId) {
        return transactionService.uploadTransactions(fileContents, accountId);
    }

}
