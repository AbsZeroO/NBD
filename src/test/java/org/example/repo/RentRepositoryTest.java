package org.example.repo;

import com.datastax.oss.driver.api.querybuilder.QueryBuilder;
import com.datastax.oss.driver.api.querybuilder.truncate.Truncate;
import org.example.model.cassandra.RentCas;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
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

    @AfterAll
    public static void teardown() {
        Truncate truncate = QueryBuilder.truncate("rent_a_vehicle", "rents");
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
    }

    @Test
    void testUpdateRentRecord() {
        RentCas rent = new RentCas(
                2,
                102,
                203,
                LocalDateTime.now(),
                null,
                0,
                false
        );

        repository.add(rent);

        rent.setArchived(true);
        boolean result = repository.update(rent);
        assertTrue(result, "Updating a rent record should return true");

        RentCas updated = repository.findById(2);
        assertNotNull(updated, "Updated rent record should not be null");
        assertTrue(updated.isArchived(), "Rent record should be archived");
    }

    @Test
    void testDeleteRentRecord() {
        RentCas rent = new RentCas(
                3,
                103,
                204,
                LocalDateTime.now(),
                null,
                0,
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
                0,
                false
        );

        RentCas rent2 = new RentCas(
                5,
                105,
                206,
                LocalDateTime.now(),
                null,
                0,
                false
        );

        repository.add(rent1);
        repository.add(rent2);

        List<RentCas> rents = repository.findAll();
        assertNotNull(rents, "Rent list should not be null");
        assertEquals(2, rents.size(), "Rent list should contain two records");
    }
}