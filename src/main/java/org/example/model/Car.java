package org.example.model;


public class Car extends Vehicle {

    private SegmentType segmentType;

    public Car(String plateNumber, int engineDisplacement, double basePrice, boolean rented, SegmentType segmentType) {
        super(plateNumber, engineDisplacement, basePrice, rented);
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
