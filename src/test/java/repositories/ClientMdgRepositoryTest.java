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
        clientMgdRepository.getMongodb().getCollection("vehicles").drop();
    }

    @AfterAll
    public static void afterAll() throws Exception {
        clientMgdRepository.close();
    }

    @AfterEach
    public void afterEach() {
        clientMgdRepository.getMongodb().getCollection("clients").drop();
        clientMgdRepository.getMongodb().getCollection("vehicles").drop();
    }

    @Test
    public void addTest() {
        AddressMgd addressMgd = new AddressMgd("Łódź", "Radwańska", "40");
        ClientAccountMgd client = new ClientAccountMgd(0, "Maciek", "Walaszek",
                addressMgd, ClientType.GOLD, false);

        ClientAccountMgd client2 = new ClientAccountMgd(1, "Walek", "Walaszek",
                addressMgd, ClientType.GOLD, false);

        clientMgdRepository.Add(client);
        clientMgdRepository.Add(client2);
        assertEquals(clientMgdRepository.getAll().size(), 2);
    }

    @Test
    public void updateTest() {
        AddressMgd addressMgd = new AddressMgd("Łódź", "Radwańska", "40");
        ClientAccountMgd client = new ClientAccountMgd(0, "Maciek", "Walaszek",
                addressMgd, ClientType.GOLD, false);

        clientMgdRepository.Add(client);

        String newName = "Waldek";
        ClientType newClientType = ClientType.BRONZE;

        client.setClientType(newClientType);
        client.setFirstName(newName);

        clientMgdRepository.Update(client);

        assertEquals(client.getFirstName(), newName);
        assertEquals(client.getClientType(), newClientType);

        ClientAccountMgd clientUpdate = clientMgdRepository.Find(client.getEntityId());

        assertEquals(clientUpdate.getFirstName(), newName);
        assertEquals(clientUpdate.getClientType(), newClientType);

    }

    @Test
    public void deleteTest() {
        AddressMgd addressMgd = new AddressMgd("Łódź", "Radwańska", "40");
        ClientAccountMgd client = new ClientAccountMgd(0, "Maciek", "Walaszek",
                addressMgd, ClientType.GOLD, false);

        ClientAccountMgd client2 = new ClientAccountMgd(1, "Walek", "Walaszek",
                addressMgd, ClientType.GOLD, false);

        clientMgdRepository.Add(client);
        clientMgdRepository.Add(client2);
        assertEquals(clientMgdRepository.getAll().size(), 2);

        clientMgdRepository.Delete(client2.getEntityId());
        assertEquals(clientMgdRepository.getAll().size(), 1);


        clientMgdRepository.Delete(client.getEntityId());
        assertEquals(clientMgdRepository.getAll().size(), 0);

    }


}
