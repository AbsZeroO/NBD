package org.example.model.modelMapper;

import org.example.model.cassandra.BicycleCas;
import org.example.model.cassandra.CarCas;
import org.example.model.cassandra.VehicleCas;
import org.example.model.domain.Bicycle;
import org.example.model.domain.Car;
import org.example.model.domain.SegmentType;
import org.example.model.domain.Vehicle;

public class VehicleModelMapper {
    public static Vehicle toDomain(VehicleCas vehicleCas) {

        if (vehicleCas instanceof CarCas) {
            return new Car(
                    vehicleCas.getEntityId(),
                    vehicleCas.getPlateNumber(),
                    vehicleCas.getBasePrice(),
                    vehicleCas.getEngineDisplacement(),
                    vehicleCas.isRented(),
                    vehicleCas.isArchived(),
                    SegmentType.fromString(((CarCas) vehicleCas).getSegmentType())
            );
        } else if (vehicleCas instanceof BicycleCas) {
            return new Bicycle(
                    vehicleCas.getEntityId(),
                    vehicleCas.getPlateNumber(),
                    vehicleCas.getBasePrice(),
                    vehicleCas.getEngineDisplacement(),
                    vehicleCas.isRented(),
                    vehicleCas.isArchived()
            );
        } else {
            throw new IllegalStateException("Unsupported VehicleCas type");
        }

    }

    public static VehicleCas toVehicleCas(Vehicle vehicle) {
        if (vehicle instanceof Car) {
            return new CarCas(
                    vehicle.getId(),
                    vehicle.getPlateNumber(),
                    vehicle.getBasePrice(),
                    vehicle.getEngineDisplacement(),
                    vehicle.isRented(),
                    vehicle.isArchived(),
                    ((Car) vehicle).getSegmentType().toString(),
                    "car"
            );
        } else if (vehicle instanceof Bicycle) {
            return new BicycleCas(
                    vehicle.getId(),
                    vehicle.getPlateNumber(),
                    vehicle.getBasePrice(),
                    vehicle.getEngineDisplacement(),
                    vehicle.isRented(),
                    vehicle.isArchived(),
                    "bicycyle"
            );
        } else {
            throw new IllegalStateException("Unsupported VehicleCas type");
        }
    }
}
