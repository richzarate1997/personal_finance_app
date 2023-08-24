package learn.personalfinance.data;

import learn.personalfinance.domain.RecurringTransactionService;
import learn.personalfinance.models.RecurringTransaction;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Sql(scripts = {"/personal_finance_schema_test.sql"})
public class RecurringTransactionRepositoryTest {

    @Autowired
    private RecurringTransactionService recurringTransactionService;

    @Autowired
    private RecurringTransactionRepository recurringTransactionRepository;

    @SpringBootTest
    @Transactional
    public class RecurringTransactionServiceIntegrationTest {

        @Autowired
        private RecurringTransactionService recurringTransactionService;

        @Autowired
        private RecurringTransactionRepository recurringTransactionRepository;

        @Test
        public void testCreateRecurringTransaction() {
            RecurringTransaction recurringTransaction = new RecurringTransaction();
            // ... Set properties for recurringTransaction as needed

            RecurringTransaction createdRecurringTransaction = recurringTransactionService.createRecurringTransaction(recurringTransaction);

            // Verify that the recurring transaction was saved with the correct properties
            assertNotNull(createdRecurringTransaction);
            // ... Assert other properties as needed

            // Verify that the recurring transaction was saved to the repository
            assertEquals(1, recurringTransactionRepository.findAll().size());
        }

        @Test
        public void testUpdateRecurringTransaction() {
            // Create a RecurringTransaction
            RecurringTransaction rt = new RecurringTransaction();
            // ... Set properties
            rt = recurringTransactionRepository.save(rt);

            // Create updated RecurringTransaction
            RecurringTransaction updatedRt = new RecurringTransaction();
            // ... Set new properties
            updatedRt.setId(rt.getId());

            // Call the update method
            RecurringTransaction result = recurringTransactionService.updateRecurringTransaction(rt.getId(), updatedRt);

            // Validate the result
            // ... assertions to check if the updated details are correct
        }

        @Test
        public void testDeleteRecurringTransaction() {
            // Create a RecurringTransaction
            RecurringTransaction rt = new RecurringTransaction();
            // ... Set properties
            rt = recurringTransactionRepository.save(rt);

            // Call the delete method
            recurringTransactionService.deleteRecurringTransaction(rt.getId());

            // Validate the result
            assertFalse(recurringTransactionRepository.findById(rt.getId()).isPresent());
        }
    }


}
