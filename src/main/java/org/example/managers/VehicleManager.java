package org.example.managers;

import org.example.exceptions.VehicleException;
import org.example.mappers.ClientMapper;
import org.example.mappers.VehicleMapper;
import org.example.model.Vehicle;
import org.example.repositories.VehicleMgdRepository;


import java.util.List;
import java.util.stream.Collectors;

public class VehicleManager {

    private final VehicleMgdRepository vehicleRepo;

    public VehicleManager(VehicleMgdRepository vehicleRepo) {
        this.vehicleRepo = vehicleRepo;
    }

    public void registerVehicle(Vehicle vehicle) {
        vehicleRepo.add(VehicleMapper.vehicleToMongo(vehicle));
    }

    public void deletVehicle(Vehicle vehicle) {
        vehicleRepo.delete(vehicle.getId());
    }
    public Vehicle getVehicle(int id) throws VehicleException {
        Vehicle vehicle = VehicleMapper.vehicleFromMongo(vehicleRepo.findById(id));
        if(vehicle == null) {
            throw new VehicleException("No such client exist");
        } else {
            return vehicle;
        }
    }

    public void edit(Vehicle vehicle) {
        vehicleRepo.update(VehicleMapper.vehicleToMongo(vehicle));
    }

    public List<Vehicle> getAllItems(){
        return vehicleRepo.findAll()
                .stream()
                .map(VehicleMapper::vehicleFromMongo)
                .collect(Collectors.toList());
    }

}
