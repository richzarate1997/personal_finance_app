package learn.personalfinance.data;

import learn.personalfinance.models.RecurringTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import learn.personalfinance.models.RecurringTransaction;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RecurringTransactionRepository extends JpaRepository<RecurringTransaction, Long> {
    List<RecurringTransaction> findByNextDateLessThanEqual(LocalDate date);
    List<RecurringTransaction> findByUserId(Long userId);
}

