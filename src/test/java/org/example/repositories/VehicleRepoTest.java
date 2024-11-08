package org.example.repositories;

import jakarta.persistence.*;
import org.example.model.Vehicle;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class VehicleRepoTest {

    private static EntityManagerFactory emf;
    private static EntityManager em;
    private VehicleRepo vehicleRepo;

    @BeforeAll
    static void beforeAll() {
        emf = Persistence.createEntityManagerFactory("my-persistence-unit");
        em = emf.createEntityManager();
    }

    @BeforeEach
    void setUp() {
        em = emf.createEntityManager();
        vehicleRepo = new VehicleRepo();
    }

    @Test
    void testAdd() {
        // Arrange
        Vehicle vehicle = new Vehicle("ABC123", 1600, 150.0, false);

        // Act
        vehicleRepo.Add(vehicle);

        // Assert
        assertNotNull(vehicle.getId(), "Vehicle ID should not be null after adding to the database");
        assertEquals("ABC123", vehicle.getPlateNumber(), "The plate number should match the one we added");
    }

    @Test
    void testUpdate() {
        // Arrange
        Vehicle vehicle = new Vehicle("ABC123", 1600, 150.0, false);
        vehicleRepo.Add(vehicle);

        // Act
        vehicle.setPlateNumber("XYZ789");
        vehicle.setBasePrice(200.0);
        vehicleRepo.Update(vehicle);

        // Assert
        Vehicle updatedVehicle = vehicleRepo.Find(vehicle.getId());
        assertNotNull(updatedVehicle, "Updated vehicle should not be null");
        assertEquals("XYZ789", updatedVehicle.getPlateNumber(), "The plate number should be updated");
        assertEquals(200.0, updatedVehicle.getBasePrice(), "The base price should be updated");
    }

    @Test
    void testDelete() {
        // Arrange
        Vehicle vehicle = new Vehicle("ABC123", 1600, 150.0, false);
        vehicleRepo.Add(vehicle);

        // Act
        vehicleRepo.Delete(vehicle);

        // Assert
        assertNull(vehicleRepo.Find(vehicle.getId()), "Vehicle should be null after deletion");
    }

    @Test
    void testFind() {
        // Arrange
        Vehicle vehicle = new Vehicle("ABC123", 1600, 150.0, false);
        vehicleRepo.Add(vehicle);

        // Act
        Vehicle foundVehicle = vehicleRepo.Find(vehicle.getId());

        // Assert
        assertNotNull(foundVehicle, "Found vehicle should not be null");
        assertEquals("ABC123", foundVehicle.getPlateNumber(), "The plate number should match");
    }

    @Test
    void testGetAll() {
        // Arrange
        Vehicle vehicle1 = new Vehicle("ABC123", 1600, 150.0, false);
        Vehicle vehicle2 = new Vehicle("XYZ789", 2500, 200.0, true);
        vehicleRepo.Add(vehicle1);
        vehicleRepo.Add(vehicle2);

        // Act
        List<Vehicle> vehicles = vehicleRepo.getAll();

        // Assert
        assertEquals(2, vehicles.size(), "There should be 2 vehicles in the database");
        assertTrue(vehicles.contains(vehicle1), "Vehicle1 should be in the list of vehicles");
        assertTrue(vehicles.contains(vehicle2), "Vehicle2 should be in the list of vehicles");
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
