package org.example.repo;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.PagingIterable;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.datastax.oss.driver.api.core.type.DataTypes;
import com.datastax.oss.driver.api.querybuilder.SchemaBuilder;
import com.datastax.oss.driver.shaded.guava.common.collect.Streams;
import org.example.dao.RentByClientDao;
import org.example.dao.RentByVehicleDao;
import org.example.dao.RentDao;
import org.example.daoMapper.*;
import org.example.identity.RentIDs;
import org.example.model.cassandra.RentCas;
import org.example.model.cassandra.RentCasByClient;
import org.example.model.cassandra.RentCasByVehicle;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class RentRepository extends AbstractCassandraRepository implements IRepo<RentCas> {

    private final RentDao rentDao;

    private final RentByClientDao rentByClientDao;
    private final RentByVehicleDao rentByVehicleDao;
    public CqlSession getSession() {
        return session;
    }

    public RentRepository() {
        createTable();
        createRentByClientTable();
        createRentByVehicleTable();
        RentDaoMapper rentDaoMapper = new RentDaoMapperBuilder(session).build();
        RentByVehicleDaoMapper rentByVehicleDaoMapper = new RentByVehicleDaoMapperBuilder(session).build();
        RentByClientDaoMapper rentByClientDaoMapper = new RentByClientDaoMapperBuilder(session).build();
        this.rentDao = rentDaoMapper.rentDao();
        this.rentByClientDao = rentByClientDaoMapper.rentByClientDao();
        this.rentByVehicleDao = rentByVehicleDaoMapper.rentByVehicleDao();
    }

    @Override
    public boolean add(RentCas entity) {
        try {

            RentCasByClient rentByClient = new RentCasByClient(
                    entity.getEntityId(),
                    entity.getClientAccountCas(),
                    entity.getVehicleCas(),
                    entity.getBeginTime()

            );

            RentCasByVehicle rentByVehicle = new RentCasByVehicle(
                    entity.getEntityId(),
                    entity.getClientAccountCas(),
                    entity.getVehicleCas(),
                    entity.getBeginTime()
            );

            rentDao.create(entity);
            rentByClientDao.create(rentByClient);
            rentByVehicleDao.create(rentByVehicle);

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

            RentCasByClient rentByClient = new RentCasByClient(
                    entity.getEntityId(),
                    entity.getClientAccountCas(),
                    entity.getVehicleCas(),
                    entity.getBeginTime()

            );

            RentCasByVehicle rentByVehicle = new RentCasByVehicle(
                    entity.getEntityId(),
                    entity.getClientAccountCas(),
                    entity.getVehicleCas(),
                    entity.getBeginTime()
            );

            rentByClientDao.update(rentByClient);
            rentByVehicleDao.update(rentByVehicle);

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

            RentCasByClient rentByClient = new RentCasByClient(
                    rentCas.getEntityId(),
                    rentCas.getClientAccountCas(),
                    rentCas.getVehicleCas(),
                    rentCas.getBeginTime()

            );

            RentCasByVehicle rentByVehicle = new RentCasByVehicle(
                    rentCas.getEntityId(),
                    rentCas.getClientAccountCas(),
                    rentCas.getVehicleCas(),
                    rentCas.getBeginTime()
            );


            rentByClientDao.delete(rentByClient);
            rentByVehicleDao.delete(rentByVehicle);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Wyszukiwanie wynajmów po kliencie
    public List<RentCasByClient> findByClient(int clientAccountCas) {
        PagingIterable<RentCasByClient> iterable = rentByClientDao.findByClient(clientAccountCas);
        return StreamSupport.stream(iterable.spliterator(), false).collect(Collectors.toList());
    }

    // Wyszukiwanie wynajmów po pojeździe
    public List<RentCasByVehicle> findByVehicle(int vehicleCas) {
        PagingIterable<RentCasByVehicle> iterable = rentByVehicleDao.findByVehicle(vehicleCas);
        return StreamSupport.stream(iterable.spliterator(), false).collect(Collectors.toList());
    }

    private void createTable() {
        SimpleStatement createTable =
                SchemaBuilder.createTable(RentIDs.TABLE_NAME_NAMESPACE, RentIDs.TABLE_NAME)
                        .ifNotExists()
                        .withPartitionKey(RentIDs.RENT_ID, DataTypes.INT)
                        .withColumn(RentIDs.CLIENT_ACCOUNT_CAS, DataTypes.INT)
                        .withColumn(RentIDs.VEHICLE_CAS, DataTypes.INT)
                        .withColumn(RentIDs.BEGIN_TIME, DataTypes.TIMESTAMP)
                        .withColumn(RentIDs.END_TIME, DataTypes.TIMESTAMP)
                        .withColumn(RentIDs.RENT_COST, DataTypes.DOUBLE)
                        .withColumn(RentIDs.ARCHIVED, DataTypes.BOOLEAN)
                        .build();

        session.execute(createTable);

    }

    private void createRentByClientTable() {
        SimpleStatement createRentByClientTable =
                SchemaBuilder.createTable("rent_a_vehicle", "rent_by_client")
                        .ifNotExists()
                        .withPartitionKey(RentIDs.CLIENT_ACCOUNT_CAS, DataTypes.INT)
                        .withClusteringColumn(RentIDs.RENT_ID, DataTypes.INT)
                        .withColumn(RentIDs.BEGIN_TIME, DataTypes.TIMESTAMP)
                        .withColumn(RentIDs.VEHICLE_CAS, DataTypes.INT)
                        .withColumn(RentIDs.END_TIME, DataTypes.TIMESTAMP)
                        .withColumn(RentIDs.RENT_COST, DataTypes.DOUBLE)
                        .withColumn(RentIDs.ARCHIVED, DataTypes.BOOLEAN)
                        .build();

        session.execute(createRentByClientTable);
    }


    private void createRentByVehicleTable() {
        SimpleStatement createRentByVehicleTable =
                SchemaBuilder.createTable("rent_a_vehicle", "rent_by_vehicle")
                        .ifNotExists()
                        .withPartitionKey(RentIDs.VEHICLE_CAS, DataTypes.INT)
                        .withClusteringColumn(RentIDs.RENT_ID, DataTypes.INT)
                        .withColumn(RentIDs.BEGIN_TIME, DataTypes.TIMESTAMP)
                        .withColumn(RentIDs.CLIENT_ACCOUNT_CAS, DataTypes.INT)
                        .withColumn(RentIDs.END_TIME, DataTypes.TIMESTAMP)
                        .withColumn(RentIDs.RENT_COST, DataTypes.DOUBLE)
                        .withColumn(RentIDs.ARCHIVED, DataTypes.BOOLEAN)
                        .build();

        session.execute(createRentByVehicleTable);
    }
}
