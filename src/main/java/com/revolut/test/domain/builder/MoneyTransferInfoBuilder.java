package com.revolut.test.domain.builder;

import com.revolut.test.domain.Money;
import com.revolut.test.domain.MoneyTransferInfo;

public class MoneyTransferInfoBuilder {

    private long fromAccountId;
    private long toAccountId;
    private Money amount;

    public static MoneyTransferInfoBuilder builder() {
        return new MoneyTransferInfoBuilder();
    }

    public MoneyTransferInfoBuilder fromAccountId(long fromAccountId) {
        this.fromAccountId = fromAccountId;
        return this;
    }

    public MoneyTransferInfoBuilder toAccountId(long toAccountId) {
        this.toAccountId = toAccountId;
        return this;
    }

    public MoneyTransferInfoBuilder amount(Money amount) {
        this.amount = amount;
        return this;
    }

    public MoneyTransferInfo build() {
        return new MoneyTransferInfo(fromAccountId, toAccountId, amount);
    }
}
