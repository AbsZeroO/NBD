package org.example.model;

import org.example.exceptions.ClientException;
import org.example.exceptions.RentException;
import org.example.exceptions.VehicleException;
import org.example.managers.ClientManager;
import org.example.managers.RentManager;
import org.example.managers.VehicleManager;
import org.example.model.*;
import org.example.repositories.ClientRepo;
import org.example.repositories.RentRepo;
import org.example.repositories.VehicleRepo;

import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        // Inicjalizacja menedżerów
        ClientManager clientManager = new ClientManager(new ClientRepo());
        VehicleManager vehicleManager = new VehicleManager(new VehicleRepo());
        RentManager rentManager = new RentManager(new RentRepo());

        try {
            // Tworzenie klienta
            Client client = new Client("Maciek", "Walczak", new Address("Poznan", "akcja", "5"), new Bronze());
            clientManager.registerClient(client);

            Client client2 = new Client("Maciek", "Walczak", new Address("Poznan", "akcja", "5"), new Silver());
            clientManager.registerClient(client2);

            // Tworzenie pojazdu
            Vehicle vehicle = new Vehicle("ABC1234", 195, 100.0, false);
            vehicleManager.registerVehicle(vehicle);

            Vehicle car = new Car("EL 0000", 50, 100.0, false, SegmentType.E);
            vehicleManager.registerVehicle(car);

            Vehicle bicycle = new Bicycle("EL 1111", 1000, 200.0, false);
            vehicleManager.registerVehicle(bicycle);

            // Wypożyczenie pojazdu
            rentManager.rentVehicle(client, vehicle);
            rentManager.rentVehicle(client, car);
            rentManager.rentVehicle(client, bicycle);

            // Zmiana stanu wypożyczenia po 2 dniach

            Rent rent = rentManager.getAllRents().get(0); // Zakładamy, że to pierwsze wypożyczenie
            rentManager.returnVehicle(rent.getId(), LocalDateTime.now().plusDays(2));

            // Usuwanie klienta i pojazdu
            clientManager.unregisterClient(client);
            vehicleManager.unregisterVehicle(vehicle);

        } catch (ClientException | RentException e) {
            System.err.println(e.getMessage());
        }
    }
}
