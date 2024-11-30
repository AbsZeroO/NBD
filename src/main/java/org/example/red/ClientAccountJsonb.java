package org.example.red;

import jakarta.json.bind.annotation.JsonbCreator;
import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.json.bind.annotation.JsonbSubtype;
import jakarta.json.bind.annotation.JsonbTypeInfo;
import org.example.model.ClientType;

public class ClientAccountJsonb extends AbstractEntityJsonb {
    @JsonbProperty("firstName")
    private String firstName;
    @JsonbProperty("lastName")
    private String lastName;
    @JsonbProperty("address")
    private AddressJsonb addressJsonb;
    @JsonbProperty("clientType")
    private ClientType clientType;
    @JsonbProperty("archived")
    private boolean isArchived;
    @JsonbProperty("rents")
    private int rents;

    @JsonbCreator
    public ClientAccountJsonb(@JsonbProperty("entityId") int entityId,
                              @JsonbProperty("firstName") String firstName,
                              @JsonbProperty("lastName") String lastName,
                              @JsonbProperty("address") AddressJsonb addressMgd,
                              @JsonbProperty("clientType") ClientType clientType,
                              @JsonbProperty("archived") boolean isArchived,
                              @JsonbProperty("rents") int rents) {
        super(entityId);
        this.firstName = firstName;
        this.lastName = lastName;
        this.addressJsonb = addressMgd;
        this.clientType = clientType;
        this.isArchived = isArchived;
        this.rents = rents;
    }

    public ClientAccountJsonb() {
        super();
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

    public boolean getArchived() {
        return isArchived;
    }

    public void setArchived(boolean archived) {
        isArchived = archived;
    }

    public AddressJsonb getAddressJsonb() {
        return addressJsonb;
    }

    public void setAddressJsonb(AddressJsonb addressJsonb) {
        this.addressJsonb = addressJsonb;
    }

}
