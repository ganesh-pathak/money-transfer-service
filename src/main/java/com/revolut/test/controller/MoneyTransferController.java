package com.revolut.test.controller;

import com.revolut.test.domain.MoneyTransferInfo;
import com.revolut.test.domain.MoneyTransferStatus;
import com.revolut.test.service.TransferService;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/transfer")
public class MoneyTransferController {

    @Inject
    private TransferService transferService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public MoneyTransferStatus transfer(MoneyTransferInfo moneyTransferInfo) {
        return transferService.transfer(moneyTransferInfo);
    }

}
