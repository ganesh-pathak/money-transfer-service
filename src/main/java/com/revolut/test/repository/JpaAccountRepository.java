package com.revolut.test.repository;

import com.revolut.test.domain.Account;

import javax.inject.Singleton;

@Singleton
public class JpaAccountRepository extends JpaPersistenceRepository<Account> {

    @Override
    public Account find(long id) {
        return getEntityManager().find(Account.class, id);
    }

    public Account createAccount(Account account) {
        beginTransaction();
        Account newAccount = save(account);
        commit();
        return newAccount;
    }
}
