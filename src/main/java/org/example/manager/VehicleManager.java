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
    private static final VehicleRepository repository = new VehicleRepository();

    public void add(Vehicle vehicle) {
        repository.add(VehicleModelMapper.toVehicleCas(vehicle));
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

    public void update(Vehicle vehicle) {
        repository.update(VehicleModelMapper.toVehicleCas(vehicle));
    }

    public void delete(Vehicle vehicle) {
        repository.delete(VehicleModelMapper.toVehicleCas(vehicle));
    }


}
