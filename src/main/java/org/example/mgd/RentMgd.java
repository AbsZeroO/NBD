package org.example.mgd;

import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;

import java.time.Duration;
import java.time.LocalDateTime;

public class RentMgd extends AbstractEntityMgd {
    @BsonProperty("client")
    private final ClientAccountMgd clientAccountMgd;
    @BsonProperty("vehicle")
    private final VehicleMgd vehicleMgd;
    @BsonProperty("beginTime")
    private final LocalDateTime beginTime;
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
}
