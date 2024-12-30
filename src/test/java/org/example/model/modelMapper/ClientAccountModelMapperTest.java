package org.example.model.modelMapper;

import org.example.model.cassandra.AddressCas;
import org.example.model.cassandra.ClientAccountCas;
import org.example.model.domain.Address;
import org.example.model.domain.Client;
import org.example.model.domain.ClientType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClientAccountModelMapperTest {

    @Test
    void toClientAccountCas() {
        Address address = new Address("New York", "Main St", "123");
        Client client = new Client(
                1, "John", "Doe", address, ClientType.GOLD, false, 0
        );

        ClientAccountCas clientAccountCas = ClientAccountModelMapper.toClientAccountCas(client);

        assertEquals(client.getId(), clientAccountCas.getEntityId());
        assertEquals(client.getFirstName(), clientAccountCas.getFirstName());
        assertEquals(client.getLastName(), clientAccountCas.getLastName());

        AddressCas addressCas = clientAccountCas.getAddressCas();
        assertEquals(client.getAddress().getCity(), addressCas.getCity());
        assertEquals(client.getAddress().getStreet(), addressCas.getStreet());
        assertEquals(client.getAddress().getHouseNumber(), addressCas.getHouseNumber());

        assertEquals(client.getClientType(), clientAccountCas.getClientType());
        assertEquals(client.isArchived(), clientAccountCas.isArchived());
    }

    @Test
    void toClientDomain() {
        AddressCas addressCas = new AddressCas("New York", "Main St", "123");
        ClientAccountCas clientAccountCas = new ClientAccountCas(
                1, "John", "Doe", addressCas, ClientType.SILVER, false, 0
        );

        Client client = ClientAccountModelMapper.toClientDomain(clientAccountCas);

        assertEquals(clientAccountCas.getEntityId(), client.getId());
        assertEquals(clientAccountCas.getFirstName(), client.getFirstName());
        assertEquals(clientAccountCas.getLastName(), client.getLastName());

        Address address = client.getAddress();
        assertEquals(clientAccountCas.getAddressCas().getCity(), address.getCity());
        assertEquals(clientAccountCas.getAddressCas().getStreet(), address.getStreet());
        assertEquals(clientAccountCas.getAddressCas().getHouseNumber(), address.getHouseNumber());

        assertEquals(clientAccountCas.getClientType(), client.getClientType());
        assertEquals(clientAccountCas.isArchived(), client.isArchived());
    }
}
