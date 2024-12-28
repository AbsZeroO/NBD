package org.example.provider;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.Row;
import com.datastax.oss.driver.api.mapper.MapperContext;
import com.datastax.oss.driver.api.mapper.entity.EntityHelper;
import com.datastax.oss.driver.api.querybuilder.QueryBuilder;
import com.datastax.oss.driver.api.querybuilder.relation.Relation;
import com.datastax.oss.driver.api.querybuilder.select.Select;
import org.example.identity.RentIDs;
import org.example.identity.VehicleIDs;
import org.example.model.cassandra.BicycleCas;
import org.example.model.cassandra.CarCas;
import org.example.model.cassandra.VehicleCas;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class VehicleProvider {
    private final CqlSession session;
    private final EntityHelper<CarCas> carCasEntityHelper;
    private final EntityHelper<BicycleCas> bicycleCasEntityHelper;

    public VehicleProvider(MapperContext context, EntityHelper<CarCas> carCasEntityHelper,
                           EntityHelper<BicycleCas> bicycleCasEntityHelper) {
        session = context.getSession();
        this.carCasEntityHelper = carCasEntityHelper;
        this.bicycleCasEntityHelper = bicycleCasEntityHelper;
    }

    public void create(VehicleCas vehicleCas) {
        session.execute(
                switch (vehicleCas.getDiscriminator()) {
                    case "car" -> {
                        CarCas carCas = (CarCas) vehicleCas;
                        yield session.prepare(carCasEntityHelper.insert().build())
                                .bind()
                                .setInt(VehicleIDs.ID, carCas.getEntityId())
                                .setString(VehicleIDs.PLATE_NUMBER, carCas.getPlateNumber())
                                .setDouble(VehicleIDs.BASE_PRICE, carCas.getBasePrice())
                                .setInt(VehicleIDs.ENGINE_DISPLACEMENT, carCas.getEngineDisplacement())
                                .setBoolean(VehicleIDs.RENTED, carCas.isRented())
                                .setBoolean(VehicleIDs.ARCHIVED, carCas.isArchived())
                                .setString(VehicleIDs.DISCRIMINATOR, "car")
                                .setString(VehicleIDs.SEGMENT_TYPE, carCas.getSegmentType());
                    }
                    case "bicycyle" -> {
                        BicycleCas bicycleCas = (BicycleCas) vehicleCas;
                        yield session.prepare(bicycleCasEntityHelper.insert().build())
                                .bind()
                                .setInt(VehicleIDs.ID, bicycleCas.getEntityId())
                                .setString(VehicleIDs.PLATE_NUMBER, bicycleCas.getPlateNumber())
                                .setDouble(VehicleIDs.BASE_PRICE, bicycleCas.getBasePrice())
                                .setInt(VehicleIDs.ENGINE_DISPLACEMENT, bicycleCas.getEngineDisplacement())
                                .setBoolean(VehicleIDs.RENTED, bicycleCas.isRented())
                                .setBoolean(VehicleIDs.ARCHIVED, bicycleCas.isArchived())
                                .setString(VehicleIDs.DISCRIMINATOR, "bicycyle");
                    }
                    default -> throw new IllegalArgumentException();
                }
        );
    }

    public VehicleCas findById(int id) {
        Select select = QueryBuilder.selectFrom(RentIDs.TABLE_NAME_NAMESPACE, VehicleIDs.TABLE_NAME)
                .all()
                .where(Relation.column(VehicleIDs.ID).isEqualTo(QueryBuilder.literal(id)));
        Row row = session.execute(select.build()).one();
        return switch (row.getString(VehicleIDs.DISCRIMINATOR)) {
            case "car" -> getCar(row);
            case "bicycyle" -> getBicycle(row);
            default -> throw new IllegalArgumentException();
        };
    }

    public List<VehicleCas> findAll() {
        Select select = QueryBuilder.selectFrom(RentIDs.TABLE_NAME_NAMESPACE, VehicleIDs.TABLE_NAME)
                .all();

        List<VehicleCas> vehicles = new ArrayList<>();
        List<Row> rows = session.execute(select.build()).all();

        for (Row row :
                rows) {
            switch (row.getString(VehicleIDs.DISCRIMINATOR)) {
                case "car" -> vehicles.add(getCar(row));
                case "bicycyle" -> vehicles.add(getBicycle(row));
                default -> {
                    return Collections.emptyList();
                }
            }
        }
        return vehicles;
    }

    public void update(VehicleCas vehicleCas) {
        session.execute(
                switch (vehicleCas.getDiscriminator()) {
                    case "car" -> {
                        CarCas carCas = (CarCas) vehicleCas;
                        yield session.prepare(carCasEntityHelper.updateByPrimaryKey().build())
                                .bind()
                                .setInt(VehicleIDs.ID, carCas.getEntityId())
                                .setString(VehicleIDs.PLATE_NUMBER, carCas.getPlateNumber())
                                .setDouble(VehicleIDs.BASE_PRICE, carCas.getBasePrice())
                                .setInt(VehicleIDs.ENGINE_DISPLACEMENT, carCas.getEngineDisplacement())
                                .setBoolean(VehicleIDs.RENTED, carCas.isRented())
                                .setBoolean(VehicleIDs.ARCHIVED, carCas.isArchived())
                                .setString(VehicleIDs.DISCRIMINATOR, "car")
                                .setString(VehicleIDs.SEGMENT_TYPE, carCas.getSegmentType().toString());
                    }
                    case "bicycyle" -> {
                        BicycleCas bicycleCas = (BicycleCas) vehicleCas;
                        yield session.prepare(bicycleCasEntityHelper.updateByPrimaryKey().build())
                                .bind()
                                .setInt(VehicleIDs.ID, bicycleCas.getEntityId())
                                .setString(VehicleIDs.PLATE_NUMBER, bicycleCas.getPlateNumber())
                                .setDouble(VehicleIDs.BASE_PRICE, bicycleCas.getBasePrice())
                                .setInt(VehicleIDs.ENGINE_DISPLACEMENT, bicycleCas.getEngineDisplacement())
                                .setBoolean(VehicleIDs.RENTED, bicycleCas.isRented())
                                .setBoolean(VehicleIDs.ARCHIVED, bicycleCas.isArchived())
                                .setString(VehicleIDs.DISCRIMINATOR, "bicycyle");
                    }
                    default -> throw new IllegalArgumentException();
                }
        );
    }

    private VehicleCas getCar(Row row) {
        return new CarCas(
                row.getInt(VehicleIDs.ID),
                row.getString(VehicleIDs.PLATE_NUMBER),
                row.getDouble(VehicleIDs.BASE_PRICE),
                row.getInt(VehicleIDs.ENGINE_DISPLACEMENT),
                row.getBoolean(VehicleIDs.RENTED),
                row.getBoolean(VehicleIDs.ARCHIVED),
                row.getString(VehicleIDs.SEGMENT_TYPE),
                row.getString(VehicleIDs.DISCRIMINATOR)
        );
    }

    private VehicleCas getBicycle(Row row) {
        return new BicycleCas(
                row.getInt(VehicleIDs.ID),
                row.getString(VehicleIDs.PLATE_NUMBER),
                row.getDouble(VehicleIDs.BASE_PRICE),
                row.getInt(VehicleIDs.ENGINE_DISPLACEMENT),
                row.getBoolean(VehicleIDs.RENTED),
                row.getBoolean(VehicleIDs.ARCHIVED),
                row.getString(VehicleIDs.DISCRIMINATOR)
        );
    }

}
