package org.example.mgd;

import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.bson.codecs.pojo.annotations.BsonProperty;

@BsonDiscriminator(key = "_clientType")
public class ClientTypeMgd {

    @BsonProperty("maxVehicle")
    private int maxVehicle;

    @BsonProperty("discountRate")
    private double discountRate;

    @BsonProperty("typeInfo")
    private String typeInfo;

    @BsonCreator
    public ClientTypeMgd(@BsonProperty("maxVehicle") int maxVehicle,
                         @BsonProperty("discountRate") double discountRate,
                         @BsonProperty("typeInfo") String typeInfo) {
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
        return "ClientTypeMgd{" +
                "maxVehicle=" + maxVehicle +
                ", discountRate=" + discountRate +
                ", typeInfo='" + typeInfo + '\'' +
                '}';
    }
}