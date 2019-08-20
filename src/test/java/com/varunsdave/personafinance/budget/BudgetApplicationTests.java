package com.varunsdave.personafinance.budget;

import com.varunsdave.personafinance.budget.controller.FileUploadController;
import com.varunsdave.personafinance.budget.repository.AccountRepository;
import com.varunsdave.personafinance.budget.repository.TransactionRepository;
import com.varunsdave.personafinance.budget.service.fileupload.CsvFileProcessor;
import com.varunsdave.personafinance.budget.service.fileupload.FileUploadService;
import com.varunsdave.personafinance.budget.service.transactions.TransactionProcessor;
import com.varunsdave.personafinance.budget.service.transactions.TransactionService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
@EnableAutoConfiguration
public class BudgetApplicationTests {

//    @MockBean
//    private AccountRepository accountRepository;
//
    @MockBean
    private TransactionRepository transactionRepository;
//
//    @MockBean
//    private TransactionService transactionService;
//
//    @MockBean
//    private FileUploadService fileUploadService;
//
//    @MockBean
//    private CsvFileProcessor csvFileProcessor;
//
//    @MockBean
//    private TransactionProcessor transactionProcessor;

    @Autowired
    private FileUploadController fileUploadController;

    @Test
    public void contextLoads() {
        Assertions.assertThat(fileUploadController).isNotNull();
    }

}
