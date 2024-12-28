package org.example.repo;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.PagingIterable;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.datastax.oss.driver.api.core.metadata.schema.ClusteringOrder;
import com.datastax.oss.driver.api.core.type.DataTypes;
import com.datastax.oss.driver.api.querybuilder.SchemaBuilder;
import org.example.dao.RentDao;
import org.example.daoMapper.RentDaoMapper;
import org.example.daoMapper.RentDaoMapperBuilder;
import org.example.identity.RentIDs;
import org.example.model.cassandra.RentCas;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class RentRepository extends AbstractCassandraRepository implements IRepo<RentCas> {

    private final RentDao rentDao;

    public CqlSession getSession() {
        return session;
    }

    public RentRepository() {
        createTable();
        RentDaoMapper rentDaoMapper = new RentDaoMapperBuilder(session).build();
        this.rentDao = rentDaoMapper.rentDao();
    }

    @Override
    public boolean add(RentCas entity) {
        try {
            rentDao.create(entity);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public RentCas findById(int id) {
        return rentDao.findById(id);
    }

    @Override
    public List<RentCas> findAll() {
        PagingIterable<RentCas> iterable = rentDao.findAll();
        return StreamSupport.stream(iterable.spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public boolean update(RentCas entity) {
        try {
            rentDao.update(entity);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(RentCas rentCas) {
        try {
            rentDao.delete(rentCas);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private void createTable() {
        SimpleStatement createTable =
                SchemaBuilder.createTable(RentIDs.TABLE_NAME_NAMESPACE, RentIDs.TABLE_NAME)
                        .ifNotExists()
                        .withPartitionKey(RentIDs.RENT_ID, DataTypes.INT)
                        .withColumn(RentIDs.CLIENT_ACCOUNT_CAS, DataTypes.INT)
                        .withColumn(RentIDs.VEHICLE_CAS, DataTypes.INT)
                        .withClusteringColumn(RentIDs.BEGIN_TIME, DataTypes.TIMESTAMP)
                        .withColumn(RentIDs.END_TIME, DataTypes.TIMESTAMP)
                        .withColumn(RentIDs.RENT_COST, DataTypes.DOUBLE)
                        .withColumn(RentIDs.ARCHIVED, DataTypes.BOOLEAN)
                        .withClusteringOrder(RentIDs.BEGIN_TIME, ClusteringOrder.DESC)
                        .build();

        session.execute(createTable);

    }
}
