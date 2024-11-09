package org.example.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("Gold")
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
