package repositories;

import org.example.model.*;
import org.example.repositories.Rent.RentFailOverRepository;
import org.example.repositories.Rent.RentJsonbRepository;
import org.example.repositories.Rent.RentMgdRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RentFailOverRepositoryTest {

    private RentFailOverRepository rentFailOverRepository;
    private RentJsonbRepository redisRepository;
    private RentMgdRepository mongodbRepository;

    @BeforeEach
    public void setup() {
        redisRepository = new RentJsonbRepository();
        mongodbRepository = new RentMgdRepository();
        rentFailOverRepository = new RentFailOverRepository(redisRepository, mongodbRepository);

        try {
            redisRepository.clearCashe();
        } catch (Exception e) {
            e.printStackTrace();
        }
        mongodbRepository.getMongodb().getCollection("rents").drop();
    }

    @AfterEach
    public void cleanup() {
        try {
            redisRepository.clearCashe();
            mongodbRepository.getMongodb().getCollection("rents").drop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testAdd() {
        Rent rent = createSampleRent(1);
        boolean added = rentFailOverRepository.add(rent);
        assertTrue(added);

        List<Rent> allRents = rentFailOverRepository.findAll();
        assertEquals(1, allRents.size());
        assertEquals(1, allRents.get(0).getId());
    }

    @Test
    public void testFindById() {
        Rent rent = createSampleRent(2);
        rentFailOverRepository.add(rent);

        Rent foundRent = rentFailOverRepository.findById(2);
        assertNotNull(foundRent);
        assertEquals(2, foundRent.getId());
    }

    @Test
    public void testUpdate() {
        Rent rent = createSampleRent(3);
        rentFailOverRepository.add(rent);

        rent.getClient().setFirstName("Updated Name");
        boolean updated = rentFailOverRepository.update(rent);
        assertTrue(updated);

        Rent updatedRent = rentFailOverRepository.findById(3);
        assertNotNull(updatedRent);
        assertEquals("Updated Name", updatedRent.getClient().getFirstName());
    }

    @Test
    public void testDeleteById() {
        Rent rent1 = createSampleRent(4);
        Rent rent2 = createSampleRent(5);
        rentFailOverRepository.add(rent1);
        rentFailOverRepository.add(rent2);

        boolean deleted = rentFailOverRepository.delete(4);
        assertTrue(deleted);

        List<Rent> remainingRents = rentFailOverRepository.findAll();
        assertEquals(1, remainingRents.size());
        assertEquals(5, remainingRents.get(0).getId());
    }

    @Test
    public void testFindAll() {
        Rent rent1 = createSampleRent(6);
        Rent rent2 = createSampleRent(7);
        rentFailOverRepository.add(rent1);
        rentFailOverRepository.add(rent2);

        List<Rent> allRents = rentFailOverRepository.findAll();
        assertEquals(2, allRents.size());
    }


    private Rent createSampleRent(int id) {
        Address address = new Address("Łódź", "Radwańska", "40");
        Client client = new Client(id, "Maciek", "Walaszek", address, ClientType.GOLD, false, 0);
        Car car = new Car(id, "LWD 0000", 25.0, 125, 0, false, SegmentType.B);
        return new Rent(id, client, car, LocalDateTime.now());
    }

}
