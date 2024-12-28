package org.example.identity;

import com.datastax.oss.driver.api.core.CqlIdentifier;

public class VehicleIDs {
    public static final CqlIdentifier TABLE_NAME = CqlIdentifier.fromCql("vehicles");
    public static final CqlIdentifier ID = CqlIdentifier.fromCql("entity_id");
    public static final CqlIdentifier PLATE_NUMBER = CqlIdentifier.fromCql("plate_number");
    public static final CqlIdentifier BASE_PRICE = CqlIdentifier.fromCql("base_price");
    public static final CqlIdentifier ENGINE_DISPLACEMENT = CqlIdentifier.fromCql("engine_displacement");
    public static final CqlIdentifier RENTED = CqlIdentifier.fromCql("rented");
    public static final CqlIdentifier ARCHIVED = CqlIdentifier.fromCql("archived");
    public static final CqlIdentifier DISCRIMINATOR = CqlIdentifier.fromCql("discriminator");
    public static final CqlIdentifier SEGMENT_TYPE = CqlIdentifier.fromCql("segment_type");
}
