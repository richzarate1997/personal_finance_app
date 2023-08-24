package learn.personalfinance.domain;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import learn.personalfinance.data.RecurringTransactionRepository;
import learn.personalfinance.models.RecurringTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    @Test
    public void testGetRecurringTransactionWhenIdExists() {
        RecurringTransaction mockRecurringTransaction = new RecurringTransaction();
        // ... Set properties for mockRecurringTransaction as needed
        Long id = 1L; // Assuming this is the ID you want to use
        when(recurringTransactionRepository.findById(id)).thenReturn(Optional.of(mockRecurringTransaction));

        RecurringTransaction result = recurringTransactionService.getRecurringTransaction(id);

        // Verify that the result is the expected recurring transaction
        assertEquals(mockRecurringTransaction, result);
    }

    @Test
    public void testGetRecurringTransactionWhenIdDoesNotExist() {
        Long id = 1L; // Assuming this is the ID you want to use
        when(recurringTransactionRepository.findById(id)).thenReturn(Optional.empty());

        // Verify that calling the method with an ID that doesn't exist throws the expected exception
        assertThrows(ResourceNotFoundException.class, () -> recurringTransactionService.getRecurringTransaction(id));
    }

    @Test
    public void testCreateRecurringTransaction() {
        RecurringTransaction recurringTransaction = new RecurringTransaction();
        // ... Set properties for recurringTransaction as needed

        when(recurringTransactionRepository.save(any(RecurringTransaction.class)))
                .thenAnswer(invocation -> {
                    RecurringTransaction rt = invocation.getArgument(0);
                    return rt;
                });

        RecurringTransaction createdRecurringTransaction = recurringTransactionService.createRecurringTransaction(recurringTransaction);

        // Verify that the recurring transaction was saved with the correct properties
        assertNotNull(createdRecurringTransaction);
        // ... Assert other properties as needed

        // Verify that the recurring transaction was saved to the repository
        verify(recurringTransactionRepository).save(recurringTransaction);
    }
    @Test
    public void testUpdateRecurringTransaction() {
        Long id = 1L;
        RecurringTransaction existingRecurringTransaction = new RecurringTransaction();
        // ... Set properties for existingRecurringTransaction as needed

        RecurringTransaction newDetails = new RecurringTransaction();
        // ... Set new details for newDetails (these will be used to update the existing transaction)

        when(recurringTransactionRepository.findById(id)).thenReturn(Optional.of(existingRecurringTransaction));
        when(recurringTransactionRepository.save(any(RecurringTransaction.class))).thenAnswer(invocation -> invocation.getArgument(0));

        RecurringTransaction updatedRecurringTransaction = recurringTransactionService.updateRecurringTransaction(id, newDetails);

        // Verify that the existing recurring transaction was updated with the correct properties
        assertEquals(newDetails.getAmount(), updatedRecurringTransaction.getAmount());
        // ... Assert other updated properties as needed
    }
    @Test
    public void testDeleteRecurringTransaction() {
        Long id = 1L;
        RecurringTransaction existingRecurringTransaction = new RecurringTransaction();
        // ... Set properties for existingRecurringTransaction as needed

        when(recurringTransactionRepository.findById(id)).thenReturn(Optional.of(existingRecurringTransaction));

        recurringTransactionService.deleteRecurringTransaction(id);

        // Verify that the delete method was called with the correct recurring transaction
        verify(recurringTransactionRepository).delete(existingRecurringTransaction);
    }
    @Test
    public void testUpdateRecurringTransaction_NotFound() {
        Long id = 1L;
        RecurringTransaction updatedRecurringTransaction = new RecurringTransaction();
        // ... Set properties for updatedRecurringTransaction as needed

        when(recurringTransactionRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            recurringTransactionService.updateRecurringTransaction(id, updatedRecurringTransaction);
        });

        verify(recurringTransactionRepository, never()).save(any(RecurringTransaction.class));
    }
    @Test
    public void testDeleteRecurringTransaction_NotFound() {
        Long id = 1L;

        when(recurringTransactionRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            recurringTransactionService.deleteRecurringTransaction(id);
        });

        verify(recurringTransactionRepository, never()).delete(any(RecurringTransaction.class));
    }



}
