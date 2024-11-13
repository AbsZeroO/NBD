package org.example.mappers;

import org.bson.Document;
import org.example.mgd.BicycleMgd;
import org.example.mgd.CarMgd;
import org.example.mgd.VehicleMgd;
import org.example.model.Bicycle;
import org.example.model.Car;
import org.example.model.Vehicle;
import org.example.model.SegmentType;

public class VehicleMapper {

    private static final String ID = "_id";
    private static final String PLATE_NUMBER = "plate_number";
    private static final String BASE_PRICE = "base_price";
    private static final String ENGINE_DISPLACEMENT = "engine_displacement";
    private static final String IS_RENTED = "rented";
    private static final String IS_ARCHIVE = "archive";
    private static final String SEGMENT_TYPE = "segment_type";


    public static VehicleMgd vehicleToMongo(Vehicle vehicle) {
        if (vehicle instanceof Car) {
            return new CarMgd(
                    vehicle.getId(),
                    vehicle.getPlateNumber(),
                    vehicle.getBasePrice(),
                    vehicle.getEngineDisplacement(),
                    vehicle.isRented(),
                    vehicle.isArchived(),
                    ((Car) vehicle).getSegmentType()
            );
        } else if (vehicle instanceof Bicycle) {
            return new BicycleMgd(
                    vehicle.getId(),
                    vehicle.getPlateNumber(),
                    vehicle.getBasePrice(),
                    vehicle.getEngineDisplacement(),
                    vehicle.isRented(),
                    vehicle.isArchived()
            );
        } else {
            return null;
        }
    }
    public static Vehicle vehicleFromMongo(VehicleMgd vehicleMgd) {
        if (vehicleMgd instanceof CarMgd) {
            return new Car(
                    vehicleMgd.getEntityId(),
                    vehicleMgd.getPlateNumber(),
                    vehicleMgd.getEngineDisplacement(),
                    vehicleMgd.getBasePrice(),
                    vehicleMgd.isRented(),
                    vehicleMgd.isArchived(),
                    ((CarMgd) vehicleMgd).getSegmentType()
            );
        } else if (vehicleMgd instanceof BicycleMgd) {
            return new Bicycle(
                    vehicleMgd.getEntityId(),
                    vehicleMgd.getPlateNumber(),
                    vehicleMgd.getEngineDisplacement(),
                    vehicleMgd.getBasePrice(),
                    vehicleMgd.isRented(),
                    vehicleMgd.isArchived()
            );
        } else {
            return null;
        }
    }

    public static VehicleMgd toVehicleMgd(Document vehicleDocument) {
        if (vehicleDocument.get("_type").equals("car")) {
            return toCarMgd(vehicleDocument);
        }
        if (vehicleDocument.get("_type").equals("bicycle")) {
            return toBicycleMgd(vehicleDocument);
        }
        return null;
    }

    public static CarMgd toCarMgd(Document carDocument) {
        return new CarMgd(
                carDocument.get(ID, Integer.class),
                carDocument.getString(PLATE_NUMBER),
                carDocument.getDouble(BASE_PRICE),
                carDocument.getInteger(ENGINE_DISPLACEMENT),
                carDocument.getBoolean(IS_RENTED),
                carDocument.getBoolean(IS_ARCHIVE),
                SegmentType.valueOf(carDocument.getString(SEGMENT_TYPE))
        );
    }

    public static BicycleMgd toBicycleMgd(Document bicycleDocument) {
        return new BicycleMgd(
                bicycleDocument.get(ID, Integer.class),
                bicycleDocument.getString(PLATE_NUMBER),
                bicycleDocument.getDouble(BASE_PRICE),
                bicycleDocument.getInteger(ENGINE_DISPLACEMENT),
                bicycleDocument.getBoolean(IS_RENTED),
                bicycleDocument.getBoolean(IS_ARCHIVE)
        );
    }

}
