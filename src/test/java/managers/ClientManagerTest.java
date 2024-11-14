package managers;

import org.example.exceptions.ClientException;
import org.example.exceptions.RentException;
import org.example.managers.ClientManager;
import org.example.mappers.ClientMapper;
import org.example.model.Address;
import org.example.model.Client;
import org.example.model.ClientType;
import org.example.repositories.ClientMgdRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ClientManagerTest {
    public static ClientMgdRepository repository = new ClientMgdRepository();
    public static ClientManager clientManager = new ClientManager(repository);

    @BeforeAll
    public static void beforeAll() throws Exception {
        repository.getMongodb().getCollection("clients").drop();
        repository.getMongodb().getCollection("vehicles").drop();
        repository.getMongodb().getCollection("rents").drop();
    }

    @AfterAll
    public static void afterAll() throws Exception {
        repository.close();
    }

    @AfterEach
    public void afterEach() {
        repository.getMongodb().getCollection("clients").drop();
        repository.getMongodb().getCollection("vehicles").drop();
        repository.getMongodb().getCollection("rents").drop();
    }

    @Test
    public void registerTest() {
        Address address = new Address("Łódź", "Radwańska", "40");
        Client client = new Client(0, "Maciek", "Walaszek",
                address, ClientType.GOLD, false);

        Client client2 = new Client(1, "Walek", "Walaszek",
                address, ClientType.GOLD, false);

        clientManager.registerClient(client);

        Client clientFromDb = ClientMapper.clientFromMongo(repository.findById(client.getId()));

        assertEquals(clientFromDb.getClientInfo(), client.getClientInfo());

    }

    @Test
    public void deleteTest() {
        Address address = new Address("Łódź", "Radwańska", "40");
        Client client = new Client(0, "Maciek", "Walaszek",
                address, ClientType.GOLD, false);

        Client client2 = new Client(1, "Walek", "Walaszek",
                address, ClientType.GOLD, false);

        clientManager.registerClient(client);
        clientManager.registerClient(client2);

        assertEquals(2, repository.findAll().size());

        clientManager.deleateClient(client2);

        assertEquals(1, repository.findAll().size());

    }

    @Test
    public void getClient() throws ClientException {
        Address address = new Address("Łódź", "Radwańska", "40");
        Client client = new Client(0, "Maciek", "Walaszek",
                address, ClientType.GOLD, false);

        Client client2 = new Client(1, "Walek", "Walaszek",
                address, ClientType.GOLD, false);

        clientManager.registerClient(client);
        clientManager.registerClient(client2);

        Client client2FromDataBase = clientManager.getClient(client2.getId());

        assertEquals(client2.getClientInfo(), client2FromDataBase.getClientInfo());

    }

    @Test
    public void editTest() throws ClientException {
        Address address = new Address("Łódź", "Radwańska", "40");
        Client client = new Client(0, "Maciek", "Walaszek",
                address, ClientType.GOLD, false);

        Client client2 = new Client(1, "Walek", "Walaszek",
                address, ClientType.GOLD, false);

        clientManager.registerClient(client);
        clientManager.registerClient(client2);

        ClientType updatedClientType = ClientType.BRONZE;
        String updatedFirstName = "QQQQQQQ";

        client.setFirstName(updatedFirstName);
        client.setClientType(updatedClientType);

        clientManager.updateInfo(client);

        Client updatedFromDb = clientManager.getClient(client.getId());

        assertEquals(client.getClientInfo(), updatedFromDb.getClientInfo());
        assertEquals(client.getClientType(), updatedFromDb.getClientType());

    }

    @Test
    public void testGetAllRents() throws RentException {
        Address address = new Address("Łódź", "Radwańska", "40");
        Client client = new Client(0, "Maciek", "Walaszek",
                address, ClientType.GOLD, false);

        Client client2 = new Client(1, "Walek", "Walaszek",
                address, ClientType.GOLD, false);

        clientManager.registerClient(client);
        clientManager.registerClient(client2);

        assertEquals(2, clientManager.getAllClients().size(), "There should be 2 active rents for this client.");
    }

}
