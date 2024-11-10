package org.example.model;

public class Address {
    private Long id;

    private String city;
    private String street;
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
