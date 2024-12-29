package org.example.repo;

import com.datastax.oss.driver.api.querybuilder.QueryBuilder;
import com.datastax.oss.driver.api.querybuilder.truncate.Truncate;
import org.example.model.cassandra.RentCas;
import org.example.model.cassandra.RentCasByClient;
import org.example.model.cassandra.RentCasByVehicle;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RentRepositoryTest {
    private static RentRepository repository;

    @BeforeAll
    public static void before() {
        repository = new RentRepository();
    }

    @AfterEach
    public void teardown() {
        Truncate truncate = QueryBuilder.truncate("rent_a_vehicle", "rents");
        repository.getSession().execute(truncate.build());
        truncate = QueryBuilder.truncate("rent_a_vehicle", "rent_by_client");
        repository.getSession().execute(truncate.build());
        truncate = QueryBuilder.truncate("rent_a_vehicle", "rent_by_vehicle");
        repository.getSession().execute(truncate.build());
    }

    @Test
    void testAddRentRecord() {
        RentCas rent = new RentCas(
                1,
                101,
                202,
                LocalDateTime.now(),
                null,
                0,
                false
        );

        boolean result = repository.add(rent);
        assertTrue(result, "Adding a rent record should return true");

        RentCas fetched = repository.findById(1);
        assertNotNull(fetched, "Fetched rent record should not be null");
        assertEquals(101, fetched.getClientAccountCas(), "Client account ID should match");
        assertEquals(202, fetched.getVehicleCas(), "Vehicle ID should match");
    }

    @Test
    void testUpdateRentRecord() {
        RentCas rent = new RentCas(
                2,
                102,
                203,
                LocalDateTime.now(),
                null,
                100.0,
                false
        );

        repository.add(rent);

        // Update the rent record
        rent.setArchived(true);
        rent.setRentCost(120.0);
        boolean result = repository.update(rent);
        assertTrue(result, "Updating a rent record should return true");

        RentCas updated = repository.findById(2);
        assertNotNull(updated, "Updated rent record should not be null");
        assertTrue(updated.isArchived(), "Rent record should be archived");
        assertEquals(120.0, updated.getRentCost(), "Rent cost should be updated");
    }

    @Test
    void testDeleteRentRecord() {
        RentCas rent = new RentCas(
                3,
                103,
                204,
                LocalDateTime.now(),
                null,
                50.0,
                false
        );

        repository.add(rent);
        boolean result = repository.delete(rent);
        assertTrue(result, "Deleting a rent record should return true");

        RentCas deleted = repository.findById(3);
        assertNull(deleted, "Deleted rent record should be null");
    }

    @Test
    void testFindAllRentRecords() {
        RentCas rent1 = new RentCas(
                4,
                104,
                205,
                LocalDateTime.now(),
                null,
                75.0,
                false
        );

        RentCas rent2 = new RentCas(
                5,
                105,
                206,
                LocalDateTime.now(),
                null,
                85.0,
                false
        );

        repository.add(rent1);
        repository.add(rent2);

        List<RentCas> rents = repository.findAll();
        assertNotNull(rents, "Rent list should not be null");
        assertEquals(2, rents.size(), "Rent list should contain two records");

        // Verify if the rents are present in the list
        assertTrue(rents.stream().anyMatch(r -> r.getEntityId() == 4), "Rent with ID 4 should be present");
        assertTrue(rents.stream().anyMatch(r -> r.getEntityId() == 5), "Rent with ID 5 should be present");
    }

    @Test
    void testFindByClient() {
        RentCas rent1 = new RentCas(
                6,
                106,
                207,
                LocalDateTime.now(),
                null,
                100.0,
                false
        );

        RentCas rent2 = new RentCas(
                7,
                106,
                208,
                LocalDateTime.now(),
                null,
                120.0,
                false
        );

        repository.add(rent1);
        repository.add(rent2);

        List<RentCasByClient> rents = repository.findByClient(106);
        assertNotNull(rents, "Rents for client 106 should not be null");
        assertEquals(2, rents.size(), "Client should have two rent records");

        // Verify if the correct rents are returned
        assertTrue(rents.stream().anyMatch(r -> r.getEntityId() == 6), "Rent with ID 6 should be present");
        assertTrue(rents.stream().anyMatch(r -> r.getEntityId() == 7), "Rent with ID 7 should be present");
    }

    @Test
    void testFindByVehicle() {
        RentCas rent1 = new RentCas(
                8,
                109,
                209,
                LocalDateTime.now(),
                null,
                150.0,
                false
        );

        RentCas rent2 = new RentCas(
                9,
                110,
                209,
                LocalDateTime.now(),
                null,
                170.0,
                false
        );

        repository.add(rent1);
        repository.add(rent2);

        List<RentCasByVehicle> rents = repository.findByVehicle(209);
        assertNotNull(rents, "Rents for vehicle 209 should not be null");
        assertEquals(2, rents.size(), "Vehicle should have two rent records");

        // Verify if the correct rents are returned
        assertTrue(rents.stream().anyMatch(r -> r.getEntityId() == 8), "Rent with ID 8 should be present");
        assertTrue(rents.stream().anyMatch(r -> r.getEntityId() == 9), "Rent with ID 9 should be present");
    }
}
