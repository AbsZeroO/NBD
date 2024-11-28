package org.example.red;

import jakarta.json.bind.annotation.JsonbCreator;
import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.json.bind.annotation.JsonbSubtype;
import jakarta.json.bind.annotation.JsonbTypeInfo;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.example.model.ClientType;

@JsonbTypeInfo({
        @JsonbSubtype(alias = "clientType", type = ClientTypeJsonb.class)
})
public class ClientAccountJsonb extends AbstractEntityJsonb {
    @JsonbProperty("firstName")
    private String firstName;
    @JsonbProperty("lastName")
    private String lastName;
    @JsonbProperty("address")
    private AddressJsonb addressMgd;
    @JsonbProperty("clientType")
    private ClientType clientType;
    @JsonbProperty("archived")
    private boolean isArchived;
    @JsonbProperty("rents")
    private int rents;

    @JsonbCreator
    public ClientAccountJsonb(@JsonbProperty("_id") int entityId,
                              @JsonbProperty("firstName") String firstName,
                              @JsonbProperty("lastName") String lastName,
                              @JsonbProperty("address") AddressJsonb addressMgd,
                              @JsonbProperty("clientType") ClientType clientType,
                              @JsonbProperty("archived") boolean isArchived,
                              @JsonbProperty("rents") int rents) {
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

    public AddressJsonb getAddressMgd() {
        return addressMgd;
    }

    public void setAddressMgd(AddressJsonb addressMgd) {
        this.addressMgd = addressMgd;
    }

    @Override
    public String toString() {
        return "ClientAccountJsonb{" + " firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", address=" + addressMgd +
                ", clientType=" + clientType +
                ", isArchived=" + isArchived +
                '}';
    }
}
