package com.revolut.test.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Account {

    @Id
    @GeneratedValue
    private long accountNumber;

    private Money balance;

    private String currencyCode;

    private AccountStatus accountStatus;

    @Version
    @Column(name = "VERSION")
    private Integer version;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Transaction> transactions = new ArrayList<Transaction>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private Customer customer;

    public Account() {
        // for hibernate use only
    }

    public Account(Money balance, String currencyCode) {
        this.balance = balance;
        this.currencyCode = currencyCode;
        this.accountStatus = AccountStatus.PENDING_CUSTOMER_VALIDATION;
    }

    public long getAccountNumber() {
        return accountNumber;
    }

    public Money getBalance() {
        return balance;
    }

    public void setBalance(Money balance) {
        this.balance = balance;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public AccountStatus getAccountStatus() {
        return accountStatus;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void transfer(Money amount, Account toAccount) {
        if (getBalance().isLessThan(amount)) {
            throw new IllegalStateException("Account " + getAccountNumber() + " has insufficient funds.");
        }
        withdraw(amount);
        toAccount.deposit(amount);
    }

    public void deposit(Money amount) {
        addTransaction(amount);
        setBalance(getBalance().add(amount));
    }

    public void withdraw(Money amount) {
        addTransaction(amount.negate());
        setBalance(getBalance().subtract(amount));
    }

    private void addTransaction(Money amount) {
        getTransactions().add(new Transaction(this, amount));
    }
}
