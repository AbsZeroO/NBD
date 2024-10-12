package org.example.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("Bronze")
public class Bronze extends ClientType {
    @Override
    public int getMaxVehicle() {
        return 2;
    }

    /* Apply 10% discount for bronze Client*/
    @Override
    public double applyDiscount(double price) {
        return price - (price * 0.1);
    }

    @Override
    public String getTypeInfo() {
        return "Bronze";
    }
}
