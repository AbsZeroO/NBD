package validation;

import com.mongodb.MongoWriteException;
import org.example.managers.RentManager;
import org.example.mappers.ClientMapper;
import org.example.mappers.VehicleMapper;
import org.example.model.*;
import org.example.repositories.ClientMgdRepository;
import org.example.repositories.Rent.RentMgdRepository;
import org.example.repositories.VehicleMgdRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ClientValidationTest {
    private static final RentMgdRepository repository = new RentMgdRepository();
    private static final RentManager rentManager = new RentManager();

    @BeforeAll
    public static void beforeAll() {
        repository.getMongodb().getCollection("clients").drop();
        repository.getMongodb().getCollection("vehicles").drop();
        repository.getMongodb().getCollection("rents").drop();
    }

    @AfterAll
    public static void afterAll() {
        repository.close();
    }

    @AfterEach
    public void afterEach() {
        repository.getMongodb().getCollection("clients").drop();
        repository.getMongodb().getCollection("vehicles").drop();
        repository.getMongodb().getCollection("rents").drop();
    }


    @Test
    public void validation() {
        Address address = new Address("Łódź", "Radwańska", "40");
        Client client = new Client(2, "Jan", "Nowak", address, ClientType.BRONZE, false, 0);
        Car vehicle1 = new Car(4, "XYZ 0001", 20.0, 110, 0, false, SegmentType.B);
        Car vehicle2 = new Car(5, "XYZ 0002", 22.0, 115, 0, false, SegmentType.C);
        Car vehicle3 = new Car(6, "XYZ 0003", 25.0, 120, 0, false, SegmentType.B);
        Car vehicle4 = new Car(7, "XYZ 0001", 20.0, 110, 0, false, SegmentType.B);
        Car vehicle5 = new Car(8, "XYZ 0002", 22.0, 115, 0, false, SegmentType.C);
        Car vehicle6 = new Car(9, "XYZ 0003", 25.0, 120, 0, false, SegmentType.B);

        ClientMgdRepository clientMgdRepository = new ClientMgdRepository();
        VehicleMgdRepository vehicleMgdRepository = new VehicleMgdRepository();

        clientMgdRepository.add(ClientMapper.clientToMongo(client));
        vehicleMgdRepository.add(VehicleMapper.vehicleToMongo(vehicle1));
        vehicleMgdRepository.add(VehicleMapper.vehicleToMongo(vehicle2));
        vehicleMgdRepository.add(VehicleMapper.vehicleToMongo(vehicle3));
        vehicleMgdRepository.add(VehicleMapper.vehicleToMongo(vehicle4));
        vehicleMgdRepository.add(VehicleMapper.vehicleToMongo(vehicle5));
        vehicleMgdRepository.add(VehicleMapper.vehicleToMongo(vehicle6));

        rentManager.rentVehicle(client, vehicle1);
        rentManager.rentVehicle(client, vehicle2);
        rentManager.rentVehicle(client, vehicle3);
        rentManager.rentVehicle(client, vehicle4);
        rentManager.rentVehicle(client, vehicle5);

        assertThrows(MongoWriteException.class, () -> rentManager.rentVehicle(client, vehicle6));

    }
}
