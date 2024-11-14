package org.example.mgd;

import com.mongodb.MongoWriteException;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.conversions.Bson;
import org.example.exceptions.RentException;
import org.example.repositories.ClientMgdRepository;
import org.example.repositories.RentMgdRepository;
import org.example.repositories.VehicleMgdRepository;

import java.time.Duration;
import java.time.LocalDateTime;

public class RentMgd extends AbstractEntityMgd {
    @BsonProperty("client")
    private ClientAccountMgd clientAccountMgd;
    @BsonProperty("vehicle")
    private VehicleMgd vehicleMgd;
    @BsonProperty("beginTime")
    private LocalDateTime beginTime;
    @BsonProperty("endTime")
    private LocalDateTime endTime;
    @BsonProperty("rentCost")
    private double rentCost;
    @BsonProperty("archived")
    private boolean isArchived;

    @BsonCreator
    public RentMgd(@BsonProperty("_id") int entityId,
                   @BsonProperty("client") ClientAccountMgd clientAccountMgd,
                   @BsonProperty("vehicle") VehicleMgd vehicleMgd,
                   @BsonProperty("beginTime") LocalDateTime beginTime,
                   @BsonProperty("endTime") LocalDateTime endTime,
                   @BsonProperty("rentCost") double rentCost,
                   @BsonProperty("archived") boolean isArchived) {
        super(entityId);
        this.clientAccountMgd = clientAccountMgd;
        this.vehicleMgd = vehicleMgd;
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.rentCost = rentCost;
        this.isArchived = isArchived;
    }

    public RentMgd(@BsonProperty("_id") int entityId,
                   @BsonProperty("client") ClientAccountMgd clientAccountMgd,
                   @BsonProperty("vehicle") VehicleMgd vehicleMgd,
                   @BsonProperty("beginTime") LocalDateTime beginTime) {
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
        return "RentMgd{" + "clientAccountMgd=" + clientAccountMgd +
                ", vehicleMgd=" + vehicleMgd +
                ", beginTime=" + beginTime +
                ", endTime=" + endTime +
                ", rentCost=" + rentCost +
                ", isArchived=" + isArchived +
                '}';
    }

    public ClientAccountMgd getClientAccountMgd() {
        return clientAccountMgd;
    }


    public VehicleMgd getVehicleMgd() {
        return vehicleMgd;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public void setRentCost(double rentCost) {
        this.rentCost = rentCost;
    }

    public void setClientAccountMgd(ClientAccountMgd clientAccountMgd) {
        this.clientAccountMgd = clientAccountMgd;
    }

    public void setVehicleMgd(VehicleMgd vehicleMgd) {
        this.vehicleMgd = vehicleMgd;
    }

    public void setBeginTime(LocalDateTime beginTime) {
        this.beginTime = beginTime;
    }
}
