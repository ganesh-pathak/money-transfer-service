package com.revolut.test.domain;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class AccountTest {

    @Test
    public void shouldTransferMoneyBetweenFromAndToAccounts() {
        // given
        Account fromAccount = new Account(Money.of("500"), "EUR");
        Account toAccount = new Account(Money.of("400"), "EUR");
        // when
        fromAccount.transfer(Money.of("100"), toAccount);
        // then
        assertThat(fromAccount.getBalance(), is(Money.of("400")));
        assertThat(fromAccount.getTransactions().get(0).getAmount(), is(Money.of("-100")));

        assertThat(toAccount.getBalance(), is(Money.of("500")));
        assertThat(toAccount.getTransactions().get(0).getAmount(), is(Money.of("100")));
    }
}