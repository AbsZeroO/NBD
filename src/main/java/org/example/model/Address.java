package org.example.model;

import org.apache.avro.Schema;
import org.apache.avro.specific.SpecificRecordBase;

import java.io.IOException;

public class Address extends SpecificRecordBase {

    private String city;
    private String street;
    private String houseNumber;

    public Address(String city, String street, String houseNumber) {
        this.city = city;
        this.street = street;
        this.houseNumber = houseNumber;
    }

    public Address() {
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

    @Override
    public Schema getSchema() {
        try {
            return new Schema.Parser().parse(getClass().getResourceAsStream("src/main/java/org/example/avro/address.avsc"));
        } catch (IOException e) {
            throw new RuntimeException("Błąd ładowania schematu Avro z pliku.", e);
        }
    }

    @Override
    public Object get(int field) {
        return switch (field) {
            case 0 -> street;
            case 1 -> city;
            case 2 -> houseNumber;
            default -> throw new IllegalArgumentException("Unknown field: " + field);
        };
    }

    @Override
    public void put(int field, Object value) {
        switch (field) {
            case 0 -> street = (String) value;
            case 1 -> city = (String) value;
            case 2 -> houseNumber = (String) value;
            default -> throw new IllegalArgumentException("Unknown field: " + field);
        }
    }

}
