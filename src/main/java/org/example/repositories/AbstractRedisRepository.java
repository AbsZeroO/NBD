package org.example.repositories;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import redis.clients.jedis.DefaultJedisClientConfig;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisPooled;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Objects;

public class AbstractRedisRepository implements AutoCloseable {

    private static JedisPooled pool;
    private static Jsonb jsonb = JsonbBuilder.create();
    private static String host;
    private static int port;


    static {
        try {
            host = getNode("host");
            port = Integer.parseInt(Objects.requireNonNull(getNode("port")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public AbstractRedisRepository() {
        initConnection();
    }

    private void initConnection() {
        pool = new JedisPooled(new HostAndPort(host, port), DefaultJedisClientConfig.builder().build());

        pool.configSet("maxmemory", "10mb");
        pool.configSet("maxmemory-policy", "allkeys-lru");
    }

    public static String getNode(String key) throws IOException {
        String json = new String(Files.readAllBytes(Paths.get("src/main/java/org/example/config/config.json")));

        try (Jsonb jsonb = JsonbBuilder.create()) {
            Map<String, String> map = jsonb.fromJson(json, Map.class);
            return map.get(key).toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static JedisPooled getPool() {
        return pool;
    }

    public static Jsonb getJsonb() {
        return jsonb;
    }

    public boolean checkConnection() {
        return pool.getPool().getResource().isConnected();
    }

    public void clearCashe() {
        pool.flushDB();
    }

    @Override
    public void close() throws Exception {
        pool.close();
    }
}
