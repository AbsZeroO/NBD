package org.example.model;

public class Bicycle extends Vehicle {

    public Bicycle(String plateNumber, double basePrice, boolean rented) {
        super(plateNumber, basePrice, rented);
    }

    public double getActualRentalPriceget() {
        return super.getBasePrice();
    }
}
