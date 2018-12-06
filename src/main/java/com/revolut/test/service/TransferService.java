package com.revolut.test.service;

import com.revolut.test.domain.Account;
import com.revolut.test.domain.MoneyTransferInfo;
import com.revolut.test.domain.MoneyTransferStatus;
import com.revolut.test.repository.JpaAccountRepository;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class TransferService {

    @Inject
    private JpaAccountRepository jpaAccountRepository;

    public MoneyTransferStatus transfer(MoneyTransferInfo moneyTransferInfo) {
        try {
            jpaAccountRepository.beginTransaction();
            Account fromAccount = jpaAccountRepository.find(moneyTransferInfo.getFromAccountId());
            Account toAccount = jpaAccountRepository.find(moneyTransferInfo.getToAccountId());

            jpaAccountRepository.lock(fromAccount);
            jpaAccountRepository.lock(toAccount);

            fromAccount.transfer(moneyTransferInfo.getAmount(), toAccount);

            jpaAccountRepository.save(fromAccount);
            jpaAccountRepository.save(toAccount);
            jpaAccountRepository.commit();
        } catch (IllegalStateException e) {
            jpaAccountRepository.rollback();
            return MoneyTransferStatus.FAILED_DUE_TO_INSUFFICIENT_FUNDS;
        } catch (Exception e) {
            jpaAccountRepository.rollback();
            return MoneyTransferStatus.FAILED;
        }
        return MoneyTransferStatus.COMPLETED;
    }
}
