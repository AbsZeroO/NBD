package org.example.model;


public class Car extends Vehicle {

    private final SegmentType segmentType;

    public Car(int Id, String plateNumber, double basePrice, int engineDisplacement, int rented, boolean archived, SegmentType segmentType) {
        super(Id, plateNumber, basePrice, engineDisplacement, rented, archived);
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
