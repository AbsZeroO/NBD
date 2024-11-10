package org.example.mgd;

import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;

// Nie rozszerzamy o AbstracEntityMgd, ponieważ każdy klient bedzie posiadał swój adres zamieszkania więc nie jest nam
// potrzebna identyfikacja po entityId
public class AddressMgd  {
    @BsonProperty
    private String city;
    @BsonProperty
    private String street;
    @BsonProperty
    private String houseNumber;

    @BsonCreator
    public AddressMgd(@BsonProperty String city,
                      @BsonProperty String street,
                      @BsonProperty String houseNumber) {
        this.city = city;
        this.street = street;
        this.houseNumber = houseNumber;
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
        return "AddressMgd{" + "city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", houseNumber='" + houseNumber + '\'' +
                '}';
    }
}
