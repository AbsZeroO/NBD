package org.example.mgd;

import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.bson.codecs.pojo.annotations.BsonProperty;

@BsonDiscriminator(key = "_type", value = "vehicle")
public class VehicleMgd extends AbstractEntityMgd {

    @BsonProperty("plateNumber")
    private String plateNumber;

    @BsonProperty("basePrice")
    private double basePrice;

    @BsonProperty("engineDisplacement")
    private int engineDisplacement;

    @BsonProperty("rented")
    private int rented;

    @BsonProperty("archived")
    private boolean archived;

    @BsonCreator
    public VehicleMgd(@BsonProperty("_id") int entityId,
                      @BsonProperty("plateNumber") String plateNumber,
                      @BsonProperty("basePrice") double basePrice,
                      @BsonProperty("engineDisplacement") int engineDisplacement,
                      @BsonProperty("rented") int rented,
                      @BsonProperty("archived") boolean archived) {
        super(entityId);
        this.plateNumber = plateNumber;
        this.basePrice = basePrice;
        this.engineDisplacement = engineDisplacement;
        this.rented = rented;
        this.archived = archived;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(double basePrice) {
        this.basePrice = basePrice;
    }

    public int getEngineDisplacement() {
        return engineDisplacement;
    }

    public void setEngineDisplacement(int engineDisplacement) {
        this.engineDisplacement = engineDisplacement;
    }

    public int isRented() {
        return rented;
    }

    public void setRented(int rented) {
        this.rented = rented;
    }

    public boolean isArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }

    @Override
    public String toString() {
        return "VehicleMgd{" + "plateNumber='" + plateNumber + '\'' +
                ", basePrice=" + basePrice +
                ", engineDisplacement=" + engineDisplacement +
                ", rented=" + rented +
                ", archived=" + archived +
                '}';
    }
}
