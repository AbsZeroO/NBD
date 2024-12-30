package org.example.manager;

import org.example.model.domain.Rent;
import org.example.model.modelMapper.RentModelMapper;
import org.example.repo.RentRepository;

import java.util.List;
import java.util.stream.Collectors;

public class RentManager {
    private static final RentRepository repository = new RentRepository();

    public void add(Rent rent) {
        repository.add(RentModelMapper.toRentCas(rent));
    }

    public Rent findRentById(int rentId) {
        return RentModelMapper.toDomain(repository.findById(rentId));
    }

    public List<Rent> findByClientId(int clientId) {
        return repository.findByClient(clientId)
                .stream()
                .map(RentModelMapper::fromRentCasByClientOrVehicle)
                .map(RentModelMapper::toDomain)
                .collect(Collectors.toList());
    }

    public List<Rent> findCurrentRentsByClientId(int clientId) {
        return repository.findCurrentRentsByClient(clientId)
                .stream()
                .map(RentModelMapper::fromRentCasByClientOrVehicle)
                .map(RentModelMapper::toDomain)
                .collect(Collectors.toList());
    }

    public List<Rent> findArchivedRentsByClientId(int clientId) {
        return repository.findArchivedRentsByClient(clientId)
                .stream()
                .map(RentModelMapper::fromRentCasByClientOrVehicle)
                .map(RentModelMapper::toDomain)
                .collect(Collectors.toList());
    }

    public List<Rent> findByVehicleId(int vehicleId) {
        return repository.findByVehicle(vehicleId)
                .stream()
                .map(RentModelMapper::fromRentCasByClientOrVehicle)
                .map(RentModelMapper::toDomain)
                .collect(Collectors.toList());
    }

    public List<Rent> findCurrentRentsByVehicleId(int vehicleId) {
        return repository.findCurrentRentsByVehicle(vehicleId)
                .stream()
                .map(RentModelMapper::fromRentCasByClientOrVehicle)
                .map(RentModelMapper::toDomain)
                .collect(Collectors.toList());
    }

    public List<Rent> findArchivedRentsByVehicleId(int vehicleId) {
        return repository.findArchivedRentsByVehicle(vehicleId)
                .stream()
                .map(RentModelMapper::fromRentCasByClientOrVehicle)
                .map(RentModelMapper::toDomain)
                .collect(Collectors.toList());
    }

    public List<Rent> findAllRents() {
        return repository.findAll()
                .stream()
                .map(RentModelMapper::toDomain)
                .collect(Collectors.toList());
    }

    public void update(Rent rent) {
        repository.update(RentModelMapper.toRentCas(rent));
    }

    public void delete(Rent rent) {
        repository.delete(RentModelMapper.toRentCas(rent));
    }
}
