package org.example.model;


import org.example.mgd.*;
import org.example.repositories.ClientMgdRepository;
import org.example.repositories.Rent.RentFailOverRepository;
import org.example.repositories.Rent.RentJsonbRepository;
import org.example.repositories.Rent.RentMgdRepository;
import org.example.repositories.Rent.RentProducent;
import org.example.repositories.VehicleMgdRepository;

import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        RentMgdRepository repositoryMongo = new RentMgdRepository();
        RentJsonbRepository repositoryRedis = new RentJsonbRepository();

        Address address = new Address("Łódź", "Radwańska", "40");
        Client client = new Client(0, "Maciek", "Walaszek",
                address, ClientType.GOLD, false, 0);
        Client client2 = new Client(1, "Walek", "Walaszek",
                address, ClientType.GOLD, false, 0);
        Vehicle vehicle1 = new Car(0,"LWD 0000", 25.0,125, 0, false, SegmentType.B);
        Vehicle vehicle2 = new Bicycle(1, "LWA aaaa", 50.0, 100, 0, false);

        Rent rent = new Rent(1111, client, vehicle1, LocalDateTime.now());
        Rent rent2 = new Rent(2222, client, vehicle2, LocalDateTime.now().minusDays(10), LocalDateTime.now(), 50, true);

        RentFailOverRepository repository = new RentFailOverRepository(repositoryRedis, repositoryMongo);

        repository.add(rent);

    }

}

/*
kafka-topics.sh --bootstrap-server kafka1:9192 --list

kafka-topics.sh --bootstrap-server kafka1:9192 --describe --topic wypozyczenia-rezerwacje

kafka-consumer-groups.sh --bootstrap-server kafka1:9192 --list

kafka-consumer-groups.sh --bootstrap-server kafka1:9192 --describe --group grupa

kafka-console-consumer.sh --bootstrap-server kafka1:9192 --topic wypozyczenia-rezerwacje --from-beginning

mongosh --username admin --password adminpassword --authenticationDatabase admin

mongosh mongodb://mongodb4:27020 --username root --password rootpassword --authenticationDatabase admin

*/