package org.example.mgd;

import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.example.model.ClientType;

public class ClientAccountMgd extends AbstractEntityMgd {
    @BsonProperty("firstName")
    private String firstName;
    @BsonProperty("lastName")
    private String lastName;
    @BsonProperty("address")
    private AddressMgd addressMgd;
    @BsonProperty("clientType")
    private ClientType clientType;
    @BsonProperty("archived")
    private boolean isArchived;
    @BsonProperty("rents")
    private int rents;

    @BsonCreator
    public ClientAccountMgd(@BsonProperty("_id") int entityId,
                            @BsonProperty("firstName") String firstName,
                            @BsonProperty("lastName") String lastName,
                            @BsonProperty("address") AddressMgd addressMgd,
                            @BsonProperty("clientType") ClientType clientType,
                            @BsonProperty("archived") boolean isArchived,
                            @BsonProperty("rents") int rents) {
        super(entityId);
        this.firstName = firstName;
        this.lastName = lastName;
        this.addressMgd = addressMgd;
        this.clientType = clientType;
        this.isArchived = isArchived;
        this.rents = rents;
    }

    public int getRents() {
        return rents;
    }

    public void setRents(int rents) {
        this.rents = rents;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public ClientType getClientType() {
        return clientType;
    }

    public void setClientType(ClientType clientType) {
        this.clientType = clientType;
    }

    public boolean isArchived() {
        return isArchived;
    }

    public void setArchived(boolean archived) {
        isArchived = archived;
    }

    public AddressMgd getAddressMgd() {
        return addressMgd;
    }

    public void setAddressMgd(AddressMgd addressMgd) {
        this.addressMgd = addressMgd;
    }

    @Override
    public String toString() {
        return "ClientAccountMgd{" + " firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", address=" + addressMgd +
                ", clientType=" + clientType +
                ", isArchived=" + isArchived +
                '}';
    }
}
