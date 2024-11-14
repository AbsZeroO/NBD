package repositories;

import org.example.mgd.AddressMgd;
import org.example.mgd.ClientAccountMgd;
import org.example.model.ClientType;
import org.example.repositories.ClientMgdRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ClientMdgRepositoryTest {
    public static ClientMgdRepository clientMgdRepository = new ClientMgdRepository();

    @BeforeAll
    public static void beforeAll() throws Exception {
        clientMgdRepository.getMongodb().getCollection("clients").drop();
    }

    @AfterAll
    public static void afterAll() throws Exception {
        clientMgdRepository.close();
    }

    @AfterEach
    public void afterEach() {
        clientMgdRepository.getMongodb().getCollection("clients").drop();
    }

    @Test
    public void addTest() {
        AddressMgd address = new AddressMgd("Łódź", "Radwańska", "40");
        ClientAccountMgd client = new ClientAccountMgd(0, "Maciek", "Walaszek",
                address, ClientType.GOLD, false, 0);

        ClientAccountMgd client2 = new ClientAccountMgd(1, "Walek", "Walaszek",
                address, ClientType.GOLD, false, 0);

        clientMgdRepository.add(client);
        clientMgdRepository.add(client2);
        assertEquals(clientMgdRepository.findAll().size(), 2);
    }

    @Test
    public void updateTest() {
        AddressMgd address = new AddressMgd("Łódź", "Radwańska", "40");
        ClientAccountMgd client = new ClientAccountMgd(0, "Maciek", "Walaszek",
                address, ClientType.GOLD, false, 0);

        clientMgdRepository.add(client);

        String newName = "Waldek";
        ClientType newClientType = ClientType.BRONZE;

        client.setClientType(newClientType);
        client.setFirstName(newName);

        clientMgdRepository.update(client);

        assertEquals(client.getFirstName(), newName);
        assertEquals(client.getClientType(), newClientType);

        ClientAccountMgd clientUpdate = clientMgdRepository.findById(client.getEntityId());


        assertEquals(clientUpdate.getFirstName(), newName);
        assertEquals(clientUpdate.getClientType(), newClientType);

        assertEquals(clientMgdRepository.findAll().size(), 1);

    }

    @Test
    public void deleteTest() {
        AddressMgd address = new AddressMgd("Łódź", "Radwańska", "40");
        ClientAccountMgd client = new ClientAccountMgd(0, "Maciek", "Walaszek",
                address, ClientType.GOLD, false, 0);

        ClientAccountMgd client2 = new ClientAccountMgd(1, "Walek", "Walaszek",
                address, ClientType.GOLD, false, 0);

        clientMgdRepository.add(client);
        clientMgdRepository.add(client2);
        assertEquals(clientMgdRepository.findAll().size(), 2);

        clientMgdRepository.delete(client2.getEntityId());
        assertEquals(clientMgdRepository.findAll().size(), 1);


        clientMgdRepository.delete(client.getEntityId());
        assertEquals(clientMgdRepository.findAll().size(), 0);

    }


}
