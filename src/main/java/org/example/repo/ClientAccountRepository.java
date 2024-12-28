package org.example.repo;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.PagingIterable;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.datastax.oss.driver.api.core.type.DataTypes;
import com.datastax.oss.driver.api.core.type.UserDefinedType;
import com.datastax.oss.driver.api.querybuilder.SchemaBuilder;
import com.datastax.oss.driver.internal.core.type.UserDefinedTypeBuilder;
import org.example.dao.ClientAccountDao;
import org.example.daoMapper.ClientAccountDaoMapper;
import org.example.daoMapper.ClientAccountDaoMapperBuilder;
import org.example.identity.AddressIDs;
import org.example.identity.ClientAccountIDs;
import org.example.identity.RentIDs;
import org.example.model.cassandra.ClientAccountCas;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class ClientAccountRepository extends AbstractCassandraRepository implements IRepo<ClientAccountCas> {

    ClientAccountDao clientAccountDao;

    public CqlSession getSession() {
        return session;
    }

    public ClientAccountRepository() {
        createTable();
        ClientAccountDaoMapper clientAccountDaoMapper = new ClientAccountDaoMapperBuilder(session).build();
        this.clientAccountDao = clientAccountDaoMapper.clientAccountDao();
    }

    @Override
    public boolean add(ClientAccountCas entity) {
        try {
            clientAccountDao.create(entity);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public ClientAccountCas findById(int id) {
        return clientAccountDao.findById(id);
    }

    @Override
    public List<ClientAccountCas> findAll() {
        PagingIterable<ClientAccountCas> iterable = clientAccountDao.findAll();
        return StreamSupport.stream(iterable.spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public boolean update(ClientAccountCas entity) {
        try {
            clientAccountDao.update(entity);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(ClientAccountCas clientAccountCas) {
        try {
            clientAccountDao.delete(clientAccountCas);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private void createTable() {
        SimpleStatement createType =
                SchemaBuilder.createType(AddressIDs.TABLE_NAME)
                        .ifNotExists()
                        .withField(AddressIDs.CITY, DataTypes.TEXT)
                        .withField(AddressIDs.STREET, DataTypes.TEXT)
                        .withField(AddressIDs.HOUSE_NUMBER, DataTypes.TEXT)
                        .build();

        session.execute(createType);

        UserDefinedType addressCas = new UserDefinedTypeBuilder(RentIDs.TABLE_NAME_NAMESPACE, AddressIDs.TABLE_NAME)
                .withField("city", DataTypes.TEXT)
                .withField("street", DataTypes.TEXT)
                .withField("house_number", DataTypes.TEXT)
                .build();


        SimpleStatement createTable =
                SchemaBuilder.createTable(ClientAccountIDs.TABLE_NAME)
                        .ifNotExists()
                        .withPartitionKey(ClientAccountIDs.ID, DataTypes.INT)
                        .withColumn(ClientAccountIDs.FIRST_NAME, DataTypes.TEXT)
                        .withColumn(ClientAccountIDs.LAST_NAME, DataTypes.TEXT)
                        .withColumn(ClientAccountIDs.ADDRESS, addressCas)
                        .withColumn(ClientAccountIDs.CLIENT_TYPE, DataTypes.TEXT)
                        .withColumn(ClientAccountIDs.IS_ARCHIVED, DataTypes.BOOLEAN)
                        .withColumn(ClientAccountIDs.RENTS, DataTypes.INT)
                        .build();

        session.execute(createTable);
    }



}
