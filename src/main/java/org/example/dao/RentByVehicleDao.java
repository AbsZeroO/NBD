package org.example.dao;

import com.datastax.oss.driver.api.core.PagingIterable;
import com.datastax.oss.driver.api.mapper.annotations.*;
import org.example.model.cassandra.*;
import org.example.provider.RentByVehicleProvider;
import org.example.provider.VehicleProvider;

import java.util.List;

@Dao
public interface RentByVehicleDao {
    @Insert
    @StatementAttributes(consistencyLevel = "LOCAL_QUORUM")
    void create(RentCasByVehicle rentCasByVehicle);

    @Select
    @StatementAttributes(consistencyLevel = "LOCAL_QUORUM")
    PagingIterable<RentCasByVehicle> findByVehicle(int vehicleCas);

    @QueryProvider(providerClass = RentByVehicleProvider.class)
    @StatementAttributes(consistencyLevel = "LOCAL_QUORUM")
    List<RentCasByVehicle> findCurrentRentsByVehicle(int vehicleCas);

    @QueryProvider(providerClass = RentByVehicleProvider.class)
    @StatementAttributes(consistencyLevel = "LOCAL_QUORUM")
    List<RentCasByVehicle> findArchivedRentsByVehicle(int vehicleCas);

    @Update
    @StatementAttributes(consistencyLevel = "LOCAL_QUORUM")
    void update(RentCasByVehicle rentCasByVehicle);

    @Delete
    @StatementAttributes(consistencyLevel = "LOCAL_QUORUM")
    void delete(RentCasByVehicle rentCasByVehicle);
}
