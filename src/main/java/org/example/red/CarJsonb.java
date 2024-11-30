package org.example.red;

import jakarta.json.bind.annotation.JsonbCreator;
import jakarta.json.bind.annotation.JsonbProperty;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.example.model.SegmentType;

public class CarJsonb extends VehicleJsonb {
    @JsonbProperty("segmentType")
    private SegmentType segmentType;

    @JsonbCreator
    public CarJsonb(@JsonbProperty("entityId") int entityId,
                    @JsonbProperty("plateNumber") String plateNumber,
                    @JsonbProperty("basePrice") double basePrice,
                    @JsonbProperty("engineDisplacement") int engineDisplacement,
                    @JsonbProperty("rented") int rented,
                    @JsonbProperty("archived") boolean archived,
                    @JsonbProperty("segmentType") SegmentType segmentType) {
        super(entityId, plateNumber, basePrice, engineDisplacement, rented, archived);
        this.segmentType = segmentType;
    }

    public CarJsonb() {
        super();
    }

    public SegmentType getSegmentType() {
        return segmentType;
    }

    public void setSegmentType(SegmentType segmentType) {
        this.segmentType = segmentType;
    }


}
