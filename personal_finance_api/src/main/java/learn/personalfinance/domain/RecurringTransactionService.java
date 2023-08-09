package learn.personalfinance.domain;

import learn.personalfinance.data.RecurringTransactionRepository;
import learn.personalfinance.data.TransactionRepository;
import learn.personalfinance.data.UserRepository;
import learn.personalfinance.models.RecurringTransaction;
import learn.personalfinance.models.Transaction;
import learn.personalfinance.models.TransactionType;
import learn.personalfinance.models.User;
import org.hibernate.exception.DataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class RecurringTransactionService {
    @Autowired
    private final RecurringTransactionRepository recurringTransactionRepository;
    public List<RecurringTransaction> findRecurringTransactionsToProcess(LocalDate date) {
        return recurringTransactionRepository.findByNextDateLessThanEqual(date);
    }
    private final TransactionService transactionService;

    @Autowired
    public RecurringTransactionService(RecurringTransactionRepository recurringTransactionRepository,
                                       TransactionService transactionService) {
        this.recurringTransactionRepository = recurringTransactionRepository;
        this.transactionService = transactionService;
    }

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TransactionRepository transactionRepository;

    @Scheduled(cron = "0 0 0 * * ?") // This cron expression will run the method every day at midnight
    public void processRecurringTransactions() {
        LocalDate today = LocalDate.now();

        // Find all recurring transactions where the next date is today or earlier
        List<RecurringTransaction> transactionsToProcess = findRecurringTransactionsToProcess(today);

        for (RecurringTransaction recurringTransaction : transactionsToProcess) { // Fixed here
            generateTransactionFromRecurringTransaction(recurringTransaction);

            // Update the next date for the recurring transaction based on the recurrence period
            LocalDate nextDate = recurringTransaction.getNextDate().plusDays(recurringTransaction.getRecurrencePeriod());
            recurringTransaction.setNextDate(nextDate);

            // Save the updated recurring transaction
            recurringTransactionRepository.save(recurringTransaction);
        }
    }


    private void generateTransactionFromRecurringTransaction(RecurringTransaction recurringTransaction) {
        User user = recurringTransaction.getUser();
        Transaction transaction = new Transaction();
        transaction.setUser(user);
        transaction.setType(recurringTransaction.getType()); // Now this works as both are of type TransactionType
        transaction.setCategory(recurringTransaction.getCategory());
        transaction.setAmount(recurringTransaction.getAmount());
        transaction.setDate(recurringTransaction.getNextDate());
        if (transaction.getType() == TransactionType.EXPENSE) {
            user.setBalance(user.getBalance().subtract(transaction.getAmount()));
        } else if (transaction.getType() == TransactionType.INCOME) {
            user.setBalance(user.getBalance().add(transaction.getAmount()));
        }
        userRepository.save(user);
        transactionRepository.save(transaction);
    }
    public List<RecurringTransaction> getRecurringTransactions() {
        return recurringTransactionRepository.findAll();
    }

    public RecurringTransaction getRecurringTransaction(Long id) {
        return recurringTransactionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("RecurringTransaction not found with id " + id));
    }

    public RecurringTransaction createRecurringTransaction(RecurringTransaction recurringTransaction) {
        return recurringTransactionRepository.save(recurringTransaction);
    }

    public RecurringTransaction updateRecurringTransaction(Long id, RecurringTransaction recurringTransactionDetails) {
        RecurringTransaction recurringTransaction = getRecurringTransaction(id);
        // Copy details from recurringTransactionDetails to recurringTransaction
        // ...
        return recurringTransactionRepository.save(recurringTransaction);
    }

    public void deleteRecurringTransaction(Long id) {
        RecurringTransaction recurringTransaction = getRecurringTransaction(id);
        recurringTransactionRepository.delete(recurringTransaction);
    }
    }

    // other methods as needed









