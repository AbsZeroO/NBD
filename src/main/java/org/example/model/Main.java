package org.example;

import org.example.model.Client;
import org.example.model.Rent;
import org.example.model.Vehicle;
import org.example.model.SegmentType;
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
        Client client = new Client("Jan Kowalski", "jan.kowalski@example.com");
        clientRepo.Add(client);
        System.out.println("Dodano klienta: " + client.getClientInfo());

        // Tworzenie pojazdu
        Vehicle vehicle = new Vehicle("ABC1234", 100.0, false);
        vehicleRepo.Add(vehicle);
        System.out.println("Dodano pojazd: " + vehicle.getVehicleInfo());

        // Tworzenie wypożyczenia
        Rent rent = new Rent(client, vehicle, LocalDateTime.now());
        rentRepo.Add(rent);
        System.out.println("Dodano wypożyczenie: " + rent.getRentInfo());

        // Zmiana stanu wypożyczenia po 2 dniach
        try {
            Thread.sleep(2000); // symulacja upływu czasu
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        rent.endRent(LocalDateTime.now().plusDays(2)); // zakończenie wypożyczenia
        rentRepo.Update(rent); // aktualizacja wypożyczenia
        System.out.println("Zakończono wypożyczenie: " + rent.getRentInfo());

        // Usuwanie pojazdu i klienta
        vehicleRepo.Delete(vehicle);
        clientRepo.Delete(client);
        System.out.println("Usunięto pojazd i klienta.");
    }
}
