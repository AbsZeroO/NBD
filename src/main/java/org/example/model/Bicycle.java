package org.example.model;


import org.apache.avro.Schema;

import java.io.IOException;

public class Bicycle extends Vehicle {

    public Bicycle(int Id, String plateNumber, double basePrice, int engineDisplacement,  int rented, boolean archived) {
        super(Id, plateNumber, basePrice, engineDisplacement, rented, archived);
    }

    public Bicycle() {
    }

    public double getActualRentalPriceget() {
        return super.getBasePrice();
    }

    @Override
    public Schema getSchema() {
        try {
            return new Schema.Parser().parse(getClass().getResourceAsStream("src/main/java/org/example/avro/bicycle.avsc"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
