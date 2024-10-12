package org.example.model;

public class Car extends Vehicle {
    private final SegmentType segmentType;

    public Car(String plateNumber, SegmentType segmentType, double basePrice, boolean rented) {
        super(plateNumber, basePrice, rented);
        this.segmentType = segmentType;
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
