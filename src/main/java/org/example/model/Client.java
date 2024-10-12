package org.example.model;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "Client")
public class Client implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "client_id")
    private Long personalId;

    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;

    /* 1-1 bo unzjemy, że ma 1 adres zameldowania a jak się zmieni to zmieni się wpis w bazie danych */
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "client_address", referencedColumnName = "client_address_id")
    private Address address;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "client_type", referencedColumnName = "client_type_id")
    private ClientType clientType;

    @Column(name = "is_archived")
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

    public void setClientType(ClientType clientType) {
        this.clientType = clientType;
    }

    public String getClientInfo() {
        return "First name: " + firstName
                + ", Last Name: " + lastName
                + ", PersonalID: " + personalId
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

    public Long getPersonalId() {
        return personalId;
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
