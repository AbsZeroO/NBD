package org.example.red;

import jakarta.json.bind.annotation.JsonbCreator;
import jakarta.json.bind.annotation.JsonbProperty;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.bson.codecs.pojo.annotations.BsonProperty;

public class BicycleJsonb extends VehicleJsonb {

    @JsonbCreator
    public BicycleJsonb(@JsonbProperty("_id") int entityId,
                        @JsonbProperty("plateNumber") String plateNumber,
                        @JsonbProperty("basePrice") double basePrice,
                        @JsonbProperty("engineDisplacement") int engineDisplacement,
                        @JsonbProperty("rented") int rented,
                        @JsonbProperty("archived") boolean archived) {
        super(entityId, plateNumber, basePrice, engineDisplacement, rented, archived);
    }

}
