package learn.personalfinance.domain;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import learn.personalfinance.data.RecurringTransactionRepository;
import learn.personalfinance.models.RecurringTransaction;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class RecurringTransactionServiceTest {

    @InjectMocks
    private RecurringTransactionService recurringTransactionService;

    @Mock
    private RecurringTransactionRepository recurringTransactionRepository;

    @Test
    public void testGetRecurringTransactions() {
        List<RecurringTransaction> mockList = new ArrayList<>();
        mockList.add(new RecurringTransaction());

        when(recurringTransactionRepository.findAll()).thenReturn(mockList);

        List<RecurringTransaction> result = recurringTransactionService.getRecurringTransactions();

        assertEquals(1, result.size());
    }
}

