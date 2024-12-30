package org.example.repo;

import com.datastax.oss.driver.api.querybuilder.QueryBuilder;
import com.datastax.oss.driver.api.querybuilder.truncate.Truncate;
import org.example.model.cassandra.AddressCas;
import org.example.model.cassandra.ClientAccountCas;
import org.example.model.domain.ClientType;
import org.junit.After;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ClientAccountRepositoryTest {
    private static ClientAccountRepository repository;

    @BeforeAll
    public static void before() {
        repository = new ClientAccountRepository();
    }

    @BeforeEach
    public void setup() {
        Truncate truncate = QueryBuilder.truncate("rent_a_vehicle", "clients");
        repository.getSession().execute(truncate.build());
    }

    @Test
    void testAddClientAccount() {
        ClientAccountCas client = new ClientAccountCas(
                1,
                "John",
                "Doe",
                new AddressCas("New York", "5th Avenue", "123"),
                ClientType.GOLD,
                false,
                0
        );

        boolean result = repository.add(client);
        assertTrue(result, "Adding a client account should return true");

        ClientAccountCas fetched = repository.findById(1);
        assertNotNull(fetched, "Fetched client should not be null");
        assertEquals("John", fetched.getFirstName(), "First name should match");
    }

    @Test
    void testUpdateClientAccount() {
        ClientAccountCas client = new ClientAccountCas(
                2,
                "Alice",
                "Brown",
                new AddressCas("Los Angeles", "Main Street", "456"),
                ClientType.SILVER,
                false,
                0
        );

        boolean result = repository.add(client);
        assertTrue(result, "Adding a client account should return true");

        ClientAccountCas fetched = repository.findById(2);
        assertNotNull(fetched, "Fetched client should not be null");
        assertEquals("Alice", fetched.getFirstName(), "First name should match");

        fetched.setLastName("Smith");
        boolean result2 = repository.update(fetched);
        assertTrue(result2, "Updating a client account should return true");

        ClientAccountCas updated = repository.findById(2);
        assertNotNull(updated, "Updated client should not be null");
        assertEquals("Smith", updated.getLastName(), "Last name should be updated");
    }

    @Test
    void testDeleteClientAccount() {
        ClientAccountCas client = new ClientAccountCas(
                3,
                "Bob",
                "White",
                new AddressCas("Chicago", "Lake Shore Drive", "789"),
                ClientType.BRONZE,
                false,
                0
        );

        repository.add(client);

        boolean result = repository.delete(client);
        assertTrue(result, "Deleting a client account should return true");

        ClientAccountCas deleted = repository.findById(3);
        assertNull(deleted, "Deleted client should be null");
    }

    @Test
    void testFindAllClientAccounts() {
        ClientAccountCas client1 = new ClientAccountCas(
                4,
                "Eve",
                "Black",
                new AddressCas("San Francisco", "Market Street", "100"),
                ClientType.GOLD,
                false,
                0
        );

        ClientAccountCas client2 = new ClientAccountCas(
                5,
                "Charlie",
                "Green",
                new AddressCas("Seattle", "Pine Street", "200"),
                ClientType.SILVER,
                false,
                0
        );

        repository.add(client1);
        repository.add(client2);

        List<ClientAccountCas> clients = repository.findAll();
        assertNotNull(clients, "Client list should not be null");
        assertTrue(clients.size() >= 2, "Client list should contain at least two clients");
    }

    @Test
    void findById() {
        AddressCas addressCas = new AddressCas("San Francisco", "Market Street", "100");
        ClientAccountCas client1 = new ClientAccountCas(
                4,
                "Eve",
                "Black",
                addressCas,
                ClientType.GOLD,
                false,
                0
        );

        repository.add(client1);

        ClientAccountCas fromCas = repository.findById(client1.getEntityId());

        assertEquals(addressCas.getCity(), fromCas.getAddressCas().getCity());
        assertEquals(addressCas.getStreet(), fromCas.getAddressCas().getStreet());
        assertEquals(addressCas.getHouseNumber(), fromCas.getAddressCas().getHouseNumber());


    }

}