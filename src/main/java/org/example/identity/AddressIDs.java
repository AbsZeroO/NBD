package org.example.identity;

import com.datastax.oss.driver.api.core.CqlIdentifier;

public class AddressIDs {
    public static final CqlIdentifier TABLE_NAME = CqlIdentifier.fromCql("address_cas");
    public static final CqlIdentifier CITY = CqlIdentifier.fromCql("city");
    public static final CqlIdentifier STREET = CqlIdentifier.fromCql("street");
    public static final CqlIdentifier HOUSE_NUMBER = CqlIdentifier.fromCql("house_number");
}
