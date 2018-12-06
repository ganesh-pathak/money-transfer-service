package com.revolut.test.service;

import com.revolut.test.domain.Account;
import com.revolut.test.domain.AccountInfo;
import com.revolut.test.domain.builder.AccountInfoBuilder;
import com.revolut.test.repository.JpaAccountRepository;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class AccountService {

    @Inject
    private JpaAccountRepository jpaAccountRepository;

    public long createAccount(AccountInfo accountInfo) {
        Account account = jpaAccountRepository.createAccount(new Account(accountInfo.getBalance(), accountInfo.getCurrency()));
        return account.getAccountNumber();
    }

    public AccountInfo findAccount(Long accountId) {
        Account account = jpaAccountRepository.find(accountId);
        return AccountInfoBuilder.builder()
                .balance(account.getBalance())
                .currencyCode(account.getCurrencyCode())
                .build();
    }
}
