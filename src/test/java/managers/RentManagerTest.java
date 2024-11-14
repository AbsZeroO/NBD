package managers;

import org.example.exceptions.RentException;
import org.example.managers.RentManager;
import org.example.mappers.ClientMapper;
import org.example.mappers.VehicleMapper;
import org.example.model.*;
import org.example.repositories.ClientMgdRepository;
import org.example.repositories.RentMgdRepository;
import org.example.repositories.VehicleMgdRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RentManagerTest {
    private static final RentMgdRepository repository = new RentMgdRepository();
    private static final RentManager rentManager = new RentManager(repository);



    @BeforeAll
    public static void beforeAll() throws Exception {
        repository.getMongodb().getCollection("clients").drop();
        repository.getMongodb().getCollection("vehicles").drop();
        repository.getMongodb().getCollection("rents").drop();
    }

    @AfterAll
    public static void afterAll() throws Exception {
        repository.close();
    }

    @AfterEach
    public void afterEach() {
        repository.getMongodb().getCollection("clients").drop();
        repository.getMongodb().getCollection("vehicles").drop();
        repository.getMongodb().getCollection("rents").drop();
    }


    @Test
    public void testRentVehicleSuccessfully() {
        Address address = new Address("Łódź", "Radwańska", "40");
        Client client = new Client(1, "Anna", "Kowalska", address, ClientType.SILVER, false);
        Car vehicle = new Car(3, "KLM 1234", 40.0, 150, 0, false, SegmentType.A);

        ClientMgdRepository clientMgdRepository = new ClientMgdRepository();
        VehicleMgdRepository vehicleMgdRepository = new VehicleMgdRepository();

        clientMgdRepository.add(ClientMapper.clientToMongo(client));
        vehicleMgdRepository.add(VehicleMapper.vehicleToMongo(vehicle));

        rentManager.rentVehicle(client, vehicle);

        assertTrue(vehicle.isRented() == 1, "Vehicle should be marked as rented.");
    }

    @Test
    public void testRentVehicleMaxLimitExceeded() {
        Address address = new Address("Łódź", "Radwańska", "40");
        Client client = new Client(2, "Jan", "Nowak", address, ClientType.BRONZE, false);
        Car vehicle1 = new Car(4, "XYZ 0001", 20.0, 110, 0, false, SegmentType.B);
        Car vehicle2 = new Car(5, "XYZ 0002", 22.0, 115, 0, false, SegmentType.C);
        Car vehicle3 = new Car(6, "XYZ 0003", 25.0, 120, 0, false, SegmentType.B);

        ClientMgdRepository clientMgdRepository = new ClientMgdRepository();
        VehicleMgdRepository vehicleMgdRepository = new VehicleMgdRepository();

        clientMgdRepository.add(ClientMapper.clientToMongo(client));
        vehicleMgdRepository.add(VehicleMapper.vehicleToMongo(vehicle1));
        vehicleMgdRepository.add(VehicleMapper.vehicleToMongo(vehicle2));
        vehicleMgdRepository.add(VehicleMapper.vehicleToMongo(vehicle3));

        rentManager.rentVehicle(client, vehicle1);
        rentManager.rentVehicle(client, vehicle2);


        assertThrows(RuntimeException.class, () -> rentManager.rentVehicle(client, vehicle3));
    }

    @Test
    public void testReturnVehicle() throws RentException {
        Address address = new Address("Łódź", "Radwańska", "40");
        Client client = new Client(3, "Maria", "Zielińska", address, ClientType.GOLD, false);
        Car vehicle = new Car(7, "XYZ 0004", 30.0, 140, 1, true, SegmentType.C);

        ClientMgdRepository clientMgdRepository = new ClientMgdRepository();
        VehicleMgdRepository vehicleMgdRepository = new VehicleMgdRepository();

        clientMgdRepository.add(ClientMapper.clientToMongo(client));
        vehicleMgdRepository.add(VehicleMapper.vehicleToMongo(vehicle));

        rentManager.rentVehicle(client, vehicle);
        LocalDateTime returnTime = LocalDateTime.now().plusDays(5);
        Vehicle returnedVehicle = rentManager.returnVehicle(6, returnTime);

        assertEquals(0, returnedVehicle.isRented(), "Vehicle should be marked as not rented.");
    }

    @Test
    public void testGetAllRents() throws RentException {
        Address address = new Address("Łódź", "Radwańska", "40");
        Client client = new Client(4, "Kamil", "Duda", address, ClientType.GOLD, false);
        Vehicle vehicle1 = new Car(8, "XYZ 0005", 45.0, 180, 0, false, SegmentType.B);
        Vehicle vehicle2 = new Car(9, "XYZ 0006", 50.0, 200, 0, false, SegmentType.A);

        ClientMgdRepository clientMgdRepository = new ClientMgdRepository();
        VehicleMgdRepository vehicleMgdRepository = new VehicleMgdRepository();

        clientMgdRepository.add(ClientMapper.clientToMongo(client));
        vehicleMgdRepository.add(VehicleMapper.vehicleToMongo(vehicle1));
        vehicleMgdRepository.add(VehicleMapper.vehicleToMongo(vehicle2));

        rentManager.rentVehicle(client, vehicle1);
        rentManager.rentVehicle(client, vehicle2);

        List<Rent> allRents = rentManager.getAllRents();

        assertEquals(2, allRents.size(), "There should be 2 active rents for this client.");
    }
}
