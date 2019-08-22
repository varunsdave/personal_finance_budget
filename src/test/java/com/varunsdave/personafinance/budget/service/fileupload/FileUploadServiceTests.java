package com.varunsdave.personafinance.budget.service.fileupload;

import com.varunsdave.personafinance.budget.model.UiTransaction;
import com.varunsdave.personafinance.budget.service.transactions.TransactionService;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class FileUploadServiceTests {

    @Mock
    private TransactionService transactionService;

    @InjectMocks
    private FileUploadService fileUploadService;

    private EasyRandom random;

    @BeforeEach
    public void setup() {
        random = new EasyRandom();
    }

    @Test
    public void testUploadFileService() {
        final List<UiTransaction> transactionList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            transactionList.add(random.nextObject(UiTransaction.class));
        }
        final String accountId = random.nextObject(String.class);

        fileUploadService.processFile(transactionList, accountId);

        Mockito.verify(transactionService, Mockito.times(1)).uploadTransactions(transactionList, accountId);


    }
}
