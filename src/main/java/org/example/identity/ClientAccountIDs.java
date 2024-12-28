package org.example.identity;

import com.datastax.oss.driver.api.core.CqlIdentifier;

public class ClientAccountIDs {
    public static final CqlIdentifier TABLE_NAME = CqlIdentifier.fromCql("clients");
    public static final CqlIdentifier ID = CqlIdentifier.fromCql("entity_id");
    public static final CqlIdentifier FIRST_NAME = CqlIdentifier.fromCql("first_name");
    public static final CqlIdentifier LAST_NAME = CqlIdentifier.fromCql("last_name");
    public static final CqlIdentifier ADDRESS = CqlIdentifier.fromCql("address_cas");
    public static final CqlIdentifier CLIENT_TYPE = CqlIdentifier.fromCql("client_type");
    public static final CqlIdentifier IS_ARCHIVED = CqlIdentifier.fromCql("archived");
    public static final CqlIdentifier RENTS = CqlIdentifier.fromCql("rents");
}
