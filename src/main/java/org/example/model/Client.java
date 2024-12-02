package org.example.model;

import java.io.Serializable;

public class Client implements Serializable {
    private int id;

    private String firstName;
    private String lastName;

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
}
