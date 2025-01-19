package org.example.model;


import org.apache.avro.Schema;
import org.apache.avro.specific.SpecificRecordBase;

import java.io.IOException;

public class Vehicle extends SpecificRecordBase {
    private int Id;

    private String plateNumber;

    private double basePrice;

    private int engineDisplacement;

    private int rented;

    private boolean archived;

    public Vehicle(int Id, String plateNumber, double basePrice, int engineDisplacement, int rented, boolean archived) {
        this.Id = Id;
        this.plateNumber = plateNumber;
        this.engineDisplacement =  engineDisplacement;
        this.basePrice = basePrice;
        this.rented = 0;
        this.archived = false;
    }

    public Vehicle() {
    }

    public String getVehicleInfo() {
        return "Plate Number: " + plateNumber
                + ", Base Price: " + basePrice;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getEngineDisplacement() {
        return engineDisplacement;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public int isRented() {
        return rented;
    }

    public boolean isArchived() {
        return archived;
    }

    public void setEngineDisplacement(int engineDisplacement) {
        this.engineDisplacement = engineDisplacement;
    }

    public void setPlateNumber(String plateNumber) {
        if (!plateNumber.isBlank()) {
            this.plateNumber = plateNumber;
        }
    }

    public void setBasePrice(double basePrice) {
        this.basePrice = basePrice;
    }

    public void setRented(int rented) {
        this.rented = rented;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }

    @Override
    public Schema getSchema() {
        try {
            return new Schema.Parser().parse(getClass().getResourceAsStream("src/main/java/org/example/avro/vehicle.avsc"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Object get(int field) {
        return switch (field) {
            case 0 -> Id;
            case 1 -> plateNumber;
            case 2 -> basePrice;
            case 3 -> engineDisplacement;
            case 4 -> rented;
            case 5 -> archived;
            default -> throw new IllegalArgumentException("Unknown field index: " + field);
        };
    }

    @Override
    public void put(int field, Object value) {
        switch (field) {
            case 0 -> Id = (int) value;
            case 1 -> plateNumber = (String) value;
            case 2 -> basePrice = (double) value;
            case 3 -> engineDisplacement = (int) value;
            case 4 -> rented = (int) value;
            case 5 -> archived = (boolean) value;
            default -> throw new IllegalArgumentException("Unknown field index: " + field);
        }
    }


}
