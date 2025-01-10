package org.example.manager;

import com.datastax.oss.driver.api.querybuilder.QueryBuilder;
import com.datastax.oss.driver.api.querybuilder.truncate.Truncate;
import org.example.model.cassandra.BicycleCas;
import org.example.model.cassandra.CarCas;
import org.example.model.cassandra.VehicleCas;
import org.example.model.domain.Bicycle;
import org.example.model.domain.Car;
import org.example.model.domain.SegmentType;
import org.example.model.domain.Vehicle;
import org.example.repo.VehicleRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class VehicleManagerTest {
    private static VehicleManager manager;

    @BeforeAll
    public static void before() {
        manager = new VehicleManager();
    }

    @AfterEach
    public void cleanup() {
        Truncate truncate = QueryBuilder.truncate("rent_a_vehicle", "vehicles");
        manager.getRepository().getSession().execute(truncate.build());
    }

    @Test
    void testAddCar() {
        Vehicle car = new Car(
                1,
                "ABC123",
                50.0,
                1800,
                false,
                false,
                SegmentType.E
        );

        boolean result = manager.add(car);
        assertTrue(result, "Adding a car should return true");

        Car fetched = (Car) manager.findById(1);
        assertNotNull(fetched, "Fetched car should not be null");
        assertEquals("ABC123", fetched.getPlateNumber(), "Plate number should match");
    }

    @Test
    void testUpdateCar() {
        Vehicle car = new Car(
                1,
                "XYZ789",
                70.0,
                2000,
                false,
                false,
                SegmentType.B
        );

        manager.add(car);

        car.setBasePrice(80.0);
        boolean result = manager.update(car);
        assertTrue(result, "Updating a car should return true");

        Vehicle updated = (Car) manager.findById(1);
        assertNotNull(updated, "Updated car should not be null");
        assertEquals(80.0, updated.getBasePrice(), 0.01, "Base price should be updated");
    }

    @Test
    void testDeleteCar() {
        Vehicle car = new Car(
                1,
                "DEF456",
                60.0,
                1600,
                false,
                false,
                SegmentType.C
        );

        manager.add(car);
        boolean result = manager.delete(car);
        assertTrue(result, "Deleting a car should return true");

    }

    @Test
    void testAddAndFetchAllVehicles() {
        Vehicle car = new Car(
                1,
                "GHI123",
                40.0,
                1400,
                false,
                false,
                SegmentType.A
        );

        Vehicle bicycle = new Bicycle(
                2,
                "BIKE567",
                10.0,
                100,
                false,
                false
        );

        manager.add(car);
        manager.add(bicycle);

        List<Vehicle> vehicles = manager.findAll();
        assertNotNull(vehicles, "Vehicle list should not be null");
        assertEquals(2, vehicles.size(), "Vehicle list should contain two records");
    }

    @Test
    void testAddBicycle() {
        Vehicle bicycle = new Bicycle(
                2,
                "BIKE123",
                15.0,
                1050,
                false,
                false
        );

        boolean result = manager.add(bicycle);
        assertTrue(result, "Adding a bicycle should return true");

        Vehicle fetched = manager.findById(2);
        assertNotNull(fetched, "Fetched bicycle should not be null");
        assertEquals("BIKE123", fetched.getPlateNumber(), "Plate number should match");
    }

    @Test
    void testUpdateBicycle() {
        Vehicle bicycle = new Bicycle(
                2,
                "BIKE999",
                12.0,
                50,
                false,
                false
        );

        manager.add(bicycle);

        bicycle.setBasePrice(20.0);
        boolean result = manager.update(bicycle);
        assertTrue(result, "Updating a bicycle should return true");

        Vehicle updated = (Bicycle) manager.findById(2);
        assertNotNull(updated, "Updated bicycle should not be null");
        assertEquals(20.0, updated.getBasePrice(), 0.01, "Base price should be updated");
    }

    @Test
    void testDeleteBicycle() {
        Vehicle bicycle = new Bicycle(
                2,
                "BIKE456",
                10.0,
                2500,
                false,
                false
        );

        manager.add(bicycle);
        boolean result = manager.delete(bicycle);
        assertTrue(result, "Deleting a bicycle should return true");

    }
}