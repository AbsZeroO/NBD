package org.example.repo;

import com.datastax.oss.driver.api.querybuilder.QueryBuilder;
import com.datastax.oss.driver.api.querybuilder.truncate.Truncate;
import org.example.model.cassandra.BicycleCas;
import org.example.model.cassandra.CarCas;
import org.example.model.cassandra.VehicleCas;
import org.example.model.domain.SegmentType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class VehicleRepositoryTest {
    private static VehicleRepository repository;

    @BeforeAll
    public static void before() {
        repository = new VehicleRepository();
    }

    @AfterEach
    public void cleanup() {
        Truncate truncate = QueryBuilder.truncate("rent_a_vehicle", "vehicles");
        repository.getSession().execute(truncate.build());
    }

    @Test
    void testAddCar() {
        VehicleCas car = new CarCas(
                1,
                "ABC123",
                50.0,
                1800,
                false,
                false,
                SegmentType.E.name(),
                "car"
        );

        boolean result = repository.add(car);
        assertTrue(result, "Adding a car should return true");

        CarCas fetched = (CarCas) repository.findById(1);
        assertNotNull(fetched, "Fetched car should not be null");
        assertEquals("ABC123", fetched.getPlateNumber(), "Plate number should match");
    }

    @Test
    void testUpdateCar() {
        VehicleCas car = new CarCas(
                1,
                "XYZ789",
                70.0,
                2000,
                false,
                false,
                SegmentType.B.name(),
                "car"
        );

        repository.add(car);

        car.setBasePrice(80.0);
        boolean result = repository.update(car);
        assertTrue(result, "Updating a car should return true");

        VehicleCas updated = (CarCas) repository.findById(1);
        assertNotNull(updated, "Updated car should not be null");
        assertEquals(80.0, updated.getBasePrice(), 0.01, "Base price should be updated");
    }

    @Test
    void testDeleteCar() {
        VehicleCas car = new CarCas(
                1,
                "DEF456",
                60.0,
                1600,
                false,
                false,
                SegmentType.C.name(),
                "car"
        );

        repository.add(car);
        boolean result = repository.delete(car);
        assertTrue(result, "Deleting a car should return true");

    }

    @Test
    void testAddAndFetchAllVehicles() {
        VehicleCas car = new CarCas(
                1,
                "GHI123",
                40.0,
                1400,
                false,
                false,
                SegmentType.A.name(),
                "car"
        );

        VehicleCas bicycle = new BicycleCas(
                2,
                "BIKE567",
                10.0,
                100,
                false,
                false,
                "bicycyle"
        );

        repository.add(car);
        repository.add(bicycle);

        List<VehicleCas> vehicles = repository.findAll();
        assertNotNull(vehicles, "Vehicle list should not be null");
        assertEquals(2, vehicles.size(), "Vehicle list should contain two records");
    }

    @Test
    void testAddBicycle() {
        VehicleCas bicycle = new BicycleCas(
                2,
                "BIKE123",
                15.0,
                1050,
                false,
                false,
                "bicycyle"
        );

        boolean result = repository.add(bicycle);
        assertTrue(result, "Adding a bicycle should return true");

        VehicleCas fetched = repository.findById(2);
        assertNotNull(fetched, "Fetched bicycle should not be null");
        assertEquals("BIKE123", fetched.getPlateNumber(), "Plate number should match");
    }

    @Test
    void testUpdateBicycle() {
        VehicleCas bicycle = new BicycleCas(
                2,
                "BIKE999",
                12.0,
                50,
                false,
                false,
                "bicycyle"
        );

        repository.add(bicycle);

        bicycle.setBasePrice(20.0);
        boolean result = repository.update(bicycle);
        assertTrue(result, "Updating a bicycle should return true");

        VehicleCas updated = (BicycleCas) repository.findById(2);
        assertNotNull(updated, "Updated bicycle should not be null");
        assertEquals(20.0, updated.getBasePrice(), 0.01, "Base price should be updated");
    }

    @Test
    void testDeleteBicycle() {
        VehicleCas bicycle = new BicycleCas(
                2,
                "BIKE456",
                10.0,
                2500,
                false,
                false,
                "bicycyle"
        );

        repository.add(bicycle);
        boolean result = repository.delete(bicycle);
        assertTrue(result, "Deleting a bicycle should return true");

    }

}