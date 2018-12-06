package com.revolut.test.domain;

import com.fasterxml.jackson.annotation.JsonUnwrapped;

public class AccountInfo {

    @JsonUnwrapped
    private Money balance;

    private String currency;

    public AccountInfo() {
        // Only for serialization purpose.
    }

    public AccountInfo(Money balance, String currency) {
        this.balance = balance;
        this.currency = currency;
    }

    public Money getBalance() {
        return balance;
    }

    public String getCurrency() {
        return currency;
    }
}
