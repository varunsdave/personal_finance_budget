package com.varunsdave.personafinance.budget.controller;

import com.varunsdave.personafinance.budget.model.Transaction;
import com.varunsdave.personafinance.budget.model.UiTransaction;
import com.varunsdave.personafinance.budget.service.fileupload.FileUploadService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/fileUpload")
public class FileUploadController {

    private final FileUploadService fileUploadService;

    @PostMapping("/csv/account/{accountId}")
    public ResponseEntity<List<Transaction>> uploadCsvFile(
            @PathVariable String accountId, @RequestBody List<UiTransaction> transactions
    ) {
        List<Transaction> transactionsList = fileUploadService.processFile(transactions, accountId);
        System.out.println(transactionsList.size());
        return ResponseEntity.ok(null);
    }
}
