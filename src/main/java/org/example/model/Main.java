package org.example.model;


import org.example.mgd.AddressMgd;
import org.example.mgd.ClientAccountMgd;
import org.example.repositories.ClientMgdRepository;

public class Main {
    public static void main(String[] args) {
        ClientMgdRepository clientMgdRepository = new ClientMgdRepository();
        ClientAccountMgd client = new ClientAccountMgd(1, "Maciek", "Walaszek",
                new AddressMgd("Łódź", "Radwańska", "40"), ClientType.GOLD, false);

        clientMgdRepository.Add(client);

        System.out.println();
    }
}
