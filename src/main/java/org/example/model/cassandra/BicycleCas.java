package org.example.model.cassandra;


import com.datastax.oss.driver.api.mapper.annotations.CqlName;
import com.datastax.oss.driver.api.mapper.annotations.Entity;

@Entity(defaultKeyspace = "rent_a_vehicle")
@CqlName("vehicles")
public class BicycleCas extends VehicleCas {

    public BicycleCas() {
    }

    public BicycleCas(int entityId,
                      String plateNumber,
                      double basePrice,
                      int engineDisplacement,
                      boolean rented,
                      boolean archived,
                      String discriminator) {
        super(entityId, plateNumber, basePrice, engineDisplacement, rented, archived, discriminator);
    }

}
