package org.example.model;


public class Gold extends ClientType {
    @Override
    public int getMaxVehicle() {
        return 7;
    }

    @Override
    public double applyDiscount(double price) {
        return price - (price * 0.7);
    }

    @Override
    public String getTypeInfo() {
        return "Gold";
    }
}
