package org.example.model;


public class Bicycle extends Vehicle {

    public Bicycle(int Id, String plateNumber, double basePrice, int engineDisplacement,  int rented, boolean archived) {
        super(Id, plateNumber, basePrice, engineDisplacement, rented, archived);
    }

    public double getActualRentalPriceget() {
        return super.getBasePrice();
    }
}
