package org.example.model;


public class Car extends Vehicle {

    private SegmentType segmentType;

    public Car(int Id, String plateNumber, int engineDisplacement, double basePrice, boolean rented, boolean archived, SegmentType segmentType) {
        super(Id, plateNumber, engineDisplacement, basePrice, rented, archived);
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
