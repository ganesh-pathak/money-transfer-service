package com.revolut.test.domain;

import javax.persistence.Embeddable;
import java.math.BigDecimal;
import java.util.Objects;

@Embeddable
public class Money {
    private BigDecimal amount;

    public static Money of(String amount) {
        return new Money(new BigDecimal(amount));
    }

    public Money(BigDecimal amount) {
        this.amount = amount;
    }

    public Money() {
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Money add(Money money) {
        return new Money(getAmount().add(money.getAmount()));
    }

    public Money subtract(Money money) {
        return new Money(getAmount().subtract(money.getAmount()));
    }

    public Money negate() {
        return new Money(getAmount().negate());
    }

    public boolean isLessThan(Money amount) {
        return getAmount().compareTo(amount.getAmount()) < 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Money money = (Money) o;
        return Objects.equals(amount, money.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount);
    }
}
