package org.example.model;

import jakarta.persistence.*;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@DynamicUpdate
@PrimaryKeyJoinColumn(name = "car_id")
@Table(name = "car")
public class Car extends Vehicle {
    @Enumerated(EnumType.STRING)
    private SegmentType segmentType;

    public Car(String plateNumber, SegmentType segmentType, double basePrice, boolean rented) {
        super(plateNumber, basePrice, rented);
        this.segmentType = segmentType;
    }

    public Car() {

    }

    public SegmentType getSegmentType() {
        return segmentType;
    }

    public String getVehicleInfo() {
        return super.getVehicleInfo()
                + ", Segment Type: " + segmentType;
    }

    public double getActualRentalPrice() {
        return super.getBasePrice() * segmentType.getMultiplier();
    }
}
