package org.example.dao;

import com.datastax.oss.driver.api.core.PagingIterable;
import com.datastax.oss.driver.api.mapper.annotations.*;
import org.example.model.cassandra.ClientAccountCas;

@Dao
public interface ClientAccountDao {
    @Insert
    @StatementAttributes(consistencyLevel = "LOCAL_QUORUM")
    void create(ClientAccountCas clientAccountCas);

    @Select
    @StatementAttributes(consistencyLevel = "LOCAL_QUORUM")
    ClientAccountCas findById(int id);

    @Select
    @StatementAttributes(consistencyLevel = "LOCAL_QUORUM")
    PagingIterable<ClientAccountCas> findAll();

    @Update
    @StatementAttributes(consistencyLevel = "LOCAL_QUORUM")
    void update(ClientAccountCas clientAccountCas);

    @Delete
    @StatementAttributes(consistencyLevel = "LOCAL_QUORUM")
    void delete(ClientAccountCas clientAccountCas);

}
