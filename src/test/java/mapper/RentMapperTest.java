package mapper;

import org.bson.Document;
import org.example.mappers.RentMapper;
import org.example.mgd.*;
import org.example.model.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RentMapperTest {
    @Test
    void testRentToMongo() {
        Address address = new Address("Lodz", "Poniatowski", "50");
        Client client = new Client(1, "John", "Doe", address, ClientType.GOLD, false, 0);
        Vehicle vehicle = new Bicycle(1, "ABC123", 50000.0, 2000, 0, false);
        LocalDateTime beginTime = LocalDateTime.of(2024, Month.NOVEMBER, 14, 10, 0);
        Rent rent = new Rent(1, client, vehicle, beginTime);

        RentMgd rentMgd = RentMapper.rentToMongo(rent);

        assertEquals(rent.getId(), rentMgd.getEntityId());
        assertEquals(rent.getBeginTime(), rentMgd.getBeginTime());
        assertEquals(rent.getRentCost(), rentMgd.getRentCost());
        assertEquals(rent.isArchived(), rentMgd.isArchived());

        assertEquals(client.getId(), rentMgd.getClientAccountMgd().getEntityId());
        assertEquals(vehicle.getId(), rentMgd.getVehicleMgd().getEntityId());
    }

    @Test
    void testRentFromMongo() {
        AddressMgd addressMgd = new AddressMgd("Lodz", "Poniatowski", "50");
        ClientAccountMgd clientAccountMgd = new ClientAccountMgd(1, "John", "Doe", addressMgd, ClientType.GOLD, false, 0);
        VehicleMgd vehicleMgd = new BicycleMgd(1, "ABC123", 50000.0, 2000, 0, false);
        LocalDateTime beginTime = LocalDateTime.of(2024, Month.NOVEMBER, 14, 10, 0);
        LocalDateTime endTime = LocalDateTime.of(2024, Month.NOVEMBER, 15, 10, 0);
        double rentCost = 15000.0;
        boolean archived = false;

        RentMgd rentMgd = new RentMgd(1, clientAccountMgd, vehicleMgd, beginTime, endTime, rentCost, archived);

        Rent rent = RentMapper.rentFromMongo(rentMgd);

        assertEquals(rentMgd.getEntityId(), rent.getId());
        assertEquals(rentMgd.getBeginTime(), rent.getBeginTime());
        assertEquals(rentMgd.getEndTime(), rent.getEndTime());
        assertEquals(rentMgd.getRentCost(), rent.getRentCost());
        assertEquals(rentMgd.isArchived(), rent.isArchived());

        assertEquals(clientAccountMgd.getEntityId(), rent.getClient().getId());
        assertEquals(vehicleMgd.getEntityId(), rent.getVehicle().getId());
    }

    @Test
    void testToRentMgd() {
        Document address = new Document("city", "Łódź")
                .append("street", "Piotrowska")
                .append("houseNumber", "50");

        Document clientDoc = new Document("_id", 1)
                .append("firstName", "John")
                .append("lastName", "Doe")
                .append("address", address)
                .append("clientType", "GOLD")
                .append("archived", false)
                .append("rents", 0);

        Document vehicleDoc = new Document("_id", 1)
                .append("plateNumber", "ABC123")
                .append("basePrice", 50000.0)
                .append("engineDisplacement", 2000)
                .append("rented", 0)
                .append("archived", false)
                .append("segmentType", "B")
                .append("_type", "car");

        Document rentDoc = new Document("_id", 1)
                .append("client", clientDoc)
                .append("vehicle", vehicleDoc)
                .append("beginTime", java.util.Date.from(LocalDateTime.of(2024, Month.NOVEMBER, 14, 10, 0).atZone(java.time.ZoneId.systemDefault()).toInstant()))
                .append("endTime", java.util.Date.from(LocalDateTime.of(2024, Month.NOVEMBER, 15, 10, 0).atZone(java.time.ZoneId.systemDefault()).toInstant()))
                .append("rentCost", 100.0)
                .append("archived", false);

        RentMgd rentMgd = RentMapper.toRentMgd(rentDoc);

        assertEquals(rentDoc.getInteger("_id"), rentMgd.getEntityId());
        assertEquals(rentDoc.getDate("beginTime").toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime(), rentMgd.getBeginTime());
        assertEquals(rentDoc.getDate("endTime").toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime(), rentMgd.getEndTime());
        assertEquals(rentDoc.getDouble("rentCost"), rentMgd.getRentCost());
        assertEquals(rentDoc.getBoolean("archived"), rentMgd.isArchived());


        assertEquals(clientDoc.getInteger("_id"), rentMgd.getClientAccountMgd().getEntityId());
        assertEquals(vehicleDoc.getInteger("_id"), rentMgd.getVehicleMgd().getEntityId());
    }
}
