package org.example.model;


import org.example.mgd.AddressMgd;
import org.example.mgd.ClientAccountMgd;
import org.example.repositories.ClientMgdRepository;
import org.example.repositories.VehicleMgdRepository;

public class Main {
    public static void main(String[] args) {
        ClientMgdRepository clientMgdRepository = new ClientMgdRepository();
        VehicleMgdRepository vehicleMgdRepository = new VehicleMgdRepository();
        clientMgdRepository.getMongodb().getCollection("clients").drop();
        vehicleMgdRepository.getMongodb().getCollection("vehicles").drop();
        try {

            Address address = new Address("Łódź", "Radwańska", "40");
            Client client = new Client(0, "Maciek", "Walaszek",
                    address, ClientType.GOLD, false);

            Client client2 = new Client(1, "Walek", "Walaszek",
                    address, ClientType.GOLD, false);

            Car one = new Car(0,"LWD 0000", 125,25.0, false, false, SegmentType.B);
            Bicycle two = new Bicycle(1, "LWA aaaa", 500, 50.0, false, false);

            clientMgdRepository.add(client);
            clientMgdRepository.add(client2);

            vehicleMgdRepository.add(one);
            vehicleMgdRepository.add(two);


            System.out.println();
        } catch (Exception e) {
            e.printStackTrace();
        }
        clientMgdRepository.getMongodb().getCollection("clients").drop();
        clientMgdRepository.getMongodb().getCollection("vehicles").drop();
    }

}
