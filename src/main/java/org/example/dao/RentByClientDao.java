package org.example.dao;

import com.datastax.oss.driver.api.core.PagingIterable;
import com.datastax.oss.driver.api.mapper.annotations.*;
import org.example.model.cassandra.RentCasByClient;

@Dao
public interface RentByClientDao {
    @Insert
    @StatementAttributes(consistencyLevel = "LOCAL_QUORUM")
    void create(RentCasByClient rentCasByClient);

    @Select
    @StatementAttributes(consistencyLevel = "LOCAL_QUORUM")
    PagingIterable<RentCasByClient> findByClient(int clientAccountCas);

    @Update
    @StatementAttributes(consistencyLevel = "LOCAL_QUORUM")
    void update(RentCasByClient rentCasByClient);

    @Delete
    @StatementAttributes(consistencyLevel = "LOCAL_QUORUM")
    void delete(RentCasByClient rentCasByClient);
}
