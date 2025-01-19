package org.example.manager;

import org.example.model.domain.Rent;
import org.example.model.modelMapper.RentModelMapper;
import org.example.repo.ClientAccountRepository;
import org.example.repo.RentRepository;
import org.example.repo.VehicleRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class RentManager {
    private final RentRepository repository = new RentRepository();
    private final VehicleRepository vehicleRepo = new VehicleRepository();
    private final ClientAccountRepository clientRepo = new ClientAccountRepository();

    public void rejntVehicle(Rent rent) throws Exception {
        if (vehicleRepo.findById(rent.getVehicle().getId()).isRented()) {
            throw new Exception("Ten pojazd jest juz wypozyczony!");
        } else if (repository.findByClient(rent.getClient().getId()).size() > 5) {
            throw new Exception("Za dużo wypozyczonych");
        }

        repository.add(RentModelMapper.toRentCas(rent));
        vehicleRepo.setRent(rent.getVehicle().getId(), true);
    }

    public void returnVehicle(int id, LocalDateTime endTime) {
        Rent rent = findRentById(id);

        if (rent.getBeginTime().isBefore(endTime)) {
            rent.endRent(endTime);
            update(rent);
            vehicleRepo.setRent(rent.getVehicle().getId(), false);
        }


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

    public boolean update(Rent rent) {
        return repository.update(RentModelMapper.toRentCas(rent));
    }

    public boolean delete(Rent rent) {
        return repository.delete(RentModelMapper.toRentCas(rent));
    }

    public VehicleRepository getVehicleRepo() {
        return vehicleRepo;
    }

    public RentRepository getRepository() {
        return repository;
    }
}
