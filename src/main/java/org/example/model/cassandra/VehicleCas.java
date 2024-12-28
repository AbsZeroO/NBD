package org.example.model.cassandra;

import com.datastax.oss.driver.api.mapper.annotations.CqlName;
import com.datastax.oss.driver.api.mapper.annotations.Entity;

@Entity(defaultKeyspace = "rent_a_vehicle")
@CqlName("vehicles")
public class VehicleCas extends AbstractEntityCas {

    private String plateNumber;

    private double basePrice;

    private int engineDisplacement;

    private boolean rented;

    private boolean archived;

    private String discriminator;

    public VehicleCas(int entityId,
                      String plateNumber,
                      double basePrice,
                      int engineDisplacement,
                      boolean rented,
                      boolean archived,
                      String discriminator) {
        super(entityId);
        this.plateNumber = plateNumber;
        this.basePrice = basePrice;
        this.engineDisplacement = engineDisplacement;
        this.rented = rented;
        this.archived = archived;
        this.discriminator = discriminator;
    }

    public VehicleCas() {
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

    public String getDiscriminator() {
        return discriminator;
    }

    public void setDiscriminator(String discriminator) {
        this.discriminator = discriminator;
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

    public boolean isRented() {
        return rented;
    }

    public void setRented(boolean rented) {
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
        return "VehicleCas{" + "plateNumber='" + plateNumber + '\'' +
                ", basePrice=" + basePrice +
                ", engineDisplacement=" + engineDisplacement +
                ", rented=" + rented +
                ", archived=" + archived +
                '}';
    }
}
