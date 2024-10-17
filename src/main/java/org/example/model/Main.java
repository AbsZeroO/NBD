package org.example;

import org.example.model.*;
import org.example.repositories.ClientRepo;
import org.example.repositories.RentRepo;
import org.example.repositories.VehicleRepo;

import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        // Inicjalizacja repozytoriów
        ClientRepo clientRepo = new ClientRepo();
        VehicleRepo vehicleRepo = new VehicleRepo();
        RentRepo rentRepo = new RentRepo();

        // Tworzenie klienta
        Client client = new Client("Maciek", "Walczak", new Address("Poznan", "akcja", "5"), new Gold());
        clientRepo.Add(client);
        System.out.println("Dodano klienta: " + client.getClientInfo());

        // Tworzenie pojazdu
        Vehicle vehicle = new Vehicle("ABC1234", 195, 100.0, false);
        vehicleRepo.Add(vehicle);
        System.out.println("Dodano pojazd: " + vehicle.getVehicleInfo());

        Vehicle vehicle2 = new Car("ABC1234", 50, 100.0, false, SegmentType.E);
        vehicleRepo.Add(vehicle2);
        System.out.println("Dodano pojazd: " + vehicle.getVehicleInfo());

        // Tworzenie wypożyczenia
        Rent rent = new Rent(client, vehicle, LocalDateTime.now());
        rentRepo.Add(rent);
        System.out.println("Dodano wypożyczenie: " + rent.getRentInfo());

        // Zmiana stanu wypożyczenia po 2 dniach
        rent.endRent(LocalDateTime.now().plusDays(2)); // zakończenie wypożyczenia
        rentRepo.Update(rent); // aktualizacja wypożyczenia
        System.out.println("Zakończono wypożyczenie: " + rent.getRentInfo());

        // Usuwanie pojazdu i klienta
        rentRepo.Delete(rent);
        vehicleRepo.Delete(vehicle);
        clientRepo.Delete(client);
        System.out.println("Usunięto pojazd i klienta.");
    }
}
