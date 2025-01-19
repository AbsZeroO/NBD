package mapper;

import org.bson.Document;
import org.example.mappers.VehicleMapper;
import org.example.mgd.BicycleMgd;
import org.example.mgd.CarMgd;
import org.example.mgd.VehicleMgd;
import org.example.model.Bicycle;
import org.example.model.Car;
import org.example.model.SegmentType;
import org.example.model.Vehicle;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class VehicleMapperTest {
    @Test
     void testVehicleToMongo() {
        Vehicle car = new Car(1, "ABC123", 50000.0, 2000, 0, false, SegmentType.B);
        Vehicle bicycle = new Bicycle(2, "XYZ789", 1500.0, 20, 0, false);

        VehicleMgd carMgd = VehicleMapper.vehicleToMongo(car);
        assertEquals(car.getId(), carMgd.getEntityId());
        assertEquals(car.getPlateNumber(), carMgd.getPlateNumber());
        assertEquals(car.getBasePrice(), carMgd.getBasePrice());
        assertEquals(car.getEngineDisplacement(), carMgd.getEngineDisplacement());
        assertEquals(car.isRented(), carMgd.isRented());
        assertEquals(car.isArchived(), carMgd.isArchived());
        assertEquals(((CarMgd) carMgd).getSegmentType(), SegmentType.B);

        VehicleMgd bicycleMgd = VehicleMapper.vehicleToMongo(bicycle);
        assertEquals(bicycle.getId(), bicycleMgd.getEntityId());
        assertEquals(bicycle.getPlateNumber(), bicycleMgd.getPlateNumber());
        assertEquals(bicycle.getBasePrice(), bicycleMgd.getBasePrice());
        assertEquals(bicycle.getEngineDisplacement(), bicycleMgd.getEngineDisplacement());
        assertEquals(bicycle.isRented(), bicycleMgd.isRented());
        assertEquals(bicycle.isArchived(), bicycleMgd.isArchived());
    }

    @Test
    void testVehicleFromMongo() {
        CarMgd carMgd = new CarMgd(1, "ABC123", 50000.0, 2000, 0, false, SegmentType.B);
        BicycleMgd bicycleMgd = new BicycleMgd(2, "XYZ789", 1500.0, 0, 0, false);

        Vehicle car = VehicleMapper.vehicleFromMongo(carMgd);
        assertEquals(carMgd.getEntityId(), car.getId());
        assertEquals(carMgd.getPlateNumber(), car.getPlateNumber());
        assertEquals(carMgd.getBasePrice(), car.getBasePrice());
        assertEquals(carMgd.getEngineDisplacement(), car.getEngineDisplacement());
        assertEquals(carMgd.isRented(), car.isRented());
        assertEquals(carMgd.isArchived(), car.isArchived());
        assertEquals(((Car) car).getSegmentType(), SegmentType.B);

        Vehicle bicycle = VehicleMapper.vehicleFromMongo(bicycleMgd);
        assertEquals(bicycleMgd.getEntityId(), bicycle.getId());
        assertEquals(bicycleMgd.getPlateNumber(), bicycle.getPlateNumber());
        assertEquals(bicycleMgd.getBasePrice(), bicycle.getBasePrice());
        assertEquals(bicycleMgd.getEngineDisplacement(), bicycle.getEngineDisplacement());
        assertEquals(bicycleMgd.isRented(), bicycle.isRented());
        assertEquals(bicycleMgd.isArchived(), bicycle.isArchived());
    }

    @Test
    void testToVehicleMgd() {
        Document carDoc = new Document("_id", 1)
                .append("plateNumber", "ABC123")
                .append("basePrice", 50000.0)
                .append("engineDisplacement", 2000)
                .append("rented", 0)
                .append("archived", false)
                .append("segmentType", "A")
                .append("_type", "car");

        VehicleMgd carMgd = VehicleMapper.toVehicleMgd(carDoc);
        assertEquals(carDoc.getInteger("_id"), carMgd.getEntityId());
        assertEquals(carDoc.getString("plateNumber"), carMgd.getPlateNumber());
        assertEquals(carDoc.getDouble("basePrice"), carMgd.getBasePrice());
        assertEquals(carDoc.getInteger("engineDisplacement"), carMgd.getEngineDisplacement());
        assertEquals(carDoc.getInteger("rented"), carMgd.isRented());
        assertEquals(carDoc.getBoolean("archived"), carMgd.isArchived());
        assertEquals(SegmentType.valueOf(carDoc.getString("segmentType")), ((CarMgd) carMgd).getSegmentType());

        Document bicycleDoc = new Document("_id", 2)
                .append("plateNumber", "XYZ789")
                .append("basePrice", 1500.0)
                .append("engineDisplacement", 0)
                .append("rented", 0)
                .append("archived", false)
                .append("_type", "bicycle");

        VehicleMgd bicycleMgd = VehicleMapper.toVehicleMgd(bicycleDoc);
        assertEquals(bicycleDoc.getInteger("_id"), bicycleMgd.getEntityId());
        assertEquals(bicycleDoc.getString("plateNumber"), bicycleMgd.getPlateNumber());
        assertEquals(bicycleDoc.getDouble("basePrice"), bicycleMgd.getBasePrice());
        assertEquals(bicycleDoc.getInteger("engineDisplacement"), bicycleMgd.getEngineDisplacement());
        assertEquals(bicycleDoc.getInteger("rented"), bicycleMgd.isRented());
        assertEquals(bicycleDoc.getBoolean("archived"), bicycleMgd.isArchived());
    }

}
