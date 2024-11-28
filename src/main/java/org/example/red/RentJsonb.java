package org.example.red;

import jakarta.json.bind.annotation.JsonbCreator;
import jakarta.json.bind.annotation.JsonbProperty;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;

import java.time.Duration;
import java.time.LocalDateTime;

public class RentJsonb extends AbstractEntityJsonb {
    @JsonbProperty("client")
    private ClientAccountJsonb clientAccountMgd;
    @JsonbProperty("vehicle")
    private VehicleJsonb vehicleMgd;
    @JsonbProperty("beginTime")
    private LocalDateTime beginTime;
    @JsonbProperty("endTime")
    private LocalDateTime endTime;
    @JsonbProperty("rentCost")
    private double rentCost;
    @JsonbProperty("archived")
    private boolean isArchived;

    @JsonbCreator
    public RentJsonb(@JsonbProperty("_id") int entityId,
                     @JsonbProperty("client") ClientAccountJsonb clientAccountMgd,
                     @JsonbProperty("vehicle") VehicleJsonb vehicleMgd,
                     @JsonbProperty("beginTime") LocalDateTime beginTime,
                     @JsonbProperty("endTime") LocalDateTime endTime,
                     @JsonbProperty("rentCost") double rentCost,
                     @JsonbProperty("archived") boolean isArchived) {
        super(entityId);
        this.clientAccountMgd = clientAccountMgd;
        this.vehicleMgd = vehicleMgd;
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.rentCost = rentCost;
        this.isArchived = isArchived;
    }

    public RentJsonb(@JsonbProperty("_id") int entityId,
                     @JsonbProperty("client") ClientAccountJsonb clientAccountMgd,
                     @JsonbProperty("vehicle") VehicleJsonb vehicleMgd,
                     @JsonbProperty("beginTime") LocalDateTime beginTime) {
        super(entityId);
        this.clientAccountMgd = clientAccountMgd;
        this.vehicleMgd = vehicleMgd;
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

    public LocalDateTime getBeginTime() { return beginTime; }
    public LocalDateTime getEndTime() { return endTime; }
    public double getRentCost() { return rentCost; }
    public boolean isArchived() { return isArchived; }
    public void setArchived(boolean archived) { isArchived = archived; }

    public void endRent(LocalDateTime endTime) {
        this.endTime = endTime != null ? endTime : LocalDateTime.now();
        this.rentCost = getRentDays() * clientAccountMgd.getClientType().applyDiscount(vehicleMgd.getBasePrice());
        this.isArchived = true;
    }

    public long getRentDays() {
        return Duration.between(beginTime, endTime != null ? endTime : LocalDateTime.now()).toDays();
    }

    @Override
    public String toString() {
        return "RentJsonb{" + "clientAccountMgd=" + clientAccountMgd +
                ", vehicleMgd=" + vehicleMgd +
                ", beginTime=" + beginTime +
                ", endTime=" + endTime +
                ", rentCost=" + rentCost +
                ", isArchived=" + isArchived +
                '}';
    }

    public ClientAccountJsonb getClientAccountMgd() {
        return clientAccountMgd;
    }


    public VehicleJsonb getVehicleMgd() {
        return vehicleMgd;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public void setRentCost(double rentCost) {
        this.rentCost = rentCost;
    }

    public void setClientAccountMgd(ClientAccountJsonb clientAccountMgd) {
        this.clientAccountMgd = clientAccountMgd;
    }

    public void setVehicleMgd(VehicleJsonb vehicleMgd) {
        this.vehicleMgd = vehicleMgd;
    }

    public void setBeginTime(LocalDateTime beginTime) {
        this.beginTime = beginTime;
    }
}
