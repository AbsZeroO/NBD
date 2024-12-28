package org.example.dao;

import com.datastax.oss.driver.api.mapper.annotations.*;
import org.example.model.cassandra.BicycleCas;
import org.example.model.cassandra.CarCas;
import org.example.model.cassandra.VehicleCas;
import org.example.provider.VehicleProvider;

import java.util.List;

@Dao
public interface VehicleDao {
    @QueryProvider(providerClass = VehicleProvider.class, entityHelpers = {CarCas.class, BicycleCas.class})
    @StatementAttributes(consistencyLevel = "LOCAL_QUORUM")
    void create(VehicleCas vehicleCas);

    @QueryProvider(providerClass = VehicleProvider.class, entityHelpers = {CarCas.class, BicycleCas.class})
    @StatementAttributes(consistencyLevel = "LOCAL_QUORUM")
    VehicleCas findById(int id);

    @QueryProvider(providerClass = VehicleProvider.class, entityHelpers = {CarCas.class, BicycleCas.class})
    @StatementAttributes(consistencyLevel = "LOCAL_QUORUM")
    List<VehicleCas> findAll();

    @QueryProvider(providerClass = VehicleProvider.class, entityHelpers = {CarCas.class, BicycleCas.class})
    @StatementAttributes(consistencyLevel = "LOCAL_QUORUM")
    void update(VehicleCas vehicleCas);

    @Delete
    @StatementAttributes(consistencyLevel = "LOCAL_QUORUM")
    void delete(VehicleCas vehicleCas);
}
