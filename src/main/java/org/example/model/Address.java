package org.example.model;

import jakarta.persistence.*;

@Entity
@Table(name = "client_address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "client_address_id")
    private Long id;

    @Column(name = "city")
    private String city;
    @Column(name = "street")
    private String street;
    @Column(name = "house_nuber")
    private String houseNumber;

    public Address() {
    }

    public Address(String city, String street, String houseNumber) {
        this.city = city;
        this.street = street;
        this.houseNumber = houseNumber;
    }

    public String getAddressInfo() {
        return "City: " + city
                + ", Street: " + street
                + ", Number: " + houseNumber;
    }

    public String getCity() {
        return city;
    }

    public String getStreet() {
        return street;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setCity(String city) {
        if (!city.isBlank()) {
            this.city = city;
        }
    }

    public void setStreet(String street) {
        if (!street.isBlank()) {
            this.street = street;
        }
    }

    public void setHouseNumber(String number) {
        if (!number.isBlank()) {
            this.houseNumber = number;
        }
    }
}
