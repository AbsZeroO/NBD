package org.example.model;

import java.io.Serializable;

public class Client implements Serializable {
    private Long id;

    private String firstName;
    private String lastName;

    /* 1-1 bo unzjemy, że ma 1 adres zameldowania a jak się zmieni to zmieni się wpis w bazie danych */
    private Address address;

    private ClientType clientType;

    private boolean archived;

    public Client() {
    }

    public Client(String firstName, String lastName, Address address, ClientType clientType) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.clientType = clientType;
        this.archived = false;
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

    public Long getId() {
        return id;
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
