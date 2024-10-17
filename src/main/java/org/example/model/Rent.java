package org.example.model;

import jakarta.persistence.*;

import java.time.Duration;
import java.time.LocalDateTime;

@Entity
@Table(name = "rent")
public class Rent {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne()
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;
    @ManyToOne()
    @JoinColumn(name = "vehicle_id", nullable = false)
    private Vehicle vehicle;

    @Column(name = "begin_time", nullable = false)
    private LocalDateTime beginTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @Column(name = "rent_cost")
    private double rentCost;

    @Version
    private long version;

    public Rent(long id, Client client, Vehicle vehicle, LocalDateTime beginTime) {
        this.id = id;
        this.client = client;
        this.vehicle = vehicle;
        this.vehicle.setRented(true);
        this.rentCost =  client.applyDiscount(vehicle.getBasePrice());

        if (beginTime == null) {
            this.beginTime = LocalDateTime.now();
        } else {
            this.beginTime = beginTime;
        }

        this.endTime = null;
    }

    public Rent() {
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

        this.vehicle.setRented(false);
        this.rentCost = getRentCost();
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
        return "id=" + id +
                ", client={" + client.getClientInfo() + "}" +
                ", vehicle={" + vehicle.getVehicleInfo() + "}" +
                ", beginTime=" + beginTime +
                ", endTime=" + (endTime != null ? endTime : "not yet ended");
    }

}
