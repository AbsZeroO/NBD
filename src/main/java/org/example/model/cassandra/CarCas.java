package org.example.model.cassandra;

import com.datastax.oss.driver.api.mapper.annotations.CqlName;
import com.datastax.oss.driver.api.mapper.annotations.Entity;
import org.example.model.domain.SegmentType;

@Entity(defaultKeyspace = "rent_a_vehicle")
@CqlName("vehicles")
public class CarCas extends VehicleCas {

    @CqlName("segment_type")
    private String segmentType;

    public CarCas() {
    }

    public CarCas(int entityId,
                  String plateNumber,
                  double basePrice,
                  int engineDisplacement,
                  boolean rented,
                  boolean archived,
                  String segmentType,
                  String discriminator) {
        super(entityId, plateNumber, basePrice, engineDisplacement, rented, archived, discriminator);
        this.segmentType = segmentType;
    }

    public String getSegmentType() {
        return segmentType;
    }

    public void setSegmentType(String segmentType) {
        this.segmentType = segmentType;
    }

    @Override
    public String toString() {
        return super.toString() + "CarCas{" + "segmentType=" + segmentType +
                '}';
    }
}
