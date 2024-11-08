package org.example.model;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@DynamicUpdate
@PrimaryKeyJoinColumn(name = "bicycle_id")
@Table(name = "bicycle")
public class Bicycle extends Vehicle {

    public Bicycle(String plateNumber, int engineDisplacement, double basePrice, boolean rented) {
        super(plateNumber, engineDisplacement, basePrice, rented);
    }

    public Bicycle() {

    }

    public double getActualRentalPriceget() {
        return super.getBasePrice();
    }
}
