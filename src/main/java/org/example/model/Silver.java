package org.example.model;


public class Silver extends ClientType {
    @Override
    public int getMaxVehicle() {
        return 5;
    }

    @Override
    public double applyDiscount(double price) {
        return price - (price * 0.4);
    }

    @Override
    public String getTypeInfo() {
        return "Silver";
    }
}
