package org.example.repositories;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.example.mappers.VehicleMapper;
import org.example.mgd.VehicleMgd;

import java.util.ArrayList;
import java.util.List;

public class VehicleMgdRepository extends AbstractMongoRepository implements IRepo<VehicleMgd> {

    private final MongoCollection<VehicleMgd> vehicles =
            getMongodb().getCollection("vehicles", VehicleMgd.class);

    private final MongoCollection<Document> vehiclesDoc = getMongodb().getCollection("vehicles");

    public boolean add(VehicleMgd vehicle) {
        try {
            return vehicles.insertOne(vehicle).wasAcknowledged();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public VehicleMgd findById(int id) {
        try {
            Document vehicleDocument = vehiclesDoc.find(Filters.eq("_id", id)).first();
            if (vehicleDocument != null) {
                return VehicleMapper.toVehicleMgd(vehicleDocument);
            }
            else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean update(VehicleMgd vehicle) {
        try {
            return vehicles.replaceOne(Filters.eq("_id", vehicle.getEntityId()), vehicle).wasAcknowledged();
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

    public List<VehicleMgd> findAll() {
        try {
            return vehicles.find().into(new ArrayList<>());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void updateRent(VehicleMgd vehicleMgd) {
        Bson filter = Filters.eq("_id", vehicleMgd.getEntityId());
        Bson update = Updates.inc("rented", 1);
        vehicles.updateOne(filter, update);
    }
}
