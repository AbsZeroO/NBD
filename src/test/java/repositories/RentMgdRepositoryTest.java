package repositories;

import com.mongodb.MongoWriteConcernException;
import com.mongodb.MongoWriteException;
import org.example.exceptions.RentException;
import org.example.mgd.*;
import org.example.model.ClientType;
import org.example.model.SegmentType;
import org.example.repositories.ClientMgdRepository;
import org.example.repositories.RentMgdRepository;
import org.example.repositories.VehicleMgdRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class RentMgdRepositoryTest {
    public static RentMgdRepository rentMgdRepository = new RentMgdRepository();

    @BeforeAll
    public static void beforeAll() throws Exception {
        rentMgdRepository.getMongodb().getCollection("rents").drop();
    }

    @AfterAll
    public static void afterAll() throws Exception {
        rentMgdRepository.close();
    }

    @AfterEach
    public void afterEach() {
        rentMgdRepository.getMongodb().getCollection("rents").drop();
        rentMgdRepository.getMongodb().getCollection("vehicles").drop();
    }

    @Test
    public void addTest() {
        AddressMgd address = new AddressMgd("Łódź", "Radwańska", "40");
        ClientAccountMgd client = new ClientAccountMgd(0, "Maciek", "Walaszek",
                address, ClientType.GOLD, false, 0);

        ClientAccountMgd client2 = new ClientAccountMgd(1, "Walek", "Walaszek",
                address, ClientType.GOLD, false, 0);

        CarMgd vehicle1 = new CarMgd(0,"LWD 0000", 25.0, 125, 0, false, SegmentType.B);

        RentMgd rentMgd = new RentMgd(0, client, vehicle1, LocalDateTime.now());

        rentMgdRepository.add(rentMgd);
        assertEquals(rentMgdRepository.findAll().size(), 1);
    }

    @Test
    public void updateTest() {
        AddressMgd address = new AddressMgd("Łódź", "Radwańska", "40");
        ClientAccountMgd client = new ClientAccountMgd(0, "Maciek", "Walaszek",
                address, ClientType.GOLD, false, 0);

        ClientAccountMgd client2 = new ClientAccountMgd(1, "Walek", "Walaszek",
                address, ClientType.GOLD, false, 0);

        CarMgd vehicle1 = new CarMgd(0,"LWD 0000", 25.0, 125, 0, false, SegmentType.B);

        RentMgd rentMgd = new RentMgd(0, client, vehicle1, LocalDateTime.now());

        rentMgdRepository.add(rentMgd);

        rentMgd.setClientAccountMgd(client2);

        rentMgdRepository.update(rentMgd);

        assertNotEquals(rentMgd.getClientAccountMgd().toString(), client.toString());

        RentMgd rentMgdUpdated = rentMgdRepository.findById(rentMgd.getEntityId());


        assertEquals(rentMgdUpdated.getClientAccountMgd().toString(), client2.toString());

        assertEquals(rentMgdRepository.findAll().size(), 1);

    }

    @Test
    public void deleteTest() {
        AddressMgd address = new AddressMgd("Łódź", "Radwańska", "40");
        ClientAccountMgd client = new ClientAccountMgd(0, "Maciek", "Walaszek",
                address, ClientType.GOLD, false, 0);

        ClientAccountMgd client2 = new ClientAccountMgd(1, "Walek", "Walaszek",
                address, ClientType.GOLD, false, 0);

        CarMgd vehicle1 = new CarMgd(0,"LWD 0000", 25.0,125, 1, false, SegmentType.B);
        BicycleMgd vehicle2 = new BicycleMgd(1, "LWA aaaa", 50.0, 500, 0, false);

        RentMgd rentMgd = new RentMgd(0, client, vehicle1, LocalDateTime.now());
        RentMgd rentMgd2 = new RentMgd(1, client2, vehicle1, LocalDateTime.now());


        rentMgdRepository.add(rentMgd);
        rentMgdRepository.add(rentMgd2);
        assertEquals(rentMgdRepository.findAll().size(), 2);

        rentMgdRepository.delete(rentMgd.getEntityId());
        assertEquals(rentMgdRepository.findAll().size(), 1);


        rentMgdRepository.delete(rentMgd2.getEntityId());
        assertEquals(rentMgdRepository.findAll().size(), 0);

    }

}
