package org.example.red;

import jakarta.json.bind.annotation.JsonbCreator;
import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.json.bind.annotation.JsonbTypeAdapter;

import java.time.Duration;
import java.time.LocalDateTime;

public class RentJsonb extends AbstractEntityJsonb {
    @JsonbProperty("client")
    private ClientAccountJsonb clientAccountJsonb;
    @JsonbProperty("vehicle")
    private VehicleJsonb vehicleJsonb;
    @JsonbProperty("beginTime")
    private LocalDateTime beginTime;
    @JsonbProperty("endTime")
    private LocalDateTime endTime;
    @JsonbProperty("rentCost")
    private double rentCost;
    @JsonbProperty("archived")
    private boolean isArchived;

    @JsonbCreator
    public RentJsonb(@JsonbProperty("entityId") int entityId,
                     @JsonbProperty("client") ClientAccountJsonb clientAccountMgd,
                     @JsonbProperty("vehicle") VehicleJsonb vehicleMgd,
                     @JsonbProperty("beginTime") LocalDateTime beginTime,
                     @JsonbProperty("endTime") LocalDateTime endTime,
                     @JsonbProperty("rentCost") double rentCost,
                     @JsonbProperty("archived") boolean isArchived) {
        super(entityId);
        this.clientAccountJsonb = clientAccountMgd;
        this.vehicleJsonb = vehicleMgd;
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.rentCost = rentCost;
        this.isArchived = isArchived;
    }

    public RentJsonb(@JsonbProperty("entityId") int entityId,
                     @JsonbProperty("client") ClientAccountJsonb clientAccountMgd,
                     @JsonbProperty("vehicle") VehicleJsonb vehicleMgd,
                     @JsonbProperty("beginTime") LocalDateTime beginTime) {
        super(entityId);
        this.clientAccountJsonb = clientAccountMgd;
        this.vehicleJsonb = vehicleMgd;
        this.beginTime = beginTime != null ? beginTime : LocalDateTime.now();
        this.isArchived = false;
        this.rentCost = clientAccountMgd.getClientType().applyDiscount(vehicleMgd.getBasePrice());

        if (beginTime == null) {
            this.beginTime = LocalDateTime.now();
        } else {
            this.beginTime = beginTime;
        }

        this.endTime = null;


    }

    public RentJsonb() {
        super();
    }

    public LocalDateTime getBeginTime() { return beginTime; }
    public LocalDateTime getEndTime() { return endTime; }
    public double getRentCost() { return rentCost; }
    public boolean getArchived() { return isArchived; }
    public void setArchived(boolean archived) { isArchived = archived; }

    public void endRent(LocalDateTime endTime) {
        this.endTime = endTime != null ? endTime : LocalDateTime.now();
        this.rentCost = getRentDays() * clientAccountJsonb.getClientType().applyDiscount(vehicleJsonb.getBasePrice());
        this.isArchived = true;
    }

    public long getRentDays() {
        return Duration.between(beginTime, endTime != null ? endTime : LocalDateTime.now()).toDays();
    }


    public ClientAccountJsonb getClientAccountJsonb() {
        return clientAccountJsonb;
    }


    public VehicleJsonb getVehicleJsonb() {
        return vehicleJsonb;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public void setRentCost(double rentCost) {
        this.rentCost = rentCost;
    }

    public void setClientAccountJsonb(ClientAccountJsonb clientAccountJsonb) {
        this.clientAccountJsonb = clientAccountJsonb;
    }

    public void setVehicleJsonb(VehicleJsonb vehicleJsonb) {
        this.vehicleJsonb = vehicleJsonb;
    }

    public void setBeginTime(LocalDateTime beginTime) {
        this.beginTime = beginTime;
    }
}
