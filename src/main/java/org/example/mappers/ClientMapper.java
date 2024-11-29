package org.example.mappers;

import org.bson.Document;
import org.example.mgd.AddressMgd;
import org.example.mgd.ClientAccountMgd;
import org.example.model.Client;
import org.example.model.Address;
import org.example.model.ClientType;
import org.example.red.AddressJsonb;
import org.example.red.ClientAccountJsonb;

public class ClientMapper {
    private static final String ID = "_id";
    private static final String ADDRESS = "address";
    private static final String ARCHIVED = "archived";
    private static final String CLIENT_TYPE = "clientType";
    private static final String FIRST_NAME = "firstName";
    private static final String LAST_NAME = "lastName";
    private static final String RENTS = "rents";

    public static ClientAccountMgd clientToMongo(Client client) {
        return new ClientAccountMgd(
                client.getId(),
                client.getFirstName(),
                client.getLastName(),
                toAddressMgd(client.getAddress()),
                client.getClientType(),
                client.isArchived(),
                client.getRents()
        );
    }

    public static Client clientFromMongo(ClientAccountMgd client) {
        return new Client(
                client.getEntityId(),
                client.getFirstName(),
                client.getLastName(),
                toAddress(client.getAddressMgd()),
                client.getClientType(),
                client.isArchived(),
                client.getRents()
        );
    }

    public static ClientAccountMgd toClientMgd(Document clientDocument) {
        // Jako iż zrobiliśmy że address jest poddokumentem to pobieramy dokument z adresem
        Document addressDoc = (Document) clientDocument.get(ADDRESS);
        AddressMgd addressMgd = new AddressMgd(
                addressDoc.getString("city"),
                addressDoc.getString("street"),
                addressDoc.getString("houseNumber")
        );

        return new ClientAccountMgd(
                clientDocument.getInteger(ID),
                clientDocument.getString(FIRST_NAME),
                clientDocument.getString(LAST_NAME),
                addressMgd,
                ClientType.valueOf(clientDocument.getString(CLIENT_TYPE)),
                clientDocument.getBoolean(ARCHIVED),
                clientDocument.getInteger(RENTS)
        );
    }

    public static AddressMgd toAddressMgd(Address address) {
        return new AddressMgd(
                address.getCity(),
                address.getStreet(),
                address.getHouseNumber()
        );
    }

    public static Address toAddress(AddressMgd addressMgd) {
        return new Address(
                addressMgd.getCity(),
                addressMgd.getStreet(),
                addressMgd.getHouseNumber()
        );
    }

    public static Client clientFromRedis(ClientAccountJsonb clientAccountJsonb) {
        return new Client(
                clientAccountJsonb.getEntityId(),
                clientAccountJsonb.getFirstName(),
                clientAccountJsonb.getLastName(),
                toAddress(clientAccountJsonb.getAddressJsonb()),
                clientAccountJsonb.getClientType(),
                clientAccountJsonb.isArchived(),
                clientAccountJsonb.getRents()
        );
    }

    public static ClientAccountJsonb clientToRedis(Client client) {
        return new ClientAccountJsonb(
                client.getId(),
                client.getFirstName(),
                client.getLastName(),
                toAddressJsonb(client.getAddress()),
                client.getClientType(),
                client.isArchived(),
                client.getRents()
        );
    }

    public static Address toAddress(AddressJsonb addressJsonb) {
        return new Address(
                addressJsonb.getCity(),
                addressJsonb.getStreet(),
                addressJsonb.getHouseNumber()
        );
    }

    public static AddressJsonb toAddressJsonb(Address address) {
        return new AddressJsonb(
                address.getCity(),
                address.getStreet(),
                address.getHouseNumber()
        );
    }
}
