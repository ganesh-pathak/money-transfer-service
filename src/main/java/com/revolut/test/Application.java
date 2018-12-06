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

import javax.inject.Singleton;
import java.io.IOException;
import java.net.URI;

public class Application {

    private static final String BASE_URI = "http://localhost:8082/moneytransfer";

    private static HttpServer startServer() {
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
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
    }

    public static void main(String[] args) throws IOException {
        final HttpServer server = startServer();
        System.out.println("Money transfer service started. Base URI to access it is: " + BASE_URI);
        System.out.println("Hit enter to stop this service.");
        System.in.read();
        server.shutdownNow();
    }

}
