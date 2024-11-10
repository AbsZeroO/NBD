package org.example.repositories;

import com.mongodb.client.MongoCollection;
import org.example.mgd.ClientAccountMgd;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class ClientMgdRepository extends AbstractMongoRepository implements IRepo<ClientAccountMgd> {
    private final MongoCollection<ClientAccountMgd> clients =
            getMongodb().getCollection("clients", ClientAccountMgd.class);

    @Override
    public boolean Add(ClientAccountMgd entity) {
        return clients.insertOne(entity).wasAcknowledged();
    }

    @Override
    public ClientAccountMgd Find(long id) {
        return clients.find(eq("_id", id)).first();
    }

    @Override
    public List<ClientAccountMgd> getAll() {
        return clients.find().into(new ArrayList<>());
    }

    @Override
    public boolean Update(ClientAccountMgd entity) {
        return clients.replaceOne(eq("_id", entity.getEntityId()), entity).wasAcknowledged();
    }

    @Override
    public boolean Delete(int id) {
        return clients.deleteOne(eq("_id", id)).wasAcknowledged();
    }
}
