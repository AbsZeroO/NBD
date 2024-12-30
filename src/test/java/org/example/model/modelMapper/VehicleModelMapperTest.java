package org.example.model.modelMapper;

import org.example.model.cassandra.BicycleCas;
import org.example.model.cassandra.CarCas;
import org.example.model.cassandra.VehicleCas;
import org.example.model.domain.Bicycle;
import org.example.model.domain.Car;
import org.example.model.domain.SegmentType;
import org.example.model.domain.Vehicle;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VehicleModelMapperTest {

    @Test
    void testToDomainWithCarCas() {
        VehicleCas carCas = new CarCas(1, "XYZ123", 100.0, 1500, false, false, "B", "car");

        Vehicle vehicle = VehicleModelMapper.toDomain(carCas);

        assertTrue(vehicle instanceof Car);
        Car car = (Car) vehicle;
        assertEquals(1, car.getId());
        assertEquals("XYZ123", car.getPlateNumber());
        assertEquals(100.0, car.getBasePrice());
        assertEquals(1500, car.getEngineDisplacement());
        assertEquals(SegmentType.B, car.getSegmentType());
    }

    @Test
    void testToDomainWithBicycleCas() {
        VehicleCas bicycleCas = new BicycleCas(2, "BIKE123", 50.0, 10, false, false, "bicycle");

        Vehicle vehicle = VehicleModelMapper.toDomain(bicycleCas);

        assertTrue(vehicle instanceof Bicycle);
        Bicycle bicycle = (Bicycle) vehicle;
        assertEquals(2, bicycle.getId());
        assertEquals("BIKE123", bicycle.getPlateNumber());
        assertEquals(50.0, bicycle.getBasePrice());
        assertEquals(10, bicycle.getEngineDisplacement());
    }

    @Test
    void testToVehicleCasWithCar() {
        Car car = new Car(1, "XYZ123", 100.0, 1500, false, false, SegmentType.A);

        VehicleCas vehicleCas = VehicleModelMapper.toVehicleCas(car);

        assertTrue(vehicleCas instanceof CarCas);
        CarCas carCas = (CarCas) vehicleCas;
        assertEquals(1, carCas.getEntityId());
        assertEquals("XYZ123", carCas.getPlateNumber());
        assertEquals(100.0, carCas.getBasePrice());
        assertEquals(1500, carCas.getEngineDisplacement());
        assertEquals("A", carCas.getSegmentType());
    }

    @Test
    void testToVehicleCasWithBicycle() {
        Bicycle bicycle = new Bicycle(2, "BIKE123", 50.0, 10, false, false);

        VehicleCas vehicleCas = VehicleModelMapper.toVehicleCas(bicycle);

        assertTrue(vehicleCas instanceof BicycleCas);
        BicycleCas bicycleCas = (BicycleCas) vehicleCas;
        assertEquals(2, bicycleCas.getEntityId());
        assertEquals("BIKE123", bicycleCas.getPlateNumber());
        assertEquals(50.0, bicycleCas.getBasePrice());
        assertEquals(10, bicycleCas.getEngineDisplacement());
    }

    @Test
    void testToDomainWithUnsupportedVehicleCas() {
        VehicleCas unknownVehicleCas = new VehicleCas(3, "UNKNOWN123", 100.0, 1000, false, false, "unknown");

        Exception exception = assertThrows(IllegalStateException.class, () -> {
            VehicleModelMapper.toDomain(unknownVehicleCas);
        });

        assertEquals("Unsupported VehicleCas type", exception.getMessage());
    }

    @Test
    void testToVehicleCasWithUnsupportedVehicle() {
        Vehicle unknownVehicle = new Vehicle(3, "UNKNOWN123", 100.0, 1000, false, false) {};

        Exception exception = assertThrows(IllegalStateException.class, () -> {
            VehicleModelMapper.toVehicleCas(unknownVehicle);
        });

        assertEquals("Unsupported VehicleCas type", exception.getMessage());
    }
}