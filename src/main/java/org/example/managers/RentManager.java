package org.example.managers;

import com.mongodb.MongoWriteException;
import org.example.exceptions.RentException;
import org.example.mappers.ClientMapper;
import org.example.mappers.RentMapper;
import org.example.mappers.VehicleMapper;
import org.example.mgd.RentMgd;
import org.example.model.Client;
import org.example.model.Rent;
import org.example.model.Vehicle;
import org.example.repositories.ClientMgdRepository;
import org.example.repositories.Rent.RentFailOverRepository;
import org.example.repositories.Rent.RentJsonbRepository;
import org.example.repositories.Rent.RentMgdRepository;
import org.example.repositories.VehicleMgdRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class RentManager {
    private final RentFailOverRepository rentRepo;
    private final VehicleMgdRepository vehRepo = new VehicleMgdRepository();
    private final ClientMgdRepository clientRepo = new ClientMgdRepository();
    private static AtomicInteger idGenerator = new AtomicInteger(1);

    public RentManager() {
        this.rentRepo = new RentFailOverRepository(new RentJsonbRepository(), new RentMgdRepository());
    }

    public void rentVehicle(Client client, Vehicle vehicle) {
        int id = idGenerator.getAndIncrement();
        Rent rent = new Rent(id, client, vehicle, LocalDateTime.now());

        try {
            vehRepo.updateRent(VehicleMapper.vehicleToMongo(vehicle));
            clientRepo.incRent(ClientMapper.clientToMongo(client));
            client.setRents(client.getRents() + 1);
            vehicle.setRented(1);
            rentRepo.add(rent);

        } catch (MongoWriteException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public Vehicle returnVehicle(int rentId, LocalDateTime date) throws RentException, MongoWriteException {
        try {
            Rent rent = getRentFromItemId(rentId);
            rent.getVehicle().setRented(0);
            vehRepo.update(VehicleMapper.vehicleToMongo(rent.getVehicle()));
            clientRepo.decRent(ClientMapper.clientToMongo(rent.getClient()));
            rent.getClient().setRents(rent.getClient().getRents() - 1);
            rent.endRent(date);
            rentRepo.update(rent);
            return rent.getVehicle();

        } catch (MongoWriteException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public Rent getRentFromItemId(int id) throws RentException {
        return rentRepo.findById(id);
    }

    public List<Rent> getAllRents() {
        return rentRepo.findAll();
    }

    public void clearCashe() {
        rentRepo.clearCashe();
    }
}
