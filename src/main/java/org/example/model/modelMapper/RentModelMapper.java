package org.example.model.modelMapper;

import org.example.model.cassandra.RentCas;
import org.example.model.cassandra.RentCasByClient;
import org.example.model.cassandra.RentCasByVehicle;
import org.example.model.domain.Rent;
import org.example.repo.ClientAccountRepository;
import org.example.repo.VehicleRepository;

public class RentModelMapper {
    public static RentCasByClient toRentCasByClient(RentCas rent) {
        return new RentCasByClient(
                rent.getEntityId(),
                rent.getClientAccountCas(),
                rent.getVehicleCas(),
                rent.getBeginTime(),
                rent.getEndTime(),
                rent.getRentCost(),
                rent.isArchived()
        );
    }

    public static RentCasByVehicle toRentCasByVehicle(RentCas rent) {
        return new RentCasByVehicle(
                rent.getEntityId(),
                rent.getClientAccountCas(),
                rent.getVehicleCas(),
                rent.getBeginTime(),
                rent.getEndTime(),
                rent.getRentCost(),
                rent.isArchived()
        );
    }

    public static RentCas fromRentCasByClientOrVehicle(RentCasByClient rentByClient) {
        return new RentCas(
                rentByClient.getEntityId(),
                rentByClient.getClientAccountCas(),
                rentByClient.getVehicleCas(),
                rentByClient.getBeginTime(),
                rentByClient.getEndTime(),
                rentByClient.getRentCost(),
                rentByClient.isArchived()
        );
    }

    public static RentCas fromRentCasByClientOrVehicle(RentCasByVehicle rentByVehicle) {
        return new RentCas(
                rentByVehicle.getEntityId(),
                rentByVehicle.getClientAccountCas(),
                rentByVehicle.getVehicleCas(),
                rentByVehicle.getBeginTime(),
                rentByVehicle.getEndTime(),
                rentByVehicle.getRentCost(),
                rentByVehicle.isArchived()
        );
    }

    public static RentCas toRentCas(Rent rent) {
        return new RentCas(
                rent.getId(),
                rent.getClient().getId(),
                rent.getVehicle().getId(),
                rent.getBeginTime(),
                rent.getEndTime(),
                rent.getRentCost(),
                rent.isArchived()
        );
    }

    public static Rent toDomain(RentCas rentCas) {
        ClientAccountRepository client = new ClientAccountRepository();
        VehicleRepository vehicle = new VehicleRepository();
        return new Rent(
                rentCas.getEntityId(),
                ClientAccountModelMapper.toClientDomain(client.findById(rentCas.getClientAccountCas())),
                VehicleModelMapper.toDomain(vehicle.findById(rentCas.getVehicleCas())),
                rentCas.getBeginTime(),
                rentCas.getEndTime(),
                rentCas.getRentCost(),
                rentCas.isArchived()
        );
    }


}
