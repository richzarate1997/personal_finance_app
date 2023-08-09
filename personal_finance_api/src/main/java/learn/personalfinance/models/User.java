package learn.personalfinance.models;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "User")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    private BigDecimal balance;

    @OneToMany(mappedBy="user")
    private List<Transaction> transactions;

    @OneToMany(mappedBy="user")
    private List<RecurringTransaction> recurringTransactions;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public List<RecurringTransaction> getRecurringTransactions() {
        return recurringTransactions;
    }

    public void setRecurringTransactions(List<RecurringTransaction> recurringTransactions) {
        this.recurringTransactions = recurringTransactions;
    }
}
