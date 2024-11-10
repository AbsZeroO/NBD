package org.example.model;


public enum ClientType {

    SILVER(5, 0.4, "Silver"),
    GOLD(7, 0.7, "Gold"),
    BRONZE(2, 0.1, "Bronze");

    private final int maxVehicle;
    private final double discountRate;
    private final String typeInfo;

    ClientType(int maxVehicle, double discountRate, String typeInfo) {
        this.maxVehicle = maxVehicle;
        this.discountRate = discountRate;
        this.typeInfo = typeInfo;
    }


    public int getMaxVehicle() {
        return maxVehicle;
    }

    public double applyDiscount(double price) {
        return price - (price * discountRate);
    }

    public String getTypeInfo() {
        return typeInfo;
    }


}
