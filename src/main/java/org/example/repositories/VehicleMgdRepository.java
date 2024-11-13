package org.example.repositories;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.example.mappers.VehicleMapper;
import org.example.mgd.VehicleMgd;
import org.example.model.Vehicle;

import java.util.ArrayList;
import java.util.List;

public class VehicleMgdRepository extends AbstractMongoRepository implements IRepo<Vehicle> {

    private final MongoCollection<VehicleMgd> vehicles =
            getMongodb().getCollection("vehicles", VehicleMgd.class);

    public boolean add(Vehicle vehicle) {
        try {
            VehicleMgd vehicleMgd = VehicleMapper.vehicleToMongo(vehicle);
            return vehicles.insertOne(vehicleMgd).wasAcknowledged();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Vehicle findById(int id) {
        try {
            Document vehicleDocument = getMongodb().getCollection("vehicles").find(Filters.eq("_id", id)).first();
            if (vehicleDocument != null) {
                VehicleMgd vehicleMgd = VehicleMapper.toVehicleMgd(vehicleDocument);
                return VehicleMapper.vehicleFromMongo(vehicleMgd);
            }
            else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean update(Vehicle vehicle) {
        try {
            VehicleMgd vehicleMgd = VehicleMapper.vehicleToMongo(vehicle);
            return vehicles.replaceOne(Filters.eq("_id", vehicle.getId()), vehicleMgd).wasAcknowledged();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(int id) {
        try {
            return vehicles.deleteOne(Filters.eq("_id", id)).wasAcknowledged();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Vehicle> findAll() {
        try {
            return vehicles.find()
                    .map(VehicleMapper::vehicleFromMongo)
                    .into(new ArrayList<>());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
