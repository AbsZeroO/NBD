package org.example.managers;

import com.mongodb.MongoWriteException;
import org.example.exceptions.ClientException;
import org.example.exceptions.RentException;
import org.example.mappers.ClientMapper;
import org.example.mappers.RentMapper;
import org.example.mappers.VehicleMapper;
import org.example.mgd.RentMgd;
import org.example.model.Client;
import org.example.model.Rent;
import org.example.model.Vehicle;
import org.example.repositories.ClientMgdRepository;
import org.example.repositories.RentMgdRepository;
import org.example.repositories.VehicleMgdRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class RentManager {
    private final RentMgdRepository rentRepo;
    private final VehicleMgdRepository vehRepo = new VehicleMgdRepository();
    private final ClientMgdRepository clientRepo = new ClientMgdRepository();
    private static AtomicInteger idGenerator = new AtomicInteger(1);

    public RentManager(RentMgdRepository rentRepo) {
        this.rentRepo = rentRepo;
    }

    public void rentVehicle(Client client, Vehicle vehicle) {
        int id = idGenerator.getAndIncrement();
        Rent rent = new Rent(id, client, vehicle, LocalDateTime.now());

        try {
            int a = rentRepo.countClients(ClientMapper.clientToMongo(client));
            if (a < client.getClientType().getMaxVehicle()) {
                vehRepo.updateRent(VehicleMapper.vehicleToMongo(vehicle));
                vehicle.setRented(1);
                rentRepo.add(RentMapper.rentToMongo(rent));
            }
            else {
                throw new RentException("Client rented max vehicles.");
            }

        } catch (MongoWriteException e) {
            e.printStackTrace();
            throw e;
        } catch (RentException e) {
            e.printStackTrace();
            throw new RuntimeException("Ta");
        }
    }

    public Vehicle returnVehicle(int rentId, LocalDateTime date) throws RentException {
        Rent rent = getRentFromItemId(rentId);
        Vehicle vehicle = rent.getVehicle();
        vehicle.setRented(0);
        vehRepo.update(VehicleMapper.vehicleToMongo(vehicle));
        rent.endRent(date);
        rentRepo.update(RentMapper.rentToMongo(rent));
        return vehicle;
    }

    public Rent getRentFromItemId(int id) throws RentException {
        return RentMapper.rentFromMongo(rentRepo.findById(id));
    }

    public List<Rent> getAllRents() {
        List<RentMgd> rentMgdList = rentRepo.findAll();

        List<Rent> rents = new ArrayList<>();
        for (RentMgd rentMgd : rentMgdList) {
            System.out.println("RentMgd: " + rentMgd); // Debugging: sprawdź obiekt RentMgd
            Rent rent = RentMapper.rentFromMongo(rentMgd);
            System.out.println("Mapped Rent: " + rent); // Debugging: sprawdź obiekt Rent po mapowaniu
            rents.add(rent);
        }

        return rents;
    }

}
