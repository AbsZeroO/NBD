package org.example.repo;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.datastax.oss.driver.api.core.type.DataTypes;
import com.datastax.oss.driver.api.querybuilder.SchemaBuilder;
import org.example.dao.VehicleDao;
import org.example.daoMapper.VehicleDaoMapper;
import org.example.daoMapper.VehicleDaoMapperBuilder;
import org.example.identity.RentIDs;
import org.example.identity.VehicleIDs;
import org.example.model.cassandra.VehicleCas;

import java.util.List;


public class VehicleRepository extends AbstractCassandraRepository implements IRepo<VehicleCas> {
    private final VehicleDao vehicleDao;

    public CqlSession getSession() {
        return session;
    }

    public VehicleRepository() {
        createTable();
        VehicleDaoMapper rentDaoMapper = new VehicleDaoMapperBuilder(session).build();
        this.vehicleDao = rentDaoMapper.vehicleDao();
    }

    @Override
    public boolean add(VehicleCas entity) {
        try {
            vehicleDao.create(entity);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public VehicleCas findById(int id) {
        return vehicleDao.findById(id);
    }

    @Override
    public List<VehicleCas> findAll() {
        return vehicleDao.findAll();
    }

    @Override
    public boolean update(VehicleCas entity) {
        try {
            vehicleDao.update(entity);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(VehicleCas vehicleCas) {
        try {
            vehicleDao.delete(vehicleCas);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private void createTable() {
        SimpleStatement createTable =
                SchemaBuilder.createTable(RentIDs.TABLE_NAME_NAMESPACE, VehicleIDs.TABLE_NAME)
                        .ifNotExists()
                        .withPartitionKey(VehicleIDs.ID, DataTypes.INT)
                        .withColumn(VehicleIDs.PLATE_NUMBER, DataTypes.TEXT)
                        .withColumn(VehicleIDs.BASE_PRICE, DataTypes.DOUBLE)
                        .withColumn(VehicleIDs.ENGINE_DISPLACEMENT, DataTypes.INT)
                        .withColumn(VehicleIDs.RENTED, DataTypes.BOOLEAN)
                        .withColumn(VehicleIDs.ARCHIVED, DataTypes.BOOLEAN)
                        .withColumn(VehicleIDs.DISCRIMINATOR, DataTypes.TEXT)
                        .withColumn(VehicleIDs.SEGMENT_TYPE, DataTypes.TEXT)
                        .build();

        session.execute(createTable);

    }
}
