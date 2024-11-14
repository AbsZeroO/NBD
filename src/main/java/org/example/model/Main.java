package org.example.model;


import org.example.mgd.*;
import org.example.repositories.ClientMgdRepository;
import org.example.repositories.VehicleMgdRepository;

public class Main {
    public static void main(String[] args) {
        ClientMgdRepository clientMgdRepository = new ClientMgdRepository();
        VehicleMgdRepository vehicleMgdRepository = new VehicleMgdRepository();
        clientMgdRepository.getMongodb().getCollection("clients").drop();
        vehicleMgdRepository.getMongodb().getCollection("vehicles").drop();
        try {

            AddressMgd address = new AddressMgd("Łódź", "Radwańska", "40");
            ClientAccountMgd client = new ClientAccountMgd(0, "Maciek", "Walaszek",
                    address, ClientType.GOLD, false);

            ClientAccountMgd client2 = new ClientAccountMgd(1, "Walek", "Walaszek",
                    address, ClientType.GOLD, false);

            VehicleMgd vehicle1 = new CarMgd(0,"LWD 0000", 25.0,125, false, false, SegmentType.B);
            VehicleMgd vehicle2 = new BicycleMgd(1, "LWA aaaa", 50.0, 100, false, false);

            clientMgdRepository.add(client);
            clientMgdRepository.add(client2);

            vehicleMgdRepository.add(vehicle1);
            vehicleMgdRepository.add(vehicle2);

            String newPlateNumber = "SSS";

            vehicle1.setPlateNumber(newPlateNumber);


            vehicleMgdRepository.update(vehicle1);

            vehicleMgdRepository.findAll();

            System.out.println();
        } catch (Exception e) {
            e.printStackTrace();
        }
        clientMgdRepository.getMongodb().getCollection("clients").drop();
        clientMgdRepository.getMongodb().getCollection("vehicles").drop();
    }

}
