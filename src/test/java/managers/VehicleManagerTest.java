package managers;

import org.example.exceptions.ClientException;
import org.example.exceptions.RentException;
import org.example.exceptions.VehicleException;
import org.example.managers.VehicleManager;
import org.example.mappers.VehicleMapper;
import org.example.model.*;
import org.example.repositories.VehicleMgdRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class VehicleManagerTest {

    public static VehicleMgdRepository repository = new VehicleMgdRepository();
    public static VehicleManager vehicleManager = new VehicleManager(repository);

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
    public void registerTest() {
        Car vehicle1 = new Car(0,"LWD 0000", 25.0,125, 0, false, SegmentType.B);
        Bicycle vehicle2 = new Bicycle(1, "LWA aaaa", 500.0, 50, 0, false);


        vehicleManager.registerVehicle(vehicle1);

        Vehicle vehicleFromDb = VehicleMapper.vehicleFromMongo(repository.findById(vehicle1.getId()));

        assertEquals(vehicleFromDb.getVehicleInfo(), vehicle1.getVehicleInfo());

    }

    @Test
    public void deleteTest() {
        Car vehicle1 = new Car(0,"LWD 0000", 25.0,125, 0, false, SegmentType.B);
        Bicycle vehicle2 = new Bicycle(1, "LWA aaaa", 500.0, 50, 0, false);

        vehicleManager.registerVehicle(vehicle1);
        vehicleManager.registerVehicle(vehicle2);

        assertEquals(2, repository.findAll().size());

        vehicleManager.deletVehicle(vehicle2);

        assertEquals(1, repository.findAll().size());

    }

    @Test
    public void getClient() throws VehicleException {
        Car vehicle1 = new Car(0,"LWD 0000", 25.0,125, 0, false, SegmentType.B);
        Bicycle vehicle2 = new Bicycle(1, "LWA aaaa", 500.0, 50, 0, false);

        vehicleManager.registerVehicle(vehicle1);
        vehicleManager.registerVehicle(vehicle2);

        Vehicle vehicle2FromDataBase = vehicleManager.getVehicle(vehicle2.getId());

        assertEquals(vehicle2.getVehicleInfo(), vehicle2FromDataBase.getVehicleInfo());

    }

    @Test
    public void editTest() throws VehicleException {
        Car vehicle1 = new Car(0,"LWD 0000", 25.0,125, 0, false, SegmentType.B);
        Bicycle vehicle2 = new Bicycle(1, "LWA aaaa", 500.0, 50, 0, false);

        vehicleManager.registerVehicle(vehicle1);
        vehicleManager.registerVehicle(vehicle1);

        Double priceUpdate = 5000.50;
        String plateNumberUpdate = "NBD TO JEST TO";

        vehicle1.setPlateNumber(plateNumberUpdate);
        vehicle1.setBasePrice(priceUpdate);

        vehicleManager.edit(vehicle1);

        Vehicle vehicleFromDb = vehicleManager.getVehicle(vehicle1.getId());

        assertEquals(vehicle1.getVehicleInfo(), vehicleFromDb.getVehicleInfo());
        assertEquals(vehicle1.getPlateNumber(), vehicleFromDb.getPlateNumber());
        assertEquals(vehicle1.getBasePrice(), vehicleFromDb.getBasePrice());

    }

    @Test
    public void testGetAllRents() throws RentException {
        Car vehicle1 = new Car(0,"LWD 0000", 25.0,125, 0, false, SegmentType.B);
        Bicycle vehicle2 = new Bicycle(1, "LWA aaaa", 500.0, 50, 0, false);

        vehicleManager.registerVehicle(vehicle1);
        vehicleManager.registerVehicle(vehicle2);

        assertEquals(2, vehicleManager.getAllVehicles().size(), "There should be 2 active rents for this client.");
    }

}
