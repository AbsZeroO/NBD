package org.example.repositories;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.example.mappers.ClientMapper;
import org.example.mgd.ClientAccountMgd;
import org.example.model.Client;

import java.util.ArrayList;
import java.util.List;


public class ClientMgdRepository extends AbstractMongoRepository implements IRepo<Client> {
    private final MongoCollection<ClientAccountMgd> clients =
            getMongodb().getCollection("clients", ClientAccountMgd.class);

    public boolean add(Client client) {
        try {
            ClientAccountMgd clientMgd = ClientMapper.clientToMongo(client);
            return clients.insertOne(clientMgd).wasAcknowledged();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Client findById(int id) {
        try {
            Document clientDocument = getMongodb().getCollection("clients").find(Filters.eq("_id", id)).first();
            if (clientDocument != null) {
                ClientAccountMgd clientMgd = ClientMapper.toClientMgd(clientDocument);
                return ClientMapper.clientFromMongo(clientMgd);
            }
            else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean update(Client client) {
        try {
            ClientAccountMgd clientMgd = ClientMapper.clientToMongo(client);
            return clients.replaceOne(Filters.eq("_id", client.getId()), clientMgd).wasAcknowledged();
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

    public List<Client> findAll() {
        try {
            return clients.find()
                    .map(ClientMapper::clientFromMongo)
                    .into(new ArrayList<>());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
