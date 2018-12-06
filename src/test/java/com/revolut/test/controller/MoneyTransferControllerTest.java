package com.revolut.test.controller;

import com.revolut.test.domain.MoneyTransferInfo;
import com.revolut.test.service.TransferService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class MoneyTransferControllerTest {

    @InjectMocks
    private MoneyTransferController moneyTransferController;

    @Mock
    private TransferService transferService;

    @Test
    public void shouldDelegateToTransferServiceWhenTransfer() {
        // given
        MoneyTransferInfo moneyTransferInfo = mock(MoneyTransferInfo.class);
        // when
        transferService.transfer(moneyTransferInfo);
        // then
        verify(transferService).transfer(moneyTransferInfo);
    }
}