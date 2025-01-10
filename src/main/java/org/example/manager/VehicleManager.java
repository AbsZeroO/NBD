package org.example.manager;

import org.example.model.domain.Client;
import org.example.model.domain.Vehicle;
import org.example.model.modelMapper.ClientAccountModelMapper;
import org.example.model.modelMapper.VehicleModelMapper;
import org.example.repo.ClientAccountRepository;
import org.example.repo.VehicleRepository;

import java.util.List;
import java.util.stream.Collectors;

public class VehicleManager {
    private final VehicleRepository repository = new VehicleRepository();

    public boolean add(Vehicle vehicle) {
        return repository.add(VehicleModelMapper.toVehicleCas(vehicle));
    }

    public Vehicle findById(int id) {
        return VehicleModelMapper.toDomain(repository.findById(id));
    }

    public List<Vehicle> findAll() {
        return repository.findAll()
                .stream()
                .map(VehicleModelMapper::toDomain)
                .collect(Collectors.toList());
    }

    public boolean update(Vehicle vehicle) {
        return repository.update(VehicleModelMapper.toVehicleCas(vehicle));
    }

    public boolean delete(Vehicle vehicle) {
        return repository.delete(VehicleModelMapper.toVehicleCas(vehicle));
    }

    public VehicleRepository getRepository() {
        return repository;
    }
}
