package com.revolut.test.service;

import com.revolut.test.domain.Account;
import com.revolut.test.domain.Money;
import com.revolut.test.domain.MoneyTransferInfo;
import com.revolut.test.domain.MoneyTransferStatus;
import com.revolut.test.repository.JpaAccountRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static com.revolut.test.domain.MoneyTransferStatus.*;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class TransferServiceTest {
    
    @InjectMocks
    private TransferService transferService;
    
    @Mock
    private JpaAccountRepository jpaAccountRepository;

    private MoneyTransferInfo moneyTransferInfo;

    @Before
    public void setUp() {
        moneyTransferInfo = mock(MoneyTransferInfo.class);
        given(moneyTransferInfo.getFromAccountId()).willReturn(123L);
        given(moneyTransferInfo.getToAccountId()).willReturn(456L);
        given(moneyTransferInfo.getAmount()).willReturn(Money.of("1000"));
    }

    @Test
    public void shouldCompleteMoneyTransferBetweenAccounts() {
        // given
        Account fromAccount = mock(Account.class);
        Account toAccount = mock(Account.class);
        given(jpaAccountRepository.find(123L)).willReturn(fromAccount);
        given(jpaAccountRepository.find(456L)).willReturn(toAccount);
        // when
        MoneyTransferStatus transferStatus = transferService.transfer(moneyTransferInfo);
        // then
        verify(jpaAccountRepository).beginTransaction();
        verify(jpaAccountRepository).lock(fromAccount);
        verify(jpaAccountRepository).lock(toAccount);
        verify(fromAccount).transfer(Money.of("1000"), toAccount);
        verify(jpaAccountRepository).save(fromAccount);
        verify(jpaAccountRepository).save(toAccount);
        verify(jpaAccountRepository).commit();
        assertThat(transferStatus, is(COMPLETED));
    }

    @Test
    public void shouldRollbackTransferWhenInsufficientFunds() {
        // given
        Account fromAccount = new Account(Money.of("100"), "EUR");
        Account toAccount = mock(Account.class);
        given(jpaAccountRepository.find(123L)).willReturn(fromAccount);
        given(jpaAccountRepository.find(456L)).willReturn(toAccount);
        // when
        MoneyTransferStatus transferStatus = transferService.transfer(moneyTransferInfo);
        // then
        verify(jpaAccountRepository).beginTransaction();
        verify(jpaAccountRepository).lock(fromAccount);
        verify(jpaAccountRepository).lock(toAccount);
        verify(jpaAccountRepository).rollback();
        assertThat(transferStatus, is(FAILED_DUE_TO_INSUFFICIENT_FUNDS));
    }

    @Test
    public void shouldRollbackTransferWhenInvalidFromAccount() {
        // given
        Account toAccount = mock(Account.class);
        given(jpaAccountRepository.find(123L)).willThrow(IllegalArgumentException.class);
        given(jpaAccountRepository.find(456L)).willReturn(toAccount);
        // when
        MoneyTransferStatus transferStatus = transferService.transfer(moneyTransferInfo);
        // then
        verify(jpaAccountRepository).beginTransaction();
        verify(jpaAccountRepository).rollback();
        assertThat(transferStatus, is(FAILED));
    }

    @Test
    public void shouldRollbackTransferWhenInvalidToAccount() {
        // given
        Account fromAccount = mock(Account.class);
        given(jpaAccountRepository.find(123L)).willReturn(fromAccount);
        given(jpaAccountRepository.find(456L)).willThrow(IllegalArgumentException.class);
        // when
        MoneyTransferStatus transferStatus = transferService.transfer(moneyTransferInfo);
        // then
        verify(jpaAccountRepository).beginTransaction();
        verify(jpaAccountRepository).rollback();
        assertThat(transferStatus, is(FAILED));
    }
}