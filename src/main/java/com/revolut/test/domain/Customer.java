package com.revolut.test.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Customer {

    @Id
    @GeneratedValue
    private Long id;

    private String firstName;

    private String lastName;

    private String phoneNumber;

    @Embedded
    private Address address;

    private String ssn;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Account> accounts = new ArrayList<Account>();

    public Customer() {
        // for hibernate use only
    }

    public Customer(String firstName, String lastName, String phoneNumber, Address address, String ssn) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.ssn = ssn;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Address getAddress() {
        return address;
    }

    public String getSsn() {
        return ssn;
    }

    public List<Account> getAccounts() {
        return accounts;
    }
}
