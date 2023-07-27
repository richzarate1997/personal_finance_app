package learn.personalfinance.models;

import javax.persistence.*;
import java.time.LocalDate;

import javax.persistence.*;

@Entity
@Table(name = "RecurringTransaction")
public class RecurringTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private Double amount;

    @Column(nullable = false)
    private LocalDate startDate;  // the date when the first transaction occurs

    @Column(nullable = false)
    private Integer recurrencePeriod;  // period of recurrence in days

    @Column
    private LocalDate endDate;  // date the recurring transaction ends, null if it doesn't stop

    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    private User user;

    @ManyToOne
    @JoinColumn(name="category_id", nullable=false)
    private Category category;

    // getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public Integer getRecurrencePeriod() {
        return recurrencePeriod;
    }

    public void setRecurrencePeriod(Integer recurrencePeriod) {
        this.recurrencePeriod = recurrencePeriod;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}


