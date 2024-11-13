package org.example.mgd;

import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.example.model.SegmentType;

@BsonDiscriminator(key = "_type", value = "car")
public class CarMgd extends VehicleMgd {
    @BsonProperty("segmentType")
    private SegmentType segmentType;

    public CarMgd(@BsonProperty("_id") int entityId,
                  @BsonProperty("plateNumber") String plateNumber,
                  @BsonProperty("basePrice") double basePrice,
                  @BsonProperty("engineDisplacement") int engineDisplacement,
                  @BsonProperty("rented") boolean rented,
                  @BsonProperty("archived") boolean archived,
                  @BsonProperty("segmentType") SegmentType segmentType) {
        super(entityId, plateNumber, basePrice, engineDisplacement, rented, archived);
        this.segmentType = segmentType;
    }

    public SegmentType getSegmentType() {
        return segmentType;
    }

    public void setSegmentType(SegmentType segmentType) {
        this.segmentType = segmentType;
    }


    @Override
    public String toString() {
        return super.toString() + "CarMgd{" + "segmentType=" + segmentType +
                '}';
    }
}
