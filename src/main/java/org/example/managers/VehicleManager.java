package org.example.managers;

import org.example.exceptions.VehicleException;
import org.example.model.Vehicle;
import org.example.repositories.VehicleRepo;

import java.util.List;

public class VehicleManager {
    private final VehicleRepo vehicleRepo;

    public VehicleManager(VehicleRepo vehicleRepo) {
        this.vehicleRepo = vehicleRepo;
    }

    public void registerVehicle(Vehicle vehicle) {
        vehicleRepo.Add(vehicle);
    }

    public void unregisterVehicle(Vehicle vehicle) {
        vehicleRepo.Unredister(vehicle);
        /*vehicleRepo.Delete(vehicle);*/
    }

    public Vehicle getItem(Long id) throws VehicleException {
        Vehicle item = vehicleRepo.Find(id);
        if(item == null) {
            throw new VehicleException("No such client exist");
        } else {
            return item;
        }
    }

    public void edit(Vehicle item) {
        vehicleRepo.Update(item);
    }

    public List<Vehicle> getAllItems(){
        return vehicleRepo.getAll();
    }

}
