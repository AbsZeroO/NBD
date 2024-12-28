package org.example.model.modelMapper;

import org.example.model.cassandra.AddressCas;
import org.example.model.cassandra.ClientAccountCas;
import org.example.model.domain.Address;
import org.example.model.domain.Client;

public class ClientAccountModelMapper {
    public ClientAccountCas toClientAccountCas(Client client) {
        return new ClientAccountCas(
                client.getId(),
                client.getFirstName(),
                client.getLastName(),
                new AddressCas(
                        client.getAddress().getCity(),
                        client.getAddress().getStreet(),
                        client.getAddress().getHouseNumber()
                ),
                client.getClientType(),
                client.isArchived(),
                client.getRents()
        );
    }

    public Client toClientDomain(ClientAccountCas clientAccountCas) {
        return new Client(
                clientAccountCas.getEntityId(),
                clientAccountCas.getFirstName(),
                clientAccountCas.getLastName(),
                new Address(
                        clientAccountCas.getAddressCas().getCity(),
                        clientAccountCas.getAddressCas().getStreet(),
                        clientAccountCas.getAddressCas().getHouseNumber()
                ),
                clientAccountCas.getClientType(),
                clientAccountCas.isArchived(),
                clientAccountCas.getRents()
        );
    }

}
