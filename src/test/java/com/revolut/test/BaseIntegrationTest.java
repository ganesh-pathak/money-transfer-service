package com.revolut.test;

import com.revolut.test.controller.AccountController;
import com.revolut.test.controller.MoneyTransferController;
import com.revolut.test.repository.JpaAccountRepository;
import com.revolut.test.service.AccountService;
import com.revolut.test.service.TransferService;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.AfterClass;
import org.junit.BeforeClass;

import javax.inject.Singleton;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import java.net.URI;

public class BaseIntegrationTest {

    static final String BASE_URI = "http://localhost:8085/moneytransfer";
    protected static WebTarget target;
    private static HttpServer httpServer;

    @BeforeClass
    public static void setUp() throws Exception {
        final ResourceConfig rc = new ResourceConfig()
                .packages("com.revolut.test")
                .registerClasses(MoneyTransferController.class, AccountController.class)
                .register(new AbstractBinder() {
                    @Override
                    protected void configure() {
                        bindAsContract(TransferService.class).in(Singleton.class);
                        bindAsContract(AccountService.class).in(Singleton.class);
                        bindAsContract(JpaAccountRepository.class).in(Singleton.class);
                    }
                }).register(JacksonFeature.class);
        httpServer = GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);

        Client client = ClientBuilder.newClient();
        client.register(JacksonFeature.class);
        target = client.target(BASE_URI);
    }

    @AfterClass
    public static void tearDown() {
        httpServer.shutdownNow();
    }
}