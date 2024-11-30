package org.example.red;

import jakarta.json.bind.annotation.JsonbCreator;
import jakarta.json.bind.annotation.JsonbProperty;

// Nie rozszerzamy o AbstracEntityMgd, ponieważ każdy klient bedzie posiadał swój adres zamieszkania więc nie jest nam
// potrzebna identyfikacja po entityId

public class AddressJsonb {
    @JsonbProperty("city")
    private String city;
    @JsonbProperty("street")
    private String street;
    @JsonbProperty("houseNumber")
    private String houseNumber;

    @JsonbCreator
    public AddressJsonb(@JsonbProperty("city") String city,
                        @JsonbProperty("street") String street,
                        @JsonbProperty("houseNumber") String houseNumber) {
        this.city = city;
        this.street = street;
        this.houseNumber = houseNumber;
    }

    public AddressJsonb() {
        super();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

}
