package org.example.repositories;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.example.mappers.RentMapper;
import org.example.mgd.RentMgd;

import java.util.ArrayList;
import java.util.List;

public class RentMgdRepository extends AbstractMongoRepository implements IRepo<RentMgd> {

    private final MongoCollection<RentMgd> rents =
            getMongodb().getCollection("rents", RentMgd.class);

    private final MongoCollection<Document> rentsDoc = getMongodb().getCollection("rents");

    @Override
    public boolean add(RentMgd rentMgd) {
        try {

            return rents.insertOne(rentMgd).wasAcknowledged();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public RentMgd findById(int id) {
        try {
            Document clientDocument = rentsDoc.find(Filters.eq("_id", id)).first();
            if (clientDocument != null) {
                return RentMapper.toRentMgd(clientDocument);
            }
            else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<RentMgd> findAll() {
        try {
            return rents.find().into(new ArrayList<>());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean update(RentMgd rentMgd) {
        try {
            return rents.replaceOne(Filters.eq("_id", rentMgd.getEntityId()), rentMgd).wasAcknowledged();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(int id) {
        try {
            return rents.deleteOne(Filters.eq("_id", id)).wasAcknowledged();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
