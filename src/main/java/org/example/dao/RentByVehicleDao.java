package org.example.dao;

import com.datastax.oss.driver.api.core.PagingIterable;
import com.datastax.oss.driver.api.mapper.annotations.*;
import org.example.model.cassandra.RentCas;
import org.example.model.cassandra.RentCasByClient;
import org.example.model.cassandra.RentCasByVehicle;

@Dao
public interface RentByVehicleDao {
    @Insert
    @StatementAttributes(consistencyLevel = "LOCAL_QUORUM")
    void create(RentCasByVehicle rentCasByVehicle);

    @Select
    @StatementAttributes(consistencyLevel = "LOCAL_QUORUM")
    PagingIterable<RentCasByVehicle> findByVehicle(int vehicleCas);

    @Update
    @StatementAttributes(consistencyLevel = "LOCAL_QUORUM")
    void update(RentCasByVehicle rentCasByVehicle);

    @Delete
    @StatementAttributes(consistencyLevel = "LOCAL_QUORUM")
    void delete(RentCasByVehicle rentCasByVehicle);
}
