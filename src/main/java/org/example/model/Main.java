package org.example.model;

import org.example.model.*;
import org.example.repositories.ClientRepo;
import org.example.repositories.RentRepo;
import org.example.repositories.VehicleRepo;

import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        ClientRepo clientRepo = new ClientRepo();
        VehicleRepo vehicleRepo = new VehicleRepo();
        RentRepo rentRepo = new RentRepo();

        // Tworzenie klienta
        Client client = new Client("Maciek", "Walczak", new Address("Poznan", "akcja", "5"), new Gold());
        clientRepo.Add(client);

        Client client2 = new Client("Macieke", "Walczak", new Address("Poznan", "akcja", "5"), new Silver());
        clientRepo.Add(client2);

        Client client3 = new Client("Macieasdke", "Walczak", new Address("Poznan", "akcja", "5"), new Silver());
        clientRepo.Add(client3);

        // Tworzenie pojazdu
        Vehicle vehicle = new Vehicle("ABC1234", 195, 100.0, false);
        vehicleRepo.Add(vehicle);

        Vehicle car = new Car("EL 0000", 50, 100.0, false, SegmentType.E);
        vehicleRepo.Add(car);

        Vehicle bicycle = new Bicycle("EL 1111", 1000, 200.0, false);
        vehicleRepo.Add(bicycle);

        // Tworzenie wypożyczenia
        Rent rent = new Rent(client, vehicle, LocalDateTime.now());
        rentRepo.Add(rent);

        // Zmiana stanu wypożyczenia po 2 dniach
        rent.endRent(LocalDateTime.now().plusDays(2));
        rentRepo.Update(rent);

        // Usuwanie pojazdu, klienta i wypozyczenia chociaż nie powinno sie usuwać tylko zmieniać isArchvived
        rentRepo.Delete(rent);
        vehicleRepo.Delete(vehicle);
        clientRepo.Delete(client);
    }
}
