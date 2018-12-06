package com.revolut.test.controller;

import com.revolut.test.domain.AccountInfo;
import com.revolut.test.service.AccountService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/account")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AccountController {

    @Inject
    private AccountService accountService;

    @POST
    @Path("/create")
    public Long createAccount(AccountInfo accountInfo) {
        return accountService.createAccount(accountInfo);
    }

    @GET
    @Path("/{accountId}")
    public AccountInfo findAccount(@PathParam("accountId") Long accountId) {
        return accountService.findAccount(accountId);
    }

}
