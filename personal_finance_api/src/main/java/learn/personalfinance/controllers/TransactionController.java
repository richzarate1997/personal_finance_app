package learn.personalfinance.controllers;

import learn.personalfinance.domain.TransactionService;
import learn.personalfinance.models.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @GetMapping
    public List<Transaction> getTransactions() {
        return transactionService.getTransactions();
    }

    @GetMapping("/{id}")
    public Transaction getTransaction(@PathVariable Long id) {
        return transactionService.getTransaction(id);
    }

    @PostMapping("/{userId}")
    public Transaction createTransaction(@PathVariable Long userId, @RequestBody Transaction transaction) {
        return transactionService.createTransaction(transaction, userId);
    }


    @PutMapping("/{id}")
    public Transaction updateTransaction(@PathVariable Long id, @RequestBody Transaction transaction) {
        return transactionService.updateTransaction(id, transaction);
    }

    @DeleteMapping("/{id}")
    public void deleteTransaction(@PathVariable Long id) {
        transactionService.deleteTransaction(id);
    }
}

