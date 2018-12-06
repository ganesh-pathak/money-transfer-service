package com.revolut.test.domain.builder;

import com.revolut.test.domain.AccountInfo;
import com.revolut.test.domain.Money;

public class AccountInfoBuilder {

    private Money balance;
    private String currencyCode;

    public AccountInfoBuilder balance(Money balance) {
        this.balance = balance;
        return this;
    }

    public AccountInfoBuilder currencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
        return this;
    }

    public static AccountInfoBuilder builder() {
        return new AccountInfoBuilder();
    }

    public AccountInfo build() {
        return new AccountInfo(balance, currencyCode);
    }

}
