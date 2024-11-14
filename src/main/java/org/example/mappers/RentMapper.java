package org.example.mappers;

import org.bson.Document;
import org.example.mgd.ClientAccountMgd;
import org.example.mgd.RentMgd;
import org.example.mgd.VehicleMgd;
import org.example.model.Rent;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class RentMapper {
    private static final String ID = "_id";
    private static final String CLIENT = "client";
    private static final String VEHICLE = "vehicle";
    private static final String BEGIN_TIME = "beginTime";
    private static final String END_TIME = "endTime";
    private static final String RENT_COST = "rentCost";
    private static final String ARCHIVED = "archived";

    public static RentMgd rentToMongo(Rent rent) {
        return new RentMgd(
                rent.getId(),
                ClientMapper.clientToMongo(rent.getClient()),
                VehicleMapper.vehicleToMongo(rent.getVehicle()),
                rent.getBeginTime(),
                rent.getEndTime(),
                rent.getRentCost(),
                rent.isArchived()
        );
    }

    public static Rent rentFromMongo(RentMgd rentMgd) {
        return new Rent(
                rentMgd.getEntityId(),
                ClientMapper.clientFromMongo(rentMgd.getClientAccountMgd()),
                VehicleMapper.vehicleFromMongo(rentMgd.getVehicleMgd()),
                rentMgd.getBeginTime()
        );
    }

    public static RentMgd toRentMgd(Document document) {
        ClientAccountMgd clientAccountMgd = ClientMapper.toClientMgd(document.get(CLIENT, Document.class));
        VehicleMgd vehicleMgd = VehicleMapper.toVehicleMgd(document.get(VEHICLE, Document.class));

        LocalDateTime beginTime = document.getDate(BEGIN_TIME).toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();

        LocalDateTime endTime = document.getDate(END_TIME) != null
                ? document.getDate(END_TIME).toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime()
                : null;

        double rentCost = document.getDouble(RENT_COST);
        boolean archived = document.getBoolean(ARCHIVED);

        return new RentMgd(
                document.getInteger(ID),
                clientAccountMgd,
                vehicleMgd,
                beginTime,
                endTime,
                rentCost,
                archived
        );
    }



}
