package org.example.model;


import org.apache.avro.Schema;

import java.io.IOException;

public class Car extends Vehicle {

    private SegmentType segmentType;

    public Car(int Id, String plateNumber, double basePrice, int engineDisplacement, int rented, boolean archived, SegmentType segmentType) {
        super(Id, plateNumber, basePrice, engineDisplacement, rented, archived);
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

    @Override
    public Schema getSchema() {
        try {
            return new Schema.Parser().parse(getClass().getResourceAsStream("src/main/java/org/example/avro/car.avsc"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
