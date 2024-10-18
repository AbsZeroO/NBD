package org.example.repositories;

import org.example.model.Address;
import org.example.model.Client;
import org.example.model.Gold;
import org.junit.jupiter.api.*;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ClientRepoTest {

    private static EntityManagerFactory emf;
    private static EntityManager em;
    private ClientRepo clientRepo;

    @BeforeAll
    static void beforeAll() {
        emf = Persistence.createEntityManagerFactory("my-persistence-unit");
        em = emf.createEntityManager();
    }

    @BeforeEach
    void setUp() {
        clientRepo = new ClientRepo();
    }

    @Test
    void testAdd() {
        // Arrange
        Client client = new Client("Maciek", "Walczak", new Address("Poznan", "akcja", "5"), new Gold());

        // Act
        clientRepo.Add(client);

        // Assert
        assertEquals("Maciek", client.getFirstName());
        assertEquals("Walczak", client.getLastName());
        assertEquals("Poznan", client.getAddress().getCity());
        assertEquals("akcja", client.getAddress().getStreet());
        assertEquals("5", client.getAddress().getHouseNumber());
    }

    @Test
    void testUpdate() {
        // Arrange
        Client client = new Client("Maciek", "Walczak", new Address("Poznan", "akcja", "5"), new Gold());
        clientRepo.Add(client);

        // Act
        client.setFirstName("Michał");
        clientRepo.Update(client);

        // Assert
        assertEquals("Michał", client.getFirstName());
    }

    @Test
    void testDelete() {
        // Arrange
        Client client = new Client("Maciek", "Walczak", new Address("Poznan", "akcja", "5"), new Gold());
        clientRepo.Add(client);

        // Act
        clientRepo.Delete(client);

        // Assert
        assertEquals("Maciek", client.getFirstName());
    }

    @Test
    void testFind() {
        // Arrange
        Client client = new Client("Maciek", "Walczak", new Address("Poznan", "akcja", "5"), new Gold());
        clientRepo.Add(client);

        // Act
        Client foundClient = clientRepo.Find(client.getId());

        // Assert
        assertNotNull(foundClient);
        assertEquals("Maciek", foundClient.getFirstName());
    }

    @Test
    void testGetAll() {
        // Arrange
        Client client1 = new Client("Maciek", "Walczak", new Address("Poznan", "akcja", "5"), new Gold());
        Client client2 = new Client("Ania", "Nowak", new Address("Wroclaw", "ul. Kwiatowa", "10"), new Gold());
        clientRepo.Add(client1);
        clientRepo.Add(client2);

        // Act
        List<Client> clients = clientRepo.getAll();

        // Assert
        assertEquals(2, clients.size());
        assertTrue(clients.contains(client1));
        assertTrue(clients.contains(client2));
    }

    @AfterAll
    static void afterAll() {
        if (em != null) {
            em.close();
        }
        if (emf != null) {
            emf.close();
        }
    }
}
