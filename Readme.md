# Money Transfer

Money Transfer is a service providing money transfers between accounts.

## Technology stack

Language: Java 8

Build Tool: Maven

Web-service: 
 - JAX-RS
 - Jersey 2
 - Jackson
  
Persistence:
 - JPA
 - Hibernate
 - H2 in-memory db
 
Unit & Integration Testing: 
 - JUnit 4  

## Build

Pre-requisites:
 - Java 1.8
 - Maven

To build the project run command:

```
mvn clean install
```

This also runs following tests.

com.revolut.test.MoneyTransferIntegrationTest (Integration tests)

com.revolut.test.controller.AccountControllerTest
com.revolut.test.controller.MoneyTransferControllerTest
com.revolut.test.domain.AccountTest
com.revolut.test.service.AccountServiceTest
com.revolut.test.service.TransferServiceTest

## Run 

To run the server from project directory:

```
java -jar target/money-transfer-service-jar-with-dependencies.jar 
```

Hit Enter to shutdown the server.

## API Usage

### Create new account

```
curl -XPOST -H "Content-Type:application/json" -d '{"amount": 1000.00, "currency": "EUR"}' http://localhost:8082/moneytransfer/account/create
```

Response: Account number

```json
{
    1
}
```

### Get account details

Request: 
```
curl -XGET http://localhost:8082/moneytransfer/account/1
```

Response: Account balance and currency
```json
{
 "amount":1000.0,
 "currency":"EUR"
}
```

### Transfer money

Transfer money from account 1 to account 2 in amount of 100

```
curl -XPOST -H "Content-Type: application/json" -d '{"fromAccountId": 1, "amount": 100, "toAccountId": 2}' http://localhost:8082/moneytransfer/transfer
```

Response: Transfer status (FAILED, COMPLETED or FAILED_DUE_TO_INSUFFICIENT_FUNDS)

```json
{
 COMPLETED
}


