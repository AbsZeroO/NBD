package org.example.identity;

import com.datastax.oss.driver.api.core.CqlIdentifier;

public class RentIDs {
    public static final CqlIdentifier TABLE_NAME_NAMESPACE = CqlIdentifier.fromCql("rent_a_vehicle");
    public static final CqlIdentifier TABLE_NAME = CqlIdentifier.fromCql("rents");
    public static final CqlIdentifier TABLE_NAME_BY_CLIENTS = CqlIdentifier.fromCql("rents_by_clients");
    public static final CqlIdentifier TABLE_NAME_BY_VEHICLES = CqlIdentifier.fromCql("rents_by_vehicles");
    public static final CqlIdentifier RENT_ID = CqlIdentifier.fromCql("entity_id");
    public static final CqlIdentifier CLIENT_ACCOUNT_CAS = CqlIdentifier.fromCql("client_account_cas");
    public static final CqlIdentifier VEHICLE_CAS = CqlIdentifier.fromCql("vehicle_cas");
    public static final CqlIdentifier BEGIN_TIME = CqlIdentifier.fromCql("begin_time");
    public static final CqlIdentifier END_TIME = CqlIdentifier.fromCql("end_time");
    public static final CqlIdentifier RENT_COST = CqlIdentifier.fromCql("rent_cost");
    public static final CqlIdentifier ARCHIVED = CqlIdentifier.fromCql("archived");

}
