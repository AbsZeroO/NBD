package org.example.mgd;

import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.bson.codecs.pojo.annotations.BsonProperty;

@BsonDiscriminator(key = "_vehicle", value = "bicycle")
public class BicycleMgd extends VehicleMgd {

    public BicycleMgd(@BsonProperty("_id") int entityId,
                  @BsonProperty("plateNumber") String plateNumber,
                  @BsonProperty("basePrice") double basePrice,
                  @BsonProperty("engineDisplacement") int engineDisplacement,
                  @BsonProperty("rented") boolean rented,
                  @BsonProperty("archived") boolean archived) {
        super(entityId, plateNumber, basePrice, engineDisplacement, rented, archived);
    }

}
