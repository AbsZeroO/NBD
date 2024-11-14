package managers;

import com.mongodb.MongoWriteException;
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


import static org.junit.jupiter.api.Assertions.assertThrows;

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


    // To jest na poziomie logiki w repozytorium
    @Test
    public void maxVehicle() {
        Address address = new Address("Łódź", "Radwańska", "40");
        Client client = new Client(0, "Maciek", "Walaszek",
                address, ClientType.BRONZE, false);
        Car vehicle1 = new Car(0,"LWD 0000", 25.0, 125, 1, false, SegmentType.B);
        Car vehicle2 = new Car(1,"XYZ 1234", 30.0, 130, 0, false, SegmentType.C);
        Car vehicle3 = new Car(2,"ABC 5678", 28.5, 140, 1, false, SegmentType.B);

        VehicleMgdRepository vehicleMgdRepository = new VehicleMgdRepository();
        ClientMgdRepository clientMgdRepository = new ClientMgdRepository();

        clientMgdRepository.add(ClientMapper.clientToMongo(client));

        vehicleMgdRepository.add(VehicleMapper.vehicleToMongo(vehicle1));
        vehicleMgdRepository.add(VehicleMapper.vehicleToMongo(vehicle2));
        vehicleMgdRepository.add(VehicleMapper.vehicleToMongo(vehicle3));

        rentManager.rentVehicle(client, vehicle1);
        rentManager.rentVehicle(client, vehicle2);

        assertThrows(RuntimeException.class, () -> rentManager.rentVehicle(client, vehicle3));

    }
}
