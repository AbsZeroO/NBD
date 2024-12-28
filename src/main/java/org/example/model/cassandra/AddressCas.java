package org.example.model.cassandra;

// Nie rozszerzamy o AbstracEntityMgd, ponieważ każdy klient bedzie posiadał swój adres zamieszkania więc nie jest nam
// potrzebnaidentyfikacja po entityId


import com.datastax.oss.driver.api.mapper.annotations.CqlName;
import com.datastax.oss.driver.api.mapper.annotations.Entity;

@Entity(defaultKeyspace = "rent_a_vehicle")
@CqlName("address_cas")
public class AddressCas {
    private String city;

    private String street;

    private String houseNumber;


    public AddressCas(String city,
                      String street,
                      String houseNumber) {
        this.city = city;
        this.street = street;
        this.houseNumber = houseNumber;
    }

    public AddressCas() {
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

    @Override
    public String toString() {
        return "AddressCas{" + "city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", houseNumber='" + houseNumber + '\'' +
                '}';
    }
}
