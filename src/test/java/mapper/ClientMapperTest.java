package mapper;

import org.bson.Document;
import org.example.mappers.ClientMapper;
import org.example.mgd.AddressMgd;
import org.example.mgd.ClientAccountMgd;
import org.example.model.Address;
import org.example.model.Client;
import org.example.model.ClientType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ClientMapperTest {
    @Test
    void testClientToMongo() {
        Address address = new Address("Warsaw", "Main St", "5A");
        Client client = new Client(1, "John", "Doe", address, ClientType.GOLD, false);

        ClientAccountMgd clientMgd = ClientMapper.clientToMongo(client);

        assertEquals(client.getId(), clientMgd.getEntityId());
        assertEquals(client.getFirstName(), clientMgd.getFirstName());
        assertEquals(client.getLastName(), clientMgd.getLastName());
        assertEquals(client.getClientType(), clientMgd.getClientType());
        assertEquals(client.isArchived(), clientMgd.isArchived());
        assertEquals(client.getAddress().getCity(), clientMgd.getAddressMgd().getCity());
        assertEquals(client.getAddress().getStreet(), clientMgd.getAddressMgd().getStreet());
        assertEquals(client.getAddress().getHouseNumber(), clientMgd.getAddressMgd().getHouseNumber());
    }

    @Test
    void testClientFromMongo() {
        AddressMgd addressMgd = new AddressMgd("Warsaw", "Main St", "5A");
        ClientAccountMgd clientMgd = new ClientAccountMgd(1, "John", "Doe", addressMgd, ClientType.GOLD, false);

        Client client = ClientMapper.clientFromMongo(clientMgd);

        assertEquals(clientMgd.getEntityId(), client.getId());
        assertEquals(clientMgd.getFirstName(), client.getFirstName());
        assertEquals(clientMgd.getLastName(), client.getLastName());
        assertEquals(clientMgd.getClientType(), client.getClientType());
        assertEquals(clientMgd.isArchived(), client.isArchived());
        assertEquals(clientMgd.getAddressMgd().getCity(), client.getAddress().getCity());
        assertEquals(clientMgd.getAddressMgd().getStreet(), client.getAddress().getStreet());
        assertEquals(clientMgd.getAddressMgd().getHouseNumber(), client.getAddress().getHouseNumber());
    }

    @Test
    void testToClientMgd() {
        Document addressDoc = new Document("city", "Warsaw")
                .append("street", "Main St")
                .append("houseNumber", "5A");
        Document clientDoc = new Document("_id", 1)
                .append("firstName", "John")
                .append("lastName", "Doe")
                .append("address", addressDoc)
                .append("clientType", "GOLD")
                .append("archived", false);

        ClientAccountMgd clientMgd = ClientMapper.toClientMgd(clientDoc);

        assertEquals(clientDoc.getInteger("_id"), clientMgd.getEntityId());
        assertEquals(clientDoc.getString("firstName"), clientMgd.getFirstName());
        assertEquals(clientDoc.getString("lastName"), clientMgd.getLastName());
        assertEquals(clientDoc.getString("clientType"), clientMgd.getClientType().name());
        assertEquals(clientDoc.getBoolean("archived"), clientMgd.isArchived());
        assertEquals(addressDoc.getString("city"), clientMgd.getAddressMgd().getCity());
        assertEquals(addressDoc.getString("street"), clientMgd.getAddressMgd().getStreet());
        assertEquals(addressDoc.getString("houseNumber"), clientMgd.getAddressMgd().getHouseNumber());
    }
}
