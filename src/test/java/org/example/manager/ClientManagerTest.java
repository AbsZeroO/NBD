package org.example.manager;

import com.datastax.oss.driver.api.querybuilder.QueryBuilder;
import com.datastax.oss.driver.api.querybuilder.truncate.Truncate;
import org.example.model.cassandra.AddressCas;
import org.example.model.cassandra.ClientAccountCas;
import org.example.model.domain.Address;
import org.example.model.domain.Client;
import org.example.model.domain.ClientType;
import org.example.repo.ClientAccountRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.ref.Cleaner;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ClientManagerTest {
    private static ClientManager manager = new ClientManager();

    @BeforeEach
    public void setup() {
        Truncate truncate = QueryBuilder.truncate("rent_a_vehicle", "clients");
        manager.getRepository().getSession().execute(truncate.build());
    }

    @Test
    void testAddClientAccount() {
        Client client = new Client(
                1,
                "John",
                "Doe",
                new Address("New York", "5th Avenue", "123"),
                ClientType.GOLD,
                false,
                0
        );

        boolean result = manager.add(client);
        assertTrue(result, "Adding a client account should return true");

        Client fetched = manager.findById(1);
        assertNotNull(fetched, "Fetched client should not be null");
        assertEquals("John", fetched.getFirstName(), "First name should match");
    }

    @Test
    void testUpdateClientAccount() {
        Client client = new Client(
                2,
                "Alice",
                "Brown",
                new Address("Los Angeles", "Main Street", "456"),
                ClientType.SILVER,
                false,
                0
        );

        boolean result = manager.add(client);
        assertTrue(result, "Adding a client account should return true");

        Client fetched = manager.findById(2);
        assertNotNull(fetched, "Fetched client should not be null");
        assertEquals("Alice", fetched.getFirstName(), "First name should match");

        fetched.setLastName("Smith");
        boolean result2 = manager.update(fetched);
        assertTrue(result2, "Updating a client account should return true");

        Client updated = manager.findById(2);
        assertNotNull(updated, "Updated client should not be null");
        assertEquals("Smith", updated.getLastName(), "Last name should be updated");
    }

    @Test
    void testDeleteClientAccount() {
        Client client = new Client(
                3,
                "Bob",
                "White",
                new Address("Chicago", "Lake Shore Drive", "789"),
                ClientType.BRONZE,
                false,
                0
        );

        manager.add(client);

        boolean result = manager.delete(client);
        assertTrue(result, "Deleting a client account should return true");
    }

    @Test
    void testFindAllClientAccounts() {
        Client client1 = new Client(
                4,
                "Eve",
                "Black",
                new Address("San Francisco", "Market Street", "100"),
                ClientType.GOLD,
                false,
                0
        );

        Client client2 = new Client(
                5,
                "Charlie",
                "Green",
                new Address("Seattle", "Pine Street", "200"),
                ClientType.SILVER,
                false,
                0
        );

        manager.add(client1);
        manager.add(client2);

        List<Client> clients = manager.findAll();
        assertNotNull(clients, "Client list should not be null");
        assertTrue(clients.size() >= 2, "Client list should contain at least two clients");
    }

    @Test
    void findById() {
        Address addressCas = new Address("San Francisco", "Market Street", "100");
        Client client1 = new Client(
                4,
                "Eve",
                "Black",
                addressCas,
                ClientType.GOLD,
                false,
                0
        );

        manager.add(client1);

        Client fromCas = manager.findById(client1.getId());

        assertEquals(addressCas.getCity(), fromCas.getAddress().getCity());
        assertEquals(addressCas.getStreet(), fromCas.getAddress().getStreet());
        assertEquals(addressCas.getHouseNumber(), fromCas.getAddress().getHouseNumber());


    }

}