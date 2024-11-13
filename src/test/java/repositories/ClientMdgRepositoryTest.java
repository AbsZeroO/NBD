package repositories;

import org.example.model.Address;
import org.example.model.Client;
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
        Address address = new Address("Łódź", "Radwańska", "40");
        Client client = new Client(0, "Maciek", "Walaszek",
                address, ClientType.GOLD, false);

        Client client2 = new Client(1, "Walek", "Walaszek",
                address, ClientType.GOLD, false);

        clientMgdRepository.add(client);
        clientMgdRepository.add(client2);
        assertEquals(clientMgdRepository.findAll().size(), 2);
    }

    @Test
    public void updateTest() {
        Address address = new Address("Łódź", "Radwańska", "40");
        Client client = new Client(0, "Maciek", "Walaszek",
                address, ClientType.GOLD, false);

        clientMgdRepository.add(client);

        String newName = "Waldek";
        ClientType newClientType = ClientType.BRONZE;

        client.setClientType(newClientType);
        client.setFirstName(newName);

        clientMgdRepository.update(client);

        assertEquals(client.getFirstName(), newName);
        assertEquals(client.getClientType(), newClientType);

        Client clientUpdate = clientMgdRepository.findById(client.getId());


        assertEquals(clientUpdate.getFirstName(), newName);
        assertEquals(clientUpdate.getClientType(), newClientType);

    }

    @Test
    public void deleteTest() {
        Address address = new Address("Łódź", "Radwańska", "40");
        Client client = new Client(0, "Maciek", "Walaszek",
                address, ClientType.GOLD, false);

        Client client2 = new Client(1, "Walek", "Walaszek",
                address, ClientType.GOLD, false);

        clientMgdRepository.add(client);
        clientMgdRepository.add(client2);
        assertEquals(clientMgdRepository.findAll().size(), 2);

        clientMgdRepository.delete(client2.getId());
        assertEquals(clientMgdRepository.findAll().size(), 1);


        clientMgdRepository.delete(client.getId());
        assertEquals(clientMgdRepository.findAll().size(), 0);

    }


}
