package org.example.model;


import org.apache.avro.Schema;
import org.apache.avro.specific.SpecificRecordBase;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class Rent extends SpecificRecordBase {
    private int Id;

    public Rent() {
    }

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

    public Rent(int Id, Client client, Vehicle vehicle, LocalDateTime beginTime, LocalDateTime endTime, double rentCost, boolean isArchived) {
        this.Id = Id;
        this.client = client;
        this.vehicle = vehicle;
        this.rentCost =  rentCost;
        this.isArchived = isArchived;
        this.endTime = endTime;
        this.beginTime = beginTime;
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

    // Avro Schema
    @Override
    public Schema getSchema() {
        try {
            return new Schema.Parser().parse(getClass().getResourceAsStream("src/main/java/org/example/avro/rent.avsc"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Object get(int field) {
        return switch (field) {
            case 0 -> Id;
            case 1 -> client;
            case 2 -> vehicle;
            case 3 -> beginTime.toInstant(ZoneOffset.UTC).toEpochMilli();
            case 4 -> endTime != null ? endTime.toInstant(ZoneOffset.UTC).toEpochMilli() : null;
            case 5 -> rentCost;
            case 6 -> isArchived;
            default -> throw new IllegalArgumentException("Unknown field: " + field);
        };
    }

    // Avro put
    @Override
    public void put(int field, Object value) {
        switch (field) {
            case 0 -> Id = (int) value;
            case 1 -> client = (Client) value;
            case 2 -> vehicle = (Vehicle) value;
            case 3 -> beginTime = LocalDateTime.ofInstant(Instant.ofEpochMilli((long) value), ZoneOffset.UTC);
            case 4 -> endTime = value != null ? LocalDateTime.ofInstant(Instant.ofEpochMilli((long) value), ZoneOffset.UTC) : null;
            case 5 -> rentCost = (double) value;
            case 6 -> isArchived = (boolean) value;
            default -> throw new IllegalArgumentException("Unknown field: " + field);
        }
    }

}
