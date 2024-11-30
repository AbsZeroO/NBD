package org.example.red;

import jakarta.json.bind.annotation.JsonbCreator;
import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.json.bind.annotation.JsonbSubtype;
import jakarta.json.bind.annotation.JsonbTypeInfo;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.bson.codecs.pojo.annotations.BsonProperty;

@JsonbTypeInfo({
        @JsonbSubtype(alias = "vehicle", type = VehicleJsonb.class),
        @JsonbSubtype(alias = "car", type = CarJsonb.class),
        @JsonbSubtype(alias = "bicycle", type = BicycleJsonb.class)
})
public class VehicleJsonb extends AbstractEntityJsonb {

    @JsonbProperty("plateNumber")
    private String plateNumber;

    @JsonbProperty("basePrice")
    private double basePrice;

    @JsonbProperty("engineDisplacement")
    private int engineDisplacement;

    @JsonbProperty("rented")
    private int rented;

    @JsonbProperty("archived")
    private boolean archived;

    @JsonbCreator
    public VehicleJsonb(@JsonbProperty("entityId") int entityId,
                        @JsonbProperty("plateNumber") String plateNumber,
                        @JsonbProperty("basePrice") double basePrice,
                        @JsonbProperty("engineDisplacement") int engineDisplacement,
                        @JsonbProperty("rented") int rented,
                        @JsonbProperty("archived") boolean archived) {
        super(entityId);
        this.plateNumber = plateNumber;
        this.basePrice = basePrice;
        this.engineDisplacement = engineDisplacement;
        this.rented = rented;
        this.archived = archived;
    }

    public VehicleJsonb() {
        super();
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

    public int getRented() {
        return rented;
    }

    public void setRented(int rented) {
        this.rented = rented;
    }

    public boolean getArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }

    public boolean isArchived() {
        return archived;
    }
}
