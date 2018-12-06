package com.revolut.test.domain;

import javax.persistence.*;

@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Embedded
    private Money amount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accountNumber")
    private Account account;

    public Transaction() {
        // for hibernate use only
    }

    public Transaction(Account account, Money amount) {
        this.amount = amount;
        this.account = account;
    }

    public long getId() {
        return id;
    }

    public Money getAmount() {
        return amount;
    }

    public Account getAccount() {
        return account;
    }
}
