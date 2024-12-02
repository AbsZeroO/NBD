package org.example.model;


public class Vehicle {
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

}
