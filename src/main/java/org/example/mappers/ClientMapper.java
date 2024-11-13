package org.example.mappers;

import org.bson.Document;
import org.example.mgd.AddressMgd;
import org.example.mgd.ClientAccountMgd;
import org.example.model.Client;
import org.example.model.Address;
import org.example.model.ClientType;

public class ClientMapper {
    private static final String ID = "_id";
    private static final String ADDRESS = "address";
    private static final String ARCHIVED = "archived";
    private static final String CLIENT_TYPE = "clientType";
    private static final String FIRST_NAME = "firstName";
    private static final String LAST_NAME = "lastName";

    public static ClientAccountMgd clientToMongo(Client client) {
        return new ClientAccountMgd(
                client.getId(),
                client.getFirstName(),
                client.getLastName(),
                toAddressMgd(client.getAddress()),
                client.getClientType(),
                client.isArchived());
    }

    public static Client clientFromMongo(ClientAccountMgd client) {
        return new Client(
                client.getEntityId(),
                client.getFirstName(),
                client.getLastName(),
                toAddress(client.getAddressMgd()),
                client.getClientType(),
                client.isArchived());
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
                clientDocument.getBoolean(ARCHIVED)
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
}
