package org.example.dao;

import com.datastax.oss.driver.api.core.PagingIterable;
import com.datastax.oss.driver.api.mapper.annotations.*;
import org.example.model.cassandra.RentCas;

@Dao
public interface RentDao {
    @Insert
    @StatementAttributes(consistencyLevel = "LOCAL_QUORUM")
    void create(RentCas rentCas);

    @Select
    @StatementAttributes(consistencyLevel = "LOCAL_QUORUM")
    RentCas findById(int id);

    @Select
    @StatementAttributes(consistencyLevel = "LOCAL_QUORUM")
    PagingIterable<RentCas> findAll();

    @Update
    @StatementAttributes(consistencyLevel = "LOCAL_QUORUM")
    void update(RentCas rentCas);

    @Delete
    @StatementAttributes(consistencyLevel = "LOCAL_QUORUM")
    void delete(RentCas rentCas);
}
