package com.revolut.test.domain;

import com.fasterxml.jackson.annotation.JsonUnwrapped;

public class MoneyTransferInfo {

    private long fromAccountId;

    private long toAccountId;

    @JsonUnwrapped
    private Money amount;

    public MoneyTransferInfo() {
        // Only for serialization purpose.
    }

    public MoneyTransferInfo(long fromAccountId, long toAccountId, Money amount) {
        this.fromAccountId = fromAccountId;
        this.toAccountId = toAccountId;
        this.amount = amount;
    }

    public long getFromAccountId() {
        return fromAccountId;
    }

    public long getToAccountId() {
        return toAccountId;
    }

    public Money getAmount() {
        return amount;
    }
}
