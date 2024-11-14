package repositories;

import org.example.mgd.BicycleMgd;
import org.example.mgd.CarMgd;
import org.example.mgd.VehicleMgd;
import org.example.model.*;
import org.example.repositories.VehicleMgdRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class VehicleMgdRepositoryTest {
    public static VehicleMgdRepository vehicleMgdRepository = new VehicleMgdRepository();

    @BeforeAll
    public static void beforeAll() throws Exception {
        vehicleMgdRepository.getMongodb().getCollection("vehicles").drop();
    }

    @AfterAll
    public static void afterAll() throws Exception {
        vehicleMgdRepository.close();
    }

    @AfterEach
    public void afterEach() {
        vehicleMgdRepository.getMongodb().getCollection("vehicles").drop();
    }

    @Test
    public void addTest() {

        CarMgd vehicle1 = new CarMgd(0,"LWD 0000", 25.0,125, false, false, SegmentType.B);
        BicycleMgd vehicle2 = new BicycleMgd(1, "LWA aaaa", 50.0, 500, false, false);


        vehicleMgdRepository.add(vehicle1);
        vehicleMgdRepository.add(vehicle2);
        assertEquals(vehicleMgdRepository.findAll().size(), 2);
    }

    @Test
    public void updateTest() {
        CarMgd vehicle1 = new CarMgd(0,"LWD 0000", 25.0, 125, false, false, SegmentType.B);

        vehicleMgdRepository.add(vehicle1);

        String newPlateNumber = "SSS";

        vehicle1.setPlateNumber(newPlateNumber);


        vehicleMgdRepository.update(vehicle1);

        assertEquals(vehicle1.getPlateNumber(), newPlateNumber);

        VehicleMgd vehicleUpdated = vehicleMgdRepository.findById(vehicle1.getEntityId());

        assertEquals(vehicleMgdRepository.findAll().size(), 1);

        assertEquals(vehicleUpdated.getPlateNumber(), newPlateNumber);

    }

    @Test
    public void deleteTest() {
        CarMgd vehicle1 = new CarMgd(0,"LWD 0000", 25.0,125, false, false, SegmentType.B);
        BicycleMgd vehicle2 = new BicycleMgd(1, "LWA aaaa", 50.0, 500, false, false);


        vehicleMgdRepository.add(vehicle1);
        vehicleMgdRepository.add(vehicle2);
        assertEquals(vehicleMgdRepository.findAll().size(), 2);

        vehicleMgdRepository.delete(vehicle2.getEntityId());
        assertEquals(vehicleMgdRepository.findAll().size(), 1);


        vehicleMgdRepository.delete(vehicle1.getEntityId());
        assertEquals(vehicleMgdRepository.findAll().size(), 0);

    }
}
