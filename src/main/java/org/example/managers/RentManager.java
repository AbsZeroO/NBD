package org.example.managers;

import org.example.exceptions.ClientException;
import org.example.exceptions.RentException;
import org.example.model.Client;
import org.example.model.Rent;
import org.example.model.Vehicle;

import java.time.LocalDateTime;
import java.util.List;

public class RentManager {
    /*
    private final RentRepo rentRepo;

    public RentManager(RentRepo rentRepo) {
        this.rentRepo = rentRepo;
    }

    public void rentVehicle(Client client, Vehicle vehicle) throws RentException, ClientException {
        if (client.getClientType().getMaxVehicle() <= rentRepo.getRentedVehiclesCount(client)) {
            throw new ClientException("To many cars are already rented.");
        }
        if (vehicle.isRented()) {
            throw new RentException("Is already rented.");
        } else {
            Rent rent = new Rent(client, vehicle, LocalDateTime.now());
            vehicle.setRented(true);
            rentRepo.Add(rent);
        }
    }

    public void returnVehicle(Long rentId, LocalDateTime date) throws RentException {
        Rent rent = getRentFromItemId(rentId);
        Vehicle vehicle = rent.getVehicle();
        vehicle.setRented(false);
        rent.endRent(date);
        rentRepo.Update(rent);
    }

    public Rent getRentFromItemId(long id) throws RentException {
        Rent rent = rentRepo.findRentById(id);
        if (rent == null) {
            throw new RentException("No such rent exist");
        } else {
            return rent;
        }
    }

    public List<Rent> getAllRents(){
        return rentRepo.getAll();
    }
    */

}
