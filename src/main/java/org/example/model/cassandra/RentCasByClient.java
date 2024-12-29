package org.example.model.cassandra;

import com.datastax.oss.driver.api.mapper.annotations.ClusteringColumn;
import com.datastax.oss.driver.api.mapper.annotations.CqlName;
import com.datastax.oss.driver.api.mapper.annotations.Entity;
import com.datastax.oss.driver.api.mapper.annotations.PartitionKey;

import java.time.Duration;
import java.time.LocalDateTime;

@Entity(defaultKeyspace = "rent_a_vehicle")
@CqlName("rent_by_client")
public class RentCasByClient {
    @PartitionKey
    private int clientAccountCas;
    @ClusteringColumn
    int entityId;

    private int vehicleCas;

    private LocalDateTime beginTime;

    private LocalDateTime endTime;

    private double rentCost;

    private boolean isArchived;

    public RentCasByClient() {
    }

    public int getEntityId() {
        return entityId;
    }

    public void setEntityId(int entityId) {
        this.entityId = entityId;
    }

    public RentCasByClient(int entityId,
                           int clientAccountCas,
                           int vehicleCas,
                           LocalDateTime beginTime,
                           LocalDateTime endTime,
                           double rentCost,
                           boolean isArchived) {
        this.entityId = entityId;
        this.clientAccountCas = clientAccountCas;
        this.vehicleCas = vehicleCas;
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.rentCost = rentCost;
        this.isArchived = isArchived;
    }

    public RentCasByClient(int entityId,
                           int clientAccountCas,
                           int vehicleCas,
                           LocalDateTime beginTime) {
        this.entityId = entityId;
        this.clientAccountCas = clientAccountCas;
        this.vehicleCas = vehicleCas;
        this.beginTime = beginTime != null ? beginTime : LocalDateTime.now();
        this.isArchived = false;
        this.rentCost = 0;

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
        this.rentCost = getRentDays() * rentCost;
        this.isArchived = true;
    }

    public long getRentDays() {
        return Duration.between(beginTime, endTime != null ? endTime : LocalDateTime.now()).toDays();
    }

    @Override
    public String toString() {
        return "RentCas{" + "clientAccountCas=" + clientAccountCas +
                ", vehicleMgd=" + vehicleCas +
                ", beginTime=" + beginTime +
                ", endTime=" + endTime +
                ", rentCost=" + rentCost +
                ", isArchived=" + isArchived +
                '}';
    }

    public int getClientAccountCas() {
        return clientAccountCas;
    }


    public int getVehicleCas() {
        return vehicleCas;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public void setRentCost(double rentCost) {
        this.rentCost = rentCost;
    }

    public void setClientAccountCas(int clientAccountCas) {
        this.clientAccountCas = clientAccountCas;
    }

    public void setVehicleCas(int vehicleCas) {
        this.vehicleCas = vehicleCas;
    }

    public void setBeginTime(LocalDateTime beginTime) {
        this.beginTime = beginTime;
    }
}
