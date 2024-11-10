package org.example.mgd;

import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.example.model.ClientType;

@BsonDiscriminator(key = "_clientInfo")
public class ClientAccountMgd extends AbstractEntityMgd {
    @BsonProperty("id")
    private Long id;
    @BsonProperty("firstName")
    private String firstName;
    @BsonProperty("lastName")
    private String lastName;
    @BsonProperty("address")
    private AddressMgd addressMgd;
    @BsonProperty("clientType")
    private ClientTypeMgd clientTypeMgd;
    @BsonProperty("archived")
    private boolean isArchived;

    @BsonCreator
    public ClientAccountMgd(@BsonProperty("_id") int entityId,
                            @BsonProperty("id") Long id,
                            @BsonProperty("firstName") String firstName,
                            @BsonProperty("lastName") String lastName,
                            @BsonProperty("address") AddressMgd addressMgd,
                            @BsonProperty("clientType") ClientTypeMgd clientTypeMgd,
                            @BsonProperty("archived") boolean isArchived) {
        super(entityId);
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.addressMgd = addressMgd;
        this.clientTypeMgd = clientTypeMgd;
        this.isArchived = isArchived;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public ClientTypeMgd getClientType() {
        return clientTypeMgd;
    }

    public void setClientType(ClientTypeMgd clientType) {
        this.clientTypeMgd = clientType;
    }

    public boolean isArchived() {
        return isArchived;
    }

    public void setArchived(boolean archived) {
        isArchived = archived;
    }

    @Override
    public String toString() {
        return "ClientAccountMgd{" + "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", address=" + addressMgd +
                ", clientType=" + clientTypeMgd +
                ", isArchived=" + isArchived +
                '}';
    }
}
