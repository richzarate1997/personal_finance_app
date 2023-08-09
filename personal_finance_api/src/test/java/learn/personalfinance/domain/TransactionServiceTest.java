package learn.personalfinance.domain;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import learn.personalfinance.data.TransactionRepository;
import learn.personalfinance.data.UserRepository;
import learn.personalfinance.models.Transaction;
import learn.personalfinance.models.TransactionType;
import learn.personalfinance.models.User;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@Transactional
public class TransactionServiceTest {

    @InjectMocks
    private TransactionService transactionService;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private UserRepository userRepository;

    @Test
    public void testGetTransactions() {
        Transaction transaction1 = new Transaction();
        Transaction transaction2 = new Transaction();
        when(transactionRepository.findAll()).thenReturn(Arrays.asList(transaction1, transaction2));

        List<Transaction> transactions = transactionService.getTransactions();

        assertEquals(2, transactions.size());
        assertEquals(transaction1, transactions.get(0));
        assertEquals(transaction2, transactions.get(1));
    }

    @Test
    @Rollback
    public void testCreateTransaction() {
        Transaction transaction = new Transaction();
        transaction.setType(TransactionType.INCOME);
        transaction.setAmount(new BigDecimal("1000.00"));

        User user = new User();
        user.setBalance(new BigDecimal("500.00"));
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(transactionRepository.save(transaction)).thenReturn(transaction);

        Transaction createdTransaction = transactionService.createTransaction(transaction, userId);

        assertEquals(transaction, createdTransaction);
        assertEquals(new BigDecimal("1500.00"), user.getBalance());
        verify(userRepository).save(user);
        verify(transactionRepository).save(transaction);
    }

    @Test
    public void testGetTransaction() {
        Long transactionId = 1L;
        Transaction transaction = new Transaction();
        when(transactionRepository.findById(transactionId)).thenReturn(Optional.of(transaction));

        Transaction result = transactionService.getTransaction(transactionId);

        assertEquals(transaction, result);
    }

    @Test
    public void testGetTransactionNotFound() {
        Long transactionId = 1L;
        when(transactionRepository.findById(transactionId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            transactionService.getTransaction(transactionId);
        });
    }

    @Test
    public void testUpdateTransaction() {
        Long transactionId = 1L;
        Transaction existingTransaction = new Transaction();
        Transaction updatedTransaction = new Transaction();
        updatedTransaction.setType(TransactionType.INCOME);
        updatedTransaction.setAmount(new BigDecimal("200.00"));
        updatedTransaction.setDate(LocalDate.now());
        // ... Set other properties as needed

        when(transactionRepository.findById(transactionId)).thenReturn(Optional.of(existingTransaction));
        when(transactionRepository.save(existingTransaction)).thenReturn(updatedTransaction);

        Transaction result = transactionService.updateTransaction(transactionId, updatedTransaction);

        assertEquals(TransactionType.INCOME, result.getType());
        assertEquals(new BigDecimal("200.00"), result.getAmount());
        assertEquals(updatedTransaction.getDate(), result.getDate());
        // ... Assert other properties as needed

        verify(transactionRepository).save(existingTransaction); // Verify that the save method was called
    }
    @Test
    public void testDeleteTransaction_WhenTransactionExists() {
        Long transactionId = 1L;
        Transaction existingTransaction = mock(Transaction.class); // Create a mock for existingTransaction
        User user = new User();
        user.setBalance(new BigDecimal("1000.00"));

        when(existingTransaction.getType()).thenReturn(TransactionType.EXPENSE); // Define behavior for getType
        when(existingTransaction.getAmount()).thenReturn(new BigDecimal("100.00")); // Define behavior for getAmount
        when(existingTransaction.getUser()).thenReturn(user); // Define the behavior of getUser() on the mock
        when(transactionRepository.findById(transactionId)).thenReturn(Optional.of(existingTransaction));

        transactionService.deleteTransaction(transactionId);

        verify(userRepository).save(user); // Verify that the user is saved
        verify(transactionRepository).deleteById(transactionId); // Verify that the transaction is deleted
        assertEquals(new BigDecimal("1100.00"), user.getBalance()); // Check that the balance was updated correctly
    }

    @Test
    public void testDeleteTransaction_WhenTransactionDoesNotExist() {
        Long transactionId = 1L;

        when(transactionRepository.findById(transactionId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            transactionService.deleteTransaction(transactionId);
        });

        assertEquals("Transaction not found with id " + transactionId, exception.getMessage());
        verify(transactionRepository, never()).deleteById(transactionId); // Verify that delete was never called
    }
    @Test
    public void testCreateTransaction_WhenExpense() {
        Long userId = 1L;
        BigDecimal initialBalance = new BigDecimal("1000.00");
        BigDecimal transactionAmount = new BigDecimal("100.00");

        User user = new User();
        user.setBalance(initialBalance);

        Transaction transaction = new Transaction();
        transaction.setType(TransactionType.EXPENSE);
        transaction.setAmount(transactionAmount);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(transactionRepository.save(transaction)).thenReturn(transaction);

        Transaction result = transactionService.createTransaction(transaction, userId);

        assertEquals(transaction, result);
        assertEquals(initialBalance.subtract(transactionAmount), user.getBalance());
    }
    @Test
    public void testCreateTransaction_WhenIncome() {
        Long userId = 1L;
        BigDecimal initialBalance = new BigDecimal("1000.00");
        BigDecimal transactionAmount = new BigDecimal("100.00");

        User user = new User();
        user.setBalance(initialBalance);

        Transaction transaction = new Transaction();
        transaction.setType(TransactionType.INCOME);
        transaction.setAmount(transactionAmount);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(transactionRepository.save(transaction)).thenReturn(transaction);

        Transaction result = transactionService.createTransaction(transaction, userId);

        assertEquals(transaction, result);
        assertEquals(initialBalance.add(transactionAmount), user.getBalance());
    }

}
