package learn.personalfinance.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import learn.personalfinance.models.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}

