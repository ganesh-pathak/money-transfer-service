package com.revolut.test.service;

import com.revolut.test.domain.Account;
import com.revolut.test.domain.AccountInfo;
import com.revolut.test.domain.Money;
import com.revolut.test.repository.JpaAccountRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class AccountServiceTest {

    @InjectMocks
    private AccountService accountService;

    @Mock
    private JpaAccountRepository jpaAccountRepository;

    @Test
    public void shouldDelegateToJpaAccountRepositoryAndCreateAccount() {
        // given
        Account account = mock(Account.class);
        given(account.getAccountNumber()).willReturn(123L);
        given(jpaAccountRepository.createAccount(any(Account.class))).willReturn(account);
        // when
        long accountNumber = accountService.createAccount(mock(AccountInfo.class));
        // then
        assertThat(accountNumber, is(123L));
    }

    @Test
    public void shouldDelegateToJpaAccountRepositoryAndFindAccount() {
        // given
        Account account = mock(Account.class);
        given(account.getBalance()).willReturn(Money.of("100"));
        given(account.getCurrencyCode()).willReturn("EUR");
        given(jpaAccountRepository.find(123L)).willReturn(account);
        // when
        AccountInfo accountInfo = accountService.findAccount(123L);
        // then
        assertThat(accountInfo.getBalance().getAmount(), is(BigDecimal.valueOf(100)));
        assertThat(accountInfo.getCurrency(), is("EUR"));
    }
}