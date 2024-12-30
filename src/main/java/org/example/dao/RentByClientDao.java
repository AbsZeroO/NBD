package org.example.dao;

import com.datastax.oss.driver.api.core.PagingIterable;
import com.datastax.oss.driver.api.mapper.annotations.*;
import org.example.model.cassandra.RentCasByClient;
import org.example.model.cassandra.RentCasByVehicle;
import org.example.provider.RentByClientProvider;

import java.util.List;

@Dao
public interface RentByClientDao {
    @Insert
    @StatementAttributes(consistencyLevel = "LOCAL_QUORUM")
    void create(RentCasByClient rentCasByClient);

    @Select
    @StatementAttributes(consistencyLevel = "LOCAL_QUORUM")
    PagingIterable<RentCasByClient> findByClient(int clientAccountCas);

    @QueryProvider(providerClass = RentByClientProvider.class)
    @StatementAttributes(consistencyLevel = "LOCAL_QUORUM")
    List<RentCasByClient> findCurrentRentsByClient(int clientCas);

    @QueryProvider(providerClass = RentByClientProvider.class)
    @StatementAttributes(consistencyLevel = "LOCAL_QUORUM")
    List<RentCasByClient> findArchivedRentsByClient(int clientCas);


    @Update
    @StatementAttributes(consistencyLevel = "LOCAL_QUORUM")
    void update(RentCasByClient rentCasByClient);

    @Delete
    @StatementAttributes(consistencyLevel = "LOCAL_QUORUM")
    void delete(RentCasByClient rentCasByClient);
}
