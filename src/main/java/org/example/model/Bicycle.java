package org.example.model;


public class Bicycle extends Vehicle {

    public Bicycle(int Id, String plateNumber, int engineDisplacement, double basePrice, int rented, boolean archived) {
        super(Id, plateNumber, engineDisplacement, basePrice, rented, archived);
    }

    public double getActualRentalPriceget() {
        return super.getBasePrice();
    }
}
