package org.example.red;

import jakarta.json.bind.annotation.JsonbCreator;
import jakarta.json.bind.annotation.JsonbProperty;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.bson.codecs.pojo.annotations.BsonProperty;

public class ClientTypeJsonb {

    @JsonbProperty("maxVehicle")
    private int maxVehicle;

    @JsonbProperty("discountRate")
    private double discountRate;

    @JsonbProperty("typeInfo")
    private String typeInfo;

    @JsonbCreator
    public ClientTypeJsonb(@JsonbProperty("maxVehicle") int maxVehicle,
                           @JsonbProperty("discountRate") double discountRate,
                           @JsonbProperty("typeInfo") String typeInfo) {
        this.maxVehicle = maxVehicle;
        this.discountRate = discountRate;
        this.typeInfo = typeInfo;
    }

    public int getMaxVehicle() {
        return maxVehicle;
    }

    public double getDiscountRate() {
        return discountRate;
    }

    public String getTypeInfo() {
        return typeInfo;
    }

    public void setMaxVehicle(int maxVehicle) {
        this.maxVehicle = maxVehicle;
    }

    public void setDiscountRate(double discountRate) {
        this.discountRate = discountRate;
    }

    public void setTypeInfo(String typeInfo) {
        this.typeInfo = typeInfo;
    }

    public double applyDiscount(double price) {
        return price - (price * discountRate);
    }

    @Override
    public String toString() {
        return "ClientTypeJsonb{" +
                "maxVehicle=" + maxVehicle +
                ", discountRate=" + discountRate +
                ", typeInfo='" + typeInfo + '\'' +
                '}';
    }
}