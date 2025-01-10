package org.example.manager;

import com.datastax.oss.driver.api.querybuilder.QueryBuilder;
import com.datastax.oss.driver.api.querybuilder.truncate.Truncate;
import org.example.model.domain.*;
import org.example.repo.RentRepository;
import org.example.repo.VehicleRepository;
import org.junit.jupiter.api.*;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RentManagerTest {
    private static RentManager manager;
    private static VehicleManager vehicleRepo;
    private static ClientManager clientManager;

    @BeforeAll
    public static void before() {
        manager = new RentManager();
        vehicleRepo = new VehicleManager();
        clientManager = new ClientManager();
    }

    @AfterAll
    public static void teardown() {
        Truncate truncate = QueryBuilder.truncate("rent_a_vehicle", "rents");
        manager.getRepository().getSession().execute(truncate.build());
        truncate = QueryBuilder.truncate("rent_a_vehicle", "rent_by_client");
        manager.getRepository().getSession().execute(truncate.build());
        truncate = QueryBuilder.truncate("rent_a_vehicle", "rent_by_vehicle");
        manager.getRepository().getSession().execute(truncate.build());
    }

    @Test
    void testRentAndReturnVehicle() throws Exception {
        Client client = new Client(101, "John", "Doe", new Address("New York", "5th Avenue", "123"), ClientType.GOLD, false, 0);
        Vehicle vehicle = new Bicycle(1, "ABC123", 100, 10, false, false);
        Rent rent = new Rent(1, client, vehicle, LocalDateTime.now(), null, 0.0, false);

        clientManager.add(client);
        vehicleRepo.add(vehicle);
        manager.rejntVehicle(rent);

        Rent fetched = manager.findRentById(1);
        assertNotNull(fetched, "Fetched rent record should not be null");
        assertEquals(1, fetched.getId(), "Rent ID should match");

        manager.returnVehicle(1, LocalDateTime.now());
        Rent returned = manager.findRentById(1);
        assertNotNull(returned.getEndTime(), "End time should be set");
    }

    @Test
    void testFindByClientId() throws Exception {
        Client client = new Client(102, "Jane", "Doe", new Address("Los Angeles", "Sunset Blvd", "456"), ClientType.SILVER, false, 0);
        Vehicle vehicle1 = new Bicycle(2, "DEF456", 50, 20, false, false);
        Vehicle vehicle2 = new Bicycle(3, "GHI789", 75, 15, false, false);
        Rent rent1 = new Rent(2, client, vehicle1, LocalDateTime.now().minusDays(1));
        Rent rent2 = new Rent(3, client, vehicle2, LocalDateTime.now().minusDays(2));

        clientManager.add(client);
        vehicleRepo.add(vehicle1);
        vehicleRepo.add(vehicle2);
        manager.rejntVehicle(rent1);
        manager.rejntVehicle(rent2);

        List<Rent> rents = manager.findByClientId(102);
        assertNotNull(rents, "Rents for client 102 should not be null");
        assertEquals(2, rents.size(), "Client should have two rent records");
    }

    @Test
    void testFindCurrentRentsByClientId() throws Exception {
        Client client = new Client(103, "Mike", "Smith", new Address("Chicago", "Lake Shore Drive", "789"), ClientType.GOLD, false, 0);
        Vehicle vehicle = new Bicycle(4, "JKL012", 120, 30, false, false);
        Rent rent = new Rent(4, client, vehicle, LocalDateTime.now().minusDays(1));

        clientManager.add(client);
        vehicleRepo.add(vehicle);
        manager.rejntVehicle(rent);

        List<Rent> currentRents = manager.findCurrentRentsByClientId(103);
        assertNotNull(currentRents, "Current rents for client 103 should not be null");
        assertEquals(1, currentRents.size(), "There should be exactly one current rent for client 103");
    }

    @Test
    void testFindArchivedRentsByClientId() throws Exception {
        Client client = new Client(104, "Anna", "Johnson", new Address("Miami", "Ocean Drive", "321"), ClientType.BRONZE, false, 0);
        Vehicle vehicle = new Bicycle(5, "MNO345", 90, 25, false, false);
        Rent rent = new Rent(5, client, vehicle, LocalDateTime.now().minusDays(5), LocalDateTime.now().minusDays(2), 100.0, true);

        clientManager.add(client);
        vehicleRepo.add(vehicle);
        manager.rejntVehicle(rent);
        manager.returnVehicle(5, LocalDateTime.now().minusDays(2));

        List<Rent> archivedRents = manager.findArchivedRentsByClientId(104);
        assertNotNull(archivedRents, "Archived rents for client 104 should not be null");
        assertEquals(1, archivedRents.size(), "Client 104 should have one archived rent");
    }

    @Test
    void testFindByVehicleId() throws Exception {
        Client client = new Client(105, "Chris", "Brown", new Address("Boston", "Harvard Street", "654"), ClientType.BRONZE, false, 0);
        Vehicle vehicle = new Bicycle(6, "PQR678", 60, 18, false, false);
        Rent rent = new Rent(6, client, vehicle, LocalDateTime.now().minusDays(1));

        clientManager.add(client);
        vehicleRepo.add(vehicle);
        manager.rejntVehicle(rent);

        List<Rent> rents = manager.findByVehicleId(6);
        assertNotNull(rents, "Rents for vehicle 6 should not be null");
        assertEquals(1, rents.size(), "Vehicle 6 should have one rent record");
    }

    @Test
    void testFindCurrentRentsByVehicleId() throws Exception {
        Client client = new Client(106, "Paul", "Davis", new Address("Seattle", "Main Street", "987"), ClientType.SILVER, false, 0);
        Vehicle vehicle = new Bicycle(7, "STU901", 110, 35, false, false);
        Rent rent = new Rent(7, client, vehicle, LocalDateTime.now().minusDays(1));

        clientManager.add(client);
        vehicleRepo.add(vehicle);
        manager.rejntVehicle(rent);

        List<Rent> currentRents = manager.findCurrentRentsByVehicleId(7);
        assertNotNull(currentRents, "Current rents for vehicle 7 should not be null");
        assertEquals(1, currentRents.size(), "There should be exactly one current rent for vehicle 7");
    }

    @Test
    void testFindArchivedRentsByVehicleId() throws Exception {
        Client client = new Client(107, "Laura", "Adams", new Address("Austin", "Congress Avenue", "159"), ClientType.GOLD, false, 0);
        Vehicle vehicle = new Bicycle(8, "VWX234", 95, 20, false, false);
        Rent rent = new Rent(8, client, vehicle, LocalDateTime.now().minusDays(5), LocalDateTime.now().minusDays(2), 120.0, true);

        clientManager.add(client);
        vehicleRepo.add(vehicle);
        manager.rejntVehicle(rent);
        manager.returnVehicle(8, LocalDateTime.now().minusDays(2));

        List<Rent> archivedRents = manager.findArchivedRentsByVehicleId(8);
        assertNotNull(archivedRents, "Archived rents for vehicle 8 should not be null");
        assertEquals(1, archivedRents.size(), "Vehicle 8 should have one archived rent");
    }
}
