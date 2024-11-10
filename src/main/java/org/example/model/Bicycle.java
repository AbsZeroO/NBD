package org.example.model;


public class Bicycle extends Vehicle {

    public Bicycle(String plateNumber, int engineDisplacement, double basePrice, boolean rented) {
        super(plateNumber, engineDisplacement, basePrice, rented);
    }

    public Bicycle() {

    }

    public double getActualRentalPriceget() {
        return super.getBasePrice();
    }
}
