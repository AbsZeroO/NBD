package org.example.mgd;

import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonDiscriminator;
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

    @BsonCreator
    public ClientAccountMgd(@BsonProperty("_id") int entityId,
                            @BsonProperty("firstName") String firstName,
                            @BsonProperty("lastName") String lastName,
                            @BsonProperty("address") AddressMgd addressMgd,
                            @BsonProperty("clientType") ClientType clientType,
                            @BsonProperty("archived") boolean isArchived) {
        super(entityId);
        this.firstName = firstName;
        this.lastName = lastName;
        this.addressMgd = addressMgd;
        this.clientType = clientType;
        this.isArchived = isArchived;
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

    public AddressMgd getAddress() {
        return addressMgd;
    }

    public void setAddress(AddressMgd address) {
        this.addressMgd = address;
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
