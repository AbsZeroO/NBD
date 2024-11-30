package org.example.repositories.Rent;

import org.example.red.RentJsonb;
import org.example.repositories.AbstractRedisRepository;
import org.example.repositories.IRepo;
import redis.clients.jedis.json.Path;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class RentJsonbRepository extends AbstractRedisRepository implements IRepo<RentJsonb> {
    private final String prefix = "Rent:";

    public RentJsonbRepository() {
    }

    @Override
    public boolean add(RentJsonb entity) {
        getPool().jsonSet(prefix + entity.getEntityId(), getJsonb().toJson(entity));
        return true;
    }

    @Override
    public RentJsonb findById(int id) {
        return getJsonb().fromJson(getPool().jsonGetAsPlainString(prefix + id, Path.of(".")), RentJsonb.class);
    }

    @Override
    public List<RentJsonb> findAll() {
        return getPool().keys(prefix + "*").stream()
                .map(key -> getPool().jsonGetAsPlainString(key, Path.of(".")))
                .filter(Objects::nonNull)
                .map(json -> {
                    try {
                        return getJsonb().fromJson(json, RentJsonb.class);
                    } catch (Exception e) {
                        e.printStackTrace();
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Override
    public boolean update(RentJsonb entity) {
        getPool().jsonSet(prefix + entity.getEntityId(), getJsonb().toJson(entity));
        return true;
    }

    @Override
    public boolean delete(int id) {
        getPool().del(prefix + id);
        return true;
    }
}
