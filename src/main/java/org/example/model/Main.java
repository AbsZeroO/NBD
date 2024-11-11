package org.example.model;


import org.example.mgd.AddressMgd;
import org.example.mgd.ClientAccountMgd;
import org.example.repositories.ClientMgdRepository;

public class Main {
    public static void main(String[] args) {
        ClientMgdRepository clientMgdRepository = new ClientMgdRepository();
        try {

            AddressMgd addressMgd = new AddressMgd("Łódź", "Radwańska", "40");
            ClientAccountMgd client = new ClientAccountMgd(0, "Maciek", "Walaszek",
                    addressMgd, ClientType.GOLD, false);

            ClientAccountMgd client2 = new ClientAccountMgd(1, "Walek", "Walaszek",
                    addressMgd, ClientType.GOLD, false);

            clientMgdRepository.Add(client);
            clientMgdRepository.Add(client2);

            System.out.println(clientMgdRepository.getAll());

            System.out.println();
        } catch (Exception e) {
            e.printStackTrace();
        }
        clientMgdRepository.getMongodb().getCollection("clients").drop();
    }
}
