package org.example.repositories.Rent;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.example.red.RentJsonb;
import org.example.repositories.AbstractRedisRepository;
import org.example.repositories.IRepo;
import redis.clients.jedis.exceptions.JedisDataException;
import redis.clients.jedis.json.Path;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class RentJsonbRepository extends AbstractRedisRepository implements IRepo<RentJsonb> {
    private final String prefix = "Rent:";
    private final ObjectMapper objectMapper;

    public RentJsonbRepository() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
        this.objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

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
        String key = prefix + id;
        try {
            String json = getPool().jsonGetAsPlainString(key, Path.of("."));
            if (json != null) {
                return getJsonb().fromJson(json, RentJsonb.class);
            } else {
                throw new JedisDataException("Data not found for ID: " + id);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<RentJsonb> findAll() {
        try {
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
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
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
        try {
            getPool().del(prefix + id);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
