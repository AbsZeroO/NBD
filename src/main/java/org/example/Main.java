package org.example;

import org.example.model.cassandra.AddressCas;
import org.example.model.cassandra.ClientAccountCas;
import org.example.model.domain.ClientType;
import org.example.repo.ClientAccountRepository;

public class Main {
    public static void main(String[] args) {
        // Tworzymy instancję repozytorium
        ClientAccountRepository repository = new ClientAccountRepository();

        // Tworzymy adres
        AddressCas address = new AddressCas("New York", "5th Avenue", "100");

        // Tworzymy klienta
        ClientAccountCas newClient = new ClientAccountCas();
        newClient.setEntityId(1);  // Przykładowe ID
        newClient.setFirstName("John");
        newClient.setLastName("Doe");
        newClient.setAddressCas(address);  // Ustawiamy adres
        newClient.setClientType(ClientType.GOLD);
        newClient.setArchived(false);
        newClient.setRents(5);

        // Dodajemy klienta do repozytorium
        boolean added = repository.add(newClient);
        System.out.println("Client added: " + added);

        // Przykład pobrania klienta po ID
        ClientAccountCas foundClient = repository.findById(1);
        System.out.println("Client found: " + foundClient);

        // Przykład aktualizacji klienta
        if (foundClient != null) {
            foundClient.setRents(6);  // Zwiększamy liczbę wynajmów
            boolean updated = repository.update(foundClient);
            System.out.println("Client updated: " + updated);
        }

        // Przykład usunięcia klienta
        boolean deleted = repository.delete(newClient);
        System.out.println("Client deleted: " + deleted);

    }
}
