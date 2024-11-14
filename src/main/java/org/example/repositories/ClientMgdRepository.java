package org.example.repositories;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.example.mappers.ClientMapper;
import org.example.mgd.ClientAccountMgd;
import org.example.model.Client;

import java.util.ArrayList;
import java.util.List;


public class ClientMgdRepository extends AbstractMongoRepository implements IRepo<ClientAccountMgd> {
    private final MongoCollection<ClientAccountMgd> clients =
            getMongodb().getCollection("clients", ClientAccountMgd.class);

    private final MongoCollection<Document> clientDocs = getMongodb().getCollection("clients");
    public boolean add(ClientAccountMgd client) {
        try {
            return clients.insertOne(client).wasAcknowledged();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public ClientAccountMgd findById(int id) {
        try {
            Document clientDocument = clientDocs.find(Filters.eq("_id", id)).first();
            if (clientDocument != null) {
                return ClientMapper.toClientMgd(clientDocument);
            }
            else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean update(ClientAccountMgd client) {
        try {
            return clients.replaceOne(Filters.eq("_id", client.getEntityId()), client).wasAcknowledged();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(int id) {
        try {
            return clients.deleteOne(Filters.eq("_id", id)).wasAcknowledged();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<ClientAccountMgd> findAll() {
        try {
            return clients.find().into(new ArrayList<>());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
