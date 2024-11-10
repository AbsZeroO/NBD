package org.example.model;


public class Vehicle {

    private String plateNumber;

    private double basePrice;

    private int engineDisplacement;

    private boolean rented;

    private boolean archived;

    public Vehicle(String plateNumber, int engineDisplacement, double basePrice, boolean rented) {
        this.plateNumber = plateNumber;
        this.engineDisplacement =  engineDisplacement;
        this.basePrice = basePrice;
        this.rented = false;
        this.archived = false;
    }

    public String getVehicleInfo() {
        return "Plate Number: " + plateNumber
                + ", Base Price: " + basePrice;
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

    public boolean isRented() {
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

    public void setRented(boolean rented) {
        this.rented = rented;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }

}
