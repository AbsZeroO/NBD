package org.example.model;


import java.time.Duration;
import java.time.LocalDateTime;

public class Rent {
    private int Id;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    private Client client;
    private Vehicle vehicle;
    private LocalDateTime beginTime;
    private LocalDateTime endTime;
    private double rentCost;
    private boolean isArchived;

    public Rent(int Id, Client client, Vehicle vehicle, LocalDateTime beginTime) {
        this.Id = Id;
        this.client = client;
        this.vehicle = vehicle;
        this.rentCost =  client.applyDiscount(vehicle.getBasePrice());
        this.isArchived = false;

        if (beginTime == null) {
            this.beginTime = LocalDateTime.now();
        } else {
            this.beginTime = beginTime;
        }

        this.endTime = null;
    }

    public void endRent(LocalDateTime endTime) {
        if (endTime == null) {
            LocalDateTime currentTime = LocalDateTime.now();
            if (currentTime.isBefore(beginTime)) {
                this.endTime = beginTime;
            } else {
                this.endTime = currentTime;
            }
        } else {
            this.endTime = endTime;
        }

        this.rentCost = getRentCost();
        this.isArchived = true;
    }

    public long getRentDays() {
        if (endTime == null || beginTime.isAfter(endTime)) {
            return 0;
        }

        LocalDateTime beginTimeRounded = roundToMinutes(beginTime);
        LocalDateTime endTimeRounded = roundToMinutes(endTime);

        if (beginTimeRounded.equals(endTimeRounded)) {
            return 0;
        }

        Duration duration = Duration.between(beginTimeRounded, endTimeRounded);
        long totalHours = duration.toHours();
        long days = totalHours / 24;
        if (totalHours % 24 > 0) {
            days += 1;
        }
        return days;
    }

    private LocalDateTime roundToMinutes(LocalDateTime time) {
        return time.withSecond(0).withNano(0);
    }

    public double getRentCost() {
        return getRentDays() * client.applyDiscount(vehicle.getBasePrice());
    }

    public String getRentInfo() {
        return  ", client={" + client.getClientInfo() + "}" +
                ", vehicle={" + vehicle.getVehicleInfo() + "}" +
                ", beginTime=" + beginTime +
                ", endTime=" + (endTime != null ? endTime : "not yet ended");
    }


    public Client getClient() {
        return client;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public LocalDateTime getBeginTime() {
        return beginTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setRentCost(double rentCost) {
        this.rentCost = rentCost;
    }

    public boolean isArchived() {
        return isArchived;
    }

    public void setArchived(boolean archived) {
        isArchived = archived;
    }
}
