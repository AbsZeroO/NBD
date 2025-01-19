package org.example.model;

import org.apache.avro.Schema;
import org.apache.avro.specific.SpecificRecordBase;

import java.io.IOException;
import java.io.Serializable;

public class Client extends SpecificRecordBase implements Serializable {
    private int id;

    private String firstName;
    private String lastName;

    /* 1-1 bo unzjemy, że ma 1 adres zameldowania a jak się zmieni to zmieni się wpis w bazie danych */
    private Address address;

    private ClientType clientType;

    private boolean archived;

    private int rents = 0;

    public Client(int id, String firstName, String lastName, Address address, ClientType clientType, boolean archived, int rents) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.clientType = clientType;
        this.archived = archived;
        this.rents = rents;
    }

    public Client() {
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public int getRents() {
        return rents;
    }

    public void setRents(int rents) {
        this.rents = rents;
    }

    public int getMaxVehicles() {
        return clientType.getMaxVehicle();
    }

    public double applyDiscount(double price) {
        return clientType.applyDiscount(price);
    }

    public ClientType getClientType() {
        return clientType;
    }

    public void setClientType(ClientType clientType) {
        this.clientType = clientType;
    }

    public String getClientInfo() {
        return "First name: " + firstName
                + ", Last Name: " + lastName
                + ", PersonalID: " + id
                + ", Client Type: " + clientType;
    }

    public String getAddressInfo() {
        return address.getAddressInfo();
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Address getAddress() {
        return address;
    }

    public boolean isArchived() {
        return archived;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setArchived(boolean value) {
        this.archived = value;
    }

    @Override
    public Schema getSchema() {
        try {
            return new Schema.Parser().parse(getClass().getResourceAsStream("src/main/java/org/example/avro/client.avsc"));
        } catch (IOException e) {
            throw new RuntimeException("Błąd ładowania schematu Avro z pliku.", e);
        }
    }

    @Override
    public Object get(int field) {
        return switch (field) {
            case 0 -> id;
            case 1 -> firstName;
            case 2 -> lastName;
            case 3 -> address;
            case 4 -> clientType;
            case 5 -> archived;
            case 6 -> rents;
            default -> throw new IllegalArgumentException("Unknown field: " + field);
        };
    }

    @Override
    public void put(int field, Object value) {
        switch (field) {
            case 0 -> id = (int) value;
            case 1 -> firstName = (String) value;
            case 2 -> lastName = (String) value;
            case 3 -> address = (Address) value;
            case 4 -> clientType = (ClientType) value;
            case 5 -> archived = (boolean) value;
            case 6 -> rents = (int) value;
            default -> throw new IllegalArgumentException("Unknown field: " + field);
        }
    }
}
