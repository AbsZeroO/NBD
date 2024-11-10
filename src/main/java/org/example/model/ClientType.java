package org.example.model;


public abstract class ClientType {

    private Long id;

    public abstract int getMaxVehicle();
    public abstract double applyDiscount(double price);
    public abstract String getTypeInfo();


}
