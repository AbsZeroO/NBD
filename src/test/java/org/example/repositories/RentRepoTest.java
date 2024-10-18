
package org.example.repositories;
import jakarta.persistence.*;
import org.example.model.Address;
import org.example.model.Client;
import org.example.model.Gold;
import org.example.model.Rent;
import org.example.model.Vehicle;
import org.junit.jupiter.api.*;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RentRepoTest {

    private static EntityManagerFactory emf;
    private static EntityManager em;
    private RentRepo rentRepo;

    @BeforeAll
    static void beforeAll() {
        // Tworzenie EntityManagerFactory i EntityManager dla testów
        emf = Persistence.createEntityManagerFactory("my-persistence-unit");
    }

    @BeforeEach
    void setUp() {
        rentRepo = new RentRepo();
        em = emf.createEntityManager();
    }

    @Test
    void testAdd() {
        // Arrange
        Vehicle vehicle = new Vehicle("ABC1234", 195, 100.0, false);
        Client client = new Client("Maciek", "Walczak", new Address("Poznan", "akcja", "5"), new Gold());
        Rent rent = new Rent(client, vehicle, LocalDateTime.now());

        // Act
        rentRepo.Add(rent);

        // Assert
        assertNotNull(rent.getId(), "Rent ID should not be null after adding to the database");
        assertEquals(client.getId(), rent.getClient().getId(), "The client should match the one we added");
        assertEquals(vehicle.getId(), rent.getVehicle().getId(), "The vehicle should match the one we added");
    }

    @Test
    void testUpdate() {
        // Arrange
        Vehicle vehicle = new Vehicle("ABC1234", 195, 100.0, false);
        Client client = new Client("Maciek", "Walczak", new Address("Poznan", "akcja", "5"), new Gold());
        em.getTransaction().begin();
        em.persist(vehicle);
        em.persist(client);
        em.getTransaction().commit();
        Rent rent = new Rent(client, vehicle, LocalDateTime.now());
        rentRepo.Add(rent);

        // Act
        rent.endRent(LocalDateTime.now().plusDays(1)); // Ending the rent
        rentRepo.Update(rent);

        // Assert
        Rent updatedRent = rentRepo.Find(rent.getId());
        assertNotNull(updatedRent.getEndTime(), "The end time should not be null after ending the rent");
    }

    @Test
    void testDelete() {
        // Arrange
        Vehicle vehicle = new Vehicle("ABC1234", 195, 100.0, false);
        Client client = new Client("Maciek", "Walczak", new Address("Poznan", "akcja", "5"), new Gold());
        Rent rent = new Rent(client, vehicle, LocalDateTime.now());
        rentRepo.Add(rent);

        // Act
        rentRepo.Delete(rent);

        // Assert
        assertNull(rentRepo.Find(rent.getId()), "Rent should be null after deletion");
    }

    @Test
    void testFind() {
        // Arrange
        Vehicle vehicle = new Vehicle("ABC1234", 195, 100.0, false);
        Client client = new Client("Maciek", "Walczak", new Address("Poznan", "akcja", "5"), new Gold());
        em.getTransaction().begin();
        em.persist(vehicle);
        em.persist(client);
        em.getTransaction().commit();
        Rent rent = new Rent(client, vehicle, LocalDateTime.now());

        // Act
        rentRepo.Add(rent);
        Rent foundRent = rentRepo.Find(rent.getId());

        // Assert
        assertNotNull(foundRent, "Found rent should not be null");
        assertEquals("Maciek", foundRent.getClient().getFirstName(), "The client's first name should match");
    }

    @Test
    void testGetAll() {
        // Arrange
        Vehicle vehicle1 = new Vehicle("ABC1234", 195, 100.0, false);
        Client client1 = new Client("Maciek", "Walczak", new Address("Poznan", "akcja", "5"), new Gold());
        em.getTransaction().begin();
        em.persist(vehicle1);
        em.persist(client1);
        em.getTransaction().commit();
        Rent rent1 = new Rent(client1, vehicle1, LocalDateTime.now());
        Vehicle vehicle2 = new Vehicle("XYZ0987", 591, 500.0, false);
        Client client2 = new Client("Ania", "Nowak", new Address("Wroclaw", "Kwiatowa", "10"), new Gold());
        em.getTransaction().begin();
        em.persist(vehicle2);
        em.persist(client2);
        em.getTransaction().commit();
        Rent rent2 = new Rent(client2, vehicle2, LocalDateTime.now());
        rentRepo.Add(rent1);
        rentRepo.Add(rent2);
        // Act

        List<Rent> rents = rentRepo.getAll();

        // Assert
        assertTrue(rents.contains(rent1), "Rent1 should be in the list of rents");
        assertTrue(rents.contains(rent2), "Rent2 should be in the list of rents");
    }

    @AfterEach
    void tearDown() {
        if (em != null) {
            em.clear();
            em.close();
        }
        rentRepo = null;
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
