package org.example.repo;

import com.datastax.oss.driver.api.querybuilder.QueryBuilder;
import com.datastax.oss.driver.api.querybuilder.truncate.Truncate;
import org.example.model.cassandra.RentCas;
import org.example.model.cassandra.RentCasByClient;
import org.example.model.cassandra.RentCasByVehicle;
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

        assertTrue(rents.stream().anyMatch(r -> r.getEntityId() == 4), "Rent with ID 4 should be present");
        assertTrue(rents.stream().anyMatch(r -> r.getEntityId() == 5), "Rent with ID 5 should be present");
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

        assertTrue(rents.stream().anyMatch(r -> r.getEntityId() == 8), "Rent with ID 8 should be present");
        assertTrue(rents.stream().anyMatch(r -> r.getEntityId() == 9), "Rent with ID 9 should be present");
    }

    @Test
    void testFindCurrentRentsByVehicle() {
        RentCas rent1 = new RentCas(
                10,
                111,
                210,
                LocalDateTime.now().minusDays(2),
                null,
                200.0,
                false
        );

        RentCas rent2 = new RentCas(
                11,
                112,
                210,
                LocalDateTime.now().minusDays(5),
                LocalDateTime.now().minusDays(1),
                300.0,
                true
        );

        RentCas rent3 = new RentCas(
                12,
                113,
                211,
                LocalDateTime.now().minusDays(3),
                null,
                150.0,
                false
        );

        repository.add(rent1);
        repository.add(rent2);
        repository.add(rent3);

        List<RentCasByVehicle> currentRents = repository.findCurrentRentsByVehicle(210);

        assertNotNull(currentRents, "Lista aktywnych wynajmów dla pojazdu 210 nie powinna być null");
        assertEquals(1, currentRents.size(), "Powinna być dokładnie jedna aktywna rezerwacja dla pojazdu 210");

        RentCasByVehicle activeRent = currentRents.get(0);
        assertEquals(10, activeRent.getEntityId(), "Aktywne wypożyczenie powinno mieć ID 10");
        assertEquals(210, activeRent.getVehicleCas(), "Aktywne wypożyczenie powinno dotyczyć pojazdu 210");
        assertFalse(activeRent.isArchived(), "Wypożyczenie powinno być aktywne");
    }

    @Test
    void testFindArchivedRentsByVehicle() {
        RentCas rent1 = new RentCas(
                13,
                114,
                210,
                LocalDateTime.now().minusDays(5),
                LocalDateTime.now().minusDays(1),
                250.0,
                true
        );

        RentCas rent2 = new RentCas(
                14,
                115,
                210,
                LocalDateTime.now().minusDays(10),
                LocalDateTime.now().minusDays(2),
                300.0,
                true
        );

        RentCas rent3 = new RentCas(
                15,
                116,
                210,
                LocalDateTime.now().minusDays(3),
                null,
                200.0,
                false
        );

        repository.add(rent1);
        repository.add(rent2);
        repository.add(rent3);

        List<RentCasByVehicle> archivedRents = repository.findArchivedRentsByVehicle(210);

        assertNotNull(archivedRents, "Lista zarchiwizowanych wynajmów dla pojazdu 210 nie powinna być null");
        assertEquals(2, archivedRents.size(), "Dla pojazdu 210 powinno być 2 zarchiwizowane wynajmy");

        assertTrue(archivedRents.stream().anyMatch(rent -> rent.getEntityId() == 13 && rent.isArchived()), "Wynajem o ID 13 powinien być zarchiwizowany");
        assertTrue(archivedRents.stream().anyMatch(rent -> rent.getEntityId() == 14 && rent.isArchived()), "Wynajem o ID 14 powinien być zarchiwizowany");

        assertFalse(archivedRents.stream().anyMatch(rent -> rent.getEntityId() == 15), "Wynajem o ID 15 nie powinien być zarchiwizowany i nie powinien być zwrócony");
    }

    @Test
    void testFindByClient() {
        RentCas rent1 = new RentCas(
                20,
                120,
                220,
                LocalDateTime.now(),
                null,
                180.0,
                false
        );

        RentCas rent2 = new RentCas(
                21,
                120,
                221,
                LocalDateTime.now(),
                null,
                220.0,
                false
        );

        repository.add(rent1);
        repository.add(rent2);

        List<RentCasByClient> rents = repository.findByClient(120);
        assertNotNull(rents, "Rents for client 120 should not be null");
        assertEquals(2, rents.size(), "Client should have two rent records");

        assertTrue(rents.stream().anyMatch(r -> r.getEntityId() == 20), "Rent with ID 20 should be present");
        assertTrue(rents.stream().anyMatch(r -> r.getEntityId() == 21), "Rent with ID 21 should be present");
    }

    @Test
    void testFindCurrentRentsByClient() {
        RentCas rent1 = new RentCas(
                22,
                121,
                222,
                LocalDateTime.now().minusDays(1),
                null,
                190.0,
                false
        );

        RentCas rent2 = new RentCas(
                23,
                121,
                223,
                LocalDateTime.now().minusDays(5),
                LocalDateTime.now().minusDays(2),
                210.0,
                true
        );

        RentCas rent3 = new RentCas(
                24,
                122,
                224,
                LocalDateTime.now().minusDays(2),
                null,
                240.0,
                false
        );

        repository.add(rent1);
        repository.add(rent2);
        repository.add(rent3);

        List<RentCasByClient> currentRents = repository.findCurrentRentsByClient(121);

        assertNotNull(currentRents, "Lista aktywnych wynajmów dla klienta 121 nie powinna być null");
        assertEquals(1, currentRents.size(), "Powinna być dokładnie jedna aktywna rezerwacja dla klienta 121");

        RentCasByClient activeRent = currentRents.get(0);
        assertEquals(22, activeRent.getEntityId(), "Aktywne wypożyczenie powinno mieć ID 22");
        assertEquals(121, activeRent.getClientAccountCas(), "Aktywne wypożyczenie powinno dotyczyć klienta 121");
        assertFalse(activeRent.isArchived(), "Wypożyczenie powinno być aktywne");
    }

    @Test
    void testFindArchivedRentsByClient() {
        RentCas rent1 = new RentCas(
                25,
                123,
                225,
                LocalDateTime.now().minusDays(6),
                LocalDateTime.now().minusDays(3),
                300.0,
                true
        );

        RentCas rent2 = new RentCas(
                26,
                123,
                226,
                LocalDateTime.now().minusDays(10),
                LocalDateTime.now().minusDays(5),
                280.0,
                true
        );

        RentCas rent3 = new RentCas(
                27,
                124,
                227,
                LocalDateTime.now().minusDays(3),
                null,
                260.0,
                false
        );

        repository.add(rent1);
        repository.add(rent2);
        repository.add(rent3);

        List<RentCasByClient> archivedRents = repository.findArchivedRentsByClient(123);

        assertNotNull(archivedRents, "Lista zarchiwizowanych wynajmów dla klienta 123 nie powinna być null");
        assertEquals(2, archivedRents.size(), "Dla klienta 123 powinno być 2 zarchiwizowane wynajmy");

        assertTrue(archivedRents.stream().anyMatch(rent -> rent.getEntityId() == 25 && rent.isArchived()), "Wynajem o ID 25 powinien być zarchiwizowany");
        assertTrue(archivedRents.stream().anyMatch(rent -> rent.getEntityId() == 26 && rent.isArchived()), "Wynajem o ID 26 powinien być zarchiwizowany");

        assertFalse(archivedRents.stream().anyMatch(rent -> rent.getEntityId() == 27), "Wynajem o ID 27 nie powinien być zarchiwizowany i nie powinien być zwrócony");
    }



}
