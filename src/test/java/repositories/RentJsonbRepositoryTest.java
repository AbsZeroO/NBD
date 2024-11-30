package repositories;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import org.example.model.ClientType;
import org.example.model.SegmentType;
import org.example.red.*;
import org.example.repositories.Rent.RentJsonbRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class RentJsonbRepositoryTest {
    private static final RentJsonbRepository rentJsonbRepository = new RentJsonbRepository();

    @BeforeAll
    public static void beforAll() {
        rentJsonbRepository.clearCashe();
    }

    @AfterEach
    public void afterEach() {
        rentJsonbRepository.clearCashe();
    }

    @Test
    public void addTest() {
        AddressJsonb address = new AddressJsonb("Łódź", "Radwańska", "40");
        ClientAccountJsonb client = new ClientAccountJsonb(0, "Maciek", "Walaszek",
                address, ClientType.GOLD, false, 0);


        CarJsonb vehicle1 = new CarJsonb(0,"LWD 0000", 25.0, 125, 0, false, SegmentType.B);

        RentJsonb rentMgd = new RentJsonb(0, client, vehicle1, LocalDateTime.now());


        rentJsonbRepository.add(rentMgd);
        assertEquals(rentJsonbRepository.findAll().size(), 1);
    }

    @Test
    public void updateTest() {
        AddressJsonb address = new AddressJsonb("Łódź", "Radwańska", "40");
        ClientAccountJsonb client = new ClientAccountJsonb(0, "Maciek", "Walaszek",
                address, ClientType.GOLD, false, 0);

        ClientAccountJsonb client2 = new ClientAccountJsonb(1, "Walek", "Walaszek",
                address, ClientType.GOLD, false, 0);

        CarJsonb vehicle1 = new CarJsonb(0,"LWD 0000", 25.0, 125, 0, false, SegmentType.B);

        RentJsonb rentMgd = new RentJsonb(0, client, vehicle1, LocalDateTime.now());

        rentJsonbRepository.add(rentMgd);

        rentMgd.setClientAccountJsonb(client2);

        rentJsonbRepository.update(rentMgd);

        assertNotEquals(rentMgd.getClientAccountJsonb().toString(), client.toString());

        RentJsonb rentMgdUpdated = rentJsonbRepository.findById(rentMgd.getEntityId());


        assertEquals(rentMgdUpdated.getClientAccountJsonb().getEntityId(), client2.getEntityId());

        assertEquals(rentMgdUpdated.getClientAccountJsonb().getFirstName(), client2.getFirstName());
        assertEquals(rentMgdUpdated.getClientAccountJsonb().getClientType(), client2.getClientType());

        assertEquals(rentJsonbRepository.findAll().size(), 1);

    }

    @Test
    public void deleteTest() {
        AddressJsonb address = new AddressJsonb("Łódź", "Radwańska", "40");
        ClientAccountJsonb client = new ClientAccountJsonb(0, "Maciek", "Walaszek",
                address, ClientType.GOLD, false, 0);

        ClientAccountJsonb client2 = new ClientAccountJsonb(1, "Walek", "Walaszek",
                address, ClientType.GOLD, false, 0);

        CarJsonb vehicle1 = new CarJsonb(0,"LWD 0000", 25.0,125, 1, false, SegmentType.B);
        BicycleJsonb vehicle2 = new BicycleJsonb(1, "LWA aaaa", 50.0, 500, 0, false);

        RentJsonb rentMgd = new RentJsonb(0, client, vehicle1, LocalDateTime.now());
        RentJsonb rentMgd2 = new RentJsonb(1, client2, vehicle1, LocalDateTime.now());


        rentJsonbRepository.add(rentMgd);
        rentJsonbRepository.add(rentMgd2);
        assertEquals(rentJsonbRepository.findAll().size(), 2);

        rentJsonbRepository.delete(rentMgd.getEntityId());
        assertEquals(rentJsonbRepository.findAll().size(), 1);


        rentJsonbRepository.delete(rentMgd2.getEntityId());
        assertEquals(rentJsonbRepository.findAll().size(), 0);

    }

}
