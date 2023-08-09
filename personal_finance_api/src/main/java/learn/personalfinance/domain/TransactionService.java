package learn.personalfinance.domain;

import learn.personalfinance.data.TransactionRepository;
import learn.personalfinance.data.UserRepository;
import learn.personalfinance.models.Transaction;
import learn.personalfinance.models.TransactionType;
import learn.personalfinance.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserRepository userRepository;


    public List<Transaction> getTransactions() {
        return transactionRepository.findAll();
    }

    public Transaction getTransaction(Long id) {
        return transactionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found with id " + id));
    }

    public Transaction createTransaction(Transaction transaction, Long userId) {
        User user = userRepository.findById(userId).get();
        if (transaction.getType() == TransactionType.EXPENSE) {
            user.setBalance(user.getBalance().subtract(transaction.getAmount()));
        } else if (transaction.getType() == TransactionType.INCOME) {
            user.setBalance(user.getBalance().add(transaction.getAmount()));
        }
        userRepository.save(user);
        return transactionRepository.save(transaction);
    }

    public Transaction updateTransaction(Long id, Transaction transaction) {
        Transaction existingTransaction = getTransaction(id);
        existingTransaction.setType(transaction.getType());
        existingTransaction.setAmount(transaction.getAmount());
        existingTransaction.setDate(transaction.getDate());
        existingTransaction.setCategory(transaction.getCategory());
        return transactionRepository.save(existingTransaction);
    }

    public void deleteTransaction(Long id) {
        // Find the transaction to be deleted
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found with id " + id));

        // Get the associated user
        User user = transaction.getUser();

        // Update the user's balance based on the transaction type
        if (transaction.getType() == TransactionType.EXPENSE) {
            // If it was an expense, add the amount back to the balance
            user.setBalance(user.getBalance().add(transaction.getAmount()));
        } else if (transaction.getType() == TransactionType.INCOME) {
            // If it was an income, subtract the amount from the balance
            user.setBalance(user.getBalance().subtract(transaction.getAmount()));
        }

        // Save the updated user
        userRepository.save(user);

        // Delete the transaction
        transactionRepository.deleteById(id);
    }
}

