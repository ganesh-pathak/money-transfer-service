package com.revolut.test.domain;

import javax.persistence.Embeddable;

@Embeddable
public class Address {
    private String street1;
    private String street2;
    private String city;
    private String state;
    private String country;
    private String zipCode;

    public Address() {
        // for hibernate use only
    }

    public Address(String street1, String street2, String city, String state, String country, String zipCode) {
        this.street1 = street1;
        this.street2 = street2;
        this.city = city;
        this.state = state;
        this.country = country;
        this.zipCode = zipCode;
    }

    public String getStreet1() {
        return street1;
    }

    public String getStreet2() {
        return street2;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getCountry() {
        return country;
    }

    public String getZipCode() {
        return zipCode;
    }
}
