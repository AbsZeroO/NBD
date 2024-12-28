package org.example.model.cassandra;


import com.datastax.oss.driver.api.mapper.annotations.CqlName;
import com.datastax.oss.driver.api.mapper.annotations.Entity;
import org.example.model.domain.ClientType;

@Entity(defaultKeyspace = "rent_a_vehicle")
@CqlName("clients")
public class ClientAccountCas extends AbstractEntityCas {


    private String firstName;

    private String lastName;

    @CqlName("address_cas")
    private AddressCas addressCas;

    private ClientType clientType;

    private boolean isArchived;

    private int rents;

    public ClientAccountCas(int entityId,
                            String firstName,
                            String lastName,
                            AddressCas addressCas,
                            ClientType clientType,
                            boolean isArchived,
                            int rents) {
        super(entityId);
        this.firstName = firstName;
        this.lastName = lastName;
        this.addressCas = addressCas;
        this.clientType = clientType;
        this.isArchived = isArchived;
        this.rents = rents;
    }

    public ClientAccountCas() {

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

    public AddressCas getAddressCas() {
        return addressCas;
    }

    public void setAddressCas(AddressCas addressCas) {
        this.addressCas = addressCas;
    }

    @Override
    public String toString() {
        return "ClientAccountCas{" + " firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", address=" + addressCas +
                ", clientType=" + clientType +
                ", isArchived=" + isArchived +
                '}';
    }
}
