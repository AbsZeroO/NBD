package org.example.repositories;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.bson.UuidRepresentation;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.Conventions;
import org.bson.codecs.pojo.PojoCodecProvider;

import java.util.List;

public abstract class AbstractMongoRepository implements AutoCloseable {
    public AbstractMongoRepository() {
        initDbConnection();
    }

    private final ConnectionString connectionString = new ConnectionString(
            "mongodb://localhost:27017,localhost:27018,localhost:27019/?replicaSet=replica_set_single"
    );

    private final MongoCredential mongoCredential = MongoCredential.createCredential("admin", "admin",
            "adminpassword".toCharArray()
    );

    private final CodecRegistry pojoCodecRegistry = CodecRegistries.fromProviders(
            PojoCodecProvider.builder()
                    .automatic(true)
                    .conventions(List.of(Conventions.ANNOTATION_CONVENTION))
                    .build()
    );

    private MongoClient mongoClient;
    private MongoDatabase mongodb;

    private void initDbConnection() {
        MongoClientSettings settings = MongoClientSettings.builder()
                .credential(mongoCredential)
                .applyConnectionString(connectionString)
                .uuidRepresentation(UuidRepresentation.STANDARD)
                .codecRegistry(
                        CodecRegistries.fromRegistries(
                                CodecRegistries.fromProviders(
                                        MongoClientSettings.getDefaultCodecRegistry(),
                                        pojoCodecRegistry
                                )
                        )
                )
                .build();

        mongoClient = MongoClients.create(settings);
        mongodb = mongoClient.getDatabase("rentalcar");
    }

    public MongoClient getMongoClient() {
        return mongoClient;
    }

    public MongoDatabase getMongodb() {
        return mongodb;
    }

    @Override
    public void close() {
        mongoClient.close();
    }

}
