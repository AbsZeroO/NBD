package org.example.model.domain;


public class Bicycle extends Vehicle {

    public Bicycle(int Id, String plateNumber, double basePrice, int engineDisplacement,  boolean rented, boolean archived) {
        super(Id, plateNumber, basePrice, engineDisplacement, rented, archived);
    }

    public double getActualRentalPriceget() {
        return super.getBasePrice();
    }
}
