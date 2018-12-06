package com.revolut.test;

import com.revolut.test.domain.AccountInfo;
import com.revolut.test.domain.Money;
import com.revolut.test.domain.MoneyTransferInfo;
import com.revolut.test.domain.MoneyTransferStatus;
import com.revolut.test.domain.builder.AccountInfoBuilder;
import com.revolut.test.domain.builder.MoneyTransferInfoBuilder;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import java.math.BigDecimal;

import static com.revolut.test.domain.MoneyTransferStatus.COMPLETED;
import static com.revolut.test.domain.MoneyTransferStatus.FAILED;
import static com.revolut.test.domain.MoneyTransferStatus.FAILED_DUE_TO_INSUFFICIENT_FUNDS;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class MoneyTransferIntegrationTest extends BaseIntegrationTest {

    private Long fromAccountId;

    private Long toAccountId;

    @Before
    public void setup() {
        AccountInfo fromAccount = AccountInfoBuilder.builder()
                .balance(new Money(BigDecimal.valueOf(600)))
                .currencyCode("EUR")
                .build();

        AccountInfo toAccount = AccountInfoBuilder.builder()
                .balance(new Money(BigDecimal.valueOf(300)))
                .currencyCode("EUR")
                .build();

        fromAccountId = target.path("/account/create")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(fromAccount), Long.class);

        toAccountId = target.path("/account/create")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(toAccount), Long.class);
    }

    @Test
    public void shouldTransferMoneyBetweenAccounts() {
        // given
        MoneyTransferInfo moneyTransferInfo = MoneyTransferInfoBuilder.builder()
                .fromAccountId(fromAccountId)
                .toAccountId(toAccountId)
                .amount(new Money(BigDecimal.valueOf(100)))
                .build();
        // when
        MoneyTransferStatus moneyTransferStatus = target.path("/transfer")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(moneyTransferInfo), MoneyTransferStatus.class);
        // then
        assertThat(moneyTransferStatus, is(COMPLETED));
        assertThat(getAccount(fromAccountId).getBalance().getAmount(), is(BigDecimal.valueOf(500)));
        assertThat(getAccount(toAccountId).getBalance().getAmount(), is(BigDecimal.valueOf(400)));
    }

    @Test
    public void shouldFailTransactionWhenInsufficientBalanceInFromAccount() {
        // given
        MoneyTransferInfo moneyTransferInfo = MoneyTransferInfoBuilder.builder()
                .fromAccountId(fromAccountId)
                .toAccountId(toAccountId)
                .amount(new Money(BigDecimal.valueOf(800)))
                .build();
        // when
        MoneyTransferStatus moneyTransferStatus = target.path("/transfer")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(moneyTransferInfo), MoneyTransferStatus.class);
        // then
        assertThat(moneyTransferStatus, is(FAILED_DUE_TO_INSUFFICIENT_FUNDS));
        assertThat(getAccount(fromAccountId).getBalance().getAmount(), is(BigDecimal.valueOf(600.0)));
        assertThat(getAccount(toAccountId).getBalance().getAmount(), is(BigDecimal.valueOf(300.0)));
    }

    @Test
    public void shouldFailTransactionWhenInvalidFromAccount() {
        // given
        MoneyTransferInfo moneyTransferInfo = MoneyTransferInfoBuilder.builder()
                .fromAccountId(123)
                .toAccountId(toAccountId)
                .amount(new Money(BigDecimal.valueOf(700)))
                .build();
        // when
        MoneyTransferStatus moneyTransferStatus = target.path("/transfer")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(moneyTransferInfo), MoneyTransferStatus.class);
        // then
        assertThat(moneyTransferStatus, is(FAILED));
    }

    @Test
    public void shouldFailTransactionWhenInvalidToAccount() {
        // given
        MoneyTransferInfo moneyTransferInfo = MoneyTransferInfoBuilder.builder()
                .fromAccountId(fromAccountId)
                .toAccountId(123)
                .amount(new Money(BigDecimal.valueOf(700)))
                .build();
        // when
        MoneyTransferStatus moneyTransferStatus = target.path("/transfer")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(moneyTransferInfo), MoneyTransferStatus.class);
        // then
        assertThat(moneyTransferStatus, is(FAILED));
    }

    private AccountInfo getAccount(long accountId) {
        return target.path("/account/" + accountId)
                .request(MediaType.APPLICATION_JSON)
                .get(AccountInfo.class);
    }
}
