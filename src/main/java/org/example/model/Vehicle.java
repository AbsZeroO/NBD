package org.example.model;

import jakarta.persistence.*;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@DynamicUpdate
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "vehicle")
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "plate_number")
    private String plateNumber;

    @Column(name = "base_price")
    private double basePrice;

    @Column(name = "engine_displacement")
    private int engineDisplacement;

    @Column(name = "is_rented")
    private boolean rented;

    @Column(name = "is_archived")
    private boolean archived;

    public Vehicle(String plateNumber, int engineDisplacement, double basePrice, boolean rented) {
        this.plateNumber = plateNumber;
        this.engineDisplacement =  engineDisplacement;
        this.basePrice = basePrice;
        this.rented = false;
        this.archived = false;
    }

    public Vehicle() {

    }

    public String getVehicleInfo() {
        return "Plate Number: " + plateNumber
                + ", Base Price: " + basePrice;
    }

    public int getEngineDisplacement() {
        return engineDisplacement;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public boolean isRented() {
        return rented;
    }

    public boolean isArchived() {
        return archived;
    }

    public void setEngineDisplacement(int engineDisplacement) {
        this.engineDisplacement = engineDisplacement;
    }

    public void setPlateNumber(String plateNumber) {
        if (!plateNumber.isBlank()) {
            this.plateNumber = plateNumber;
        }
    }

    public void setBasePrice(double basePrice) {
        this.basePrice = basePrice;
    }

    public void setRented(boolean rented) {
        this.rented = rented;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }

    public Long getId() {
        return id;
    }
}
