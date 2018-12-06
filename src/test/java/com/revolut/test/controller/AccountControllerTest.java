package com.revolut.test.controller;

import com.revolut.test.domain.AccountInfo;
import com.revolut.test.service.AccountService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class AccountControllerTest {

    @InjectMocks
    private AccountController accountController;

    @Mock
    private AccountService accountService;

    @Test
    public void shouldDelegateToAccountServiceWhenCreateAccount() {
        // given
        AccountInfo accountInfo = mock(AccountInfo.class);
        // when
        accountController.createAccount(accountInfo);
        // then
        verify(accountService).createAccount(accountInfo);
    }

    @Test
    public void shouldDelegateToAccountServiceWhenFindAccount() {
        // given
        Long accountId = 123L;
        // when
        accountController.findAccount(accountId);
        // then
        verify(accountService).findAccount(accountId);
    }
}
