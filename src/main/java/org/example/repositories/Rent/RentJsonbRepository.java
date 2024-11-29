package org.example.repositories.Rent;

import org.example.red.RentJsonb;
import org.example.repositories.AbstractRedisRepository;
import org.example.repositories.IRepo;
import redis.clients.jedis.JedisPooled;

import java.util.List;
import java.util.stream.Collectors;

public class RentJsonbRepository extends AbstractRedisRepository implements IRepo<RentJsonb> {
    private final String prefix = "Rent:";

    @Override
    public boolean add(RentJsonb entity) {
        try {
            getPool().jsonSet(prefix + entity.getEntityId(), getJsonb().toJson(entity));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public RentJsonb findById(int id) {
        return getJsonb().fromJson(getPool().get(prefix + id), RentJsonb.class);
    }

    @Override
    public List<RentJsonb> findAll() {
        return getPool().keys(prefix + "*")
                .stream()
                .map(key -> getJsonb().fromJson(getPool().get(key), RentJsonb.class))
                .collect(Collectors.toList());
    }

    @Override
    public boolean update(RentJsonb entity) {
        try {
            getPool().jsonSet(prefix + entity.getEntityId(), getJsonb().toJson(entity));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean delete(int id) {
        return false;
    }
}
