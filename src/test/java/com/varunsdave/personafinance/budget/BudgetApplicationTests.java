package com.varunsdave.personafinance.budget;

import com.varunsdave.personafinance.budget.controller.FileUploadController;
import com.varunsdave.personafinance.budget.repository.TransactionRepository;
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


    @Autowired
    private FileUploadController fileUploadController;

    @Test
    public void contextLoads() {
        Assertions.assertThat(fileUploadController).isNotNull();
    }

}
