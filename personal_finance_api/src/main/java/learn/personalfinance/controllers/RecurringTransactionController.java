package learn.personalfinance.controllers;

import learn.personalfinance.domain.RecurringTransactionService;
import learn.personalfinance.models.RecurringTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/recurringTransactions")
public class RecurringTransactionController {

    @Autowired
    private RecurringTransactionService recurringTransactionService;

    @GetMapping
    public List<RecurringTransaction> getRecurringTransactions() {
        return recurringTransactionService.getRecurringTransactions();
    }

    @GetMapping("/{id}")
    public Optional<RecurringTransaction> getRecurringTransaction(@PathVariable Long id) {
        return Optional.ofNullable(recurringTransactionService.getRecurringTransaction(id));
    }

    @PostMapping
    public RecurringTransaction createRecurringTransaction(@RequestBody RecurringTransaction recurringTransaction) {
        return recurringTransactionService.createRecurringTransaction(recurringTransaction);
    }

    @PutMapping("/{id}")
    public RecurringTransaction updateRecurringTransaction(@PathVariable Long id, @RequestBody RecurringTransaction recurringTransaction) {
        return recurringTransactionService.updateRecurringTransaction(id, recurringTransaction);
    }

    @DeleteMapping("/{id}")
    public void deleteRecurringTransaction(@PathVariable Long id) {
        recurringTransactionService.deleteRecurringTransaction(id);
    }
}
