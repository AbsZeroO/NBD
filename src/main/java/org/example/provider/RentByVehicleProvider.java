package org.example.provider;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.Row;
import com.datastax.oss.driver.api.mapper.MapperContext;
import com.datastax.oss.driver.api.mapper.entity.EntityHelper;
import com.datastax.oss.driver.api.querybuilder.QueryBuilder;
import com.datastax.oss.driver.api.querybuilder.relation.Relation;
import com.datastax.oss.driver.api.querybuilder.select.Select;
import org.example.identity.RentIDs;
import org.example.model.cassandra.RentCasByVehicle;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class RentByVehicleProvider {
    private final CqlSession session;

    public RentByVehicleProvider(MapperContext context) {
        this.session = context.getSession();
    }

    public List<RentCasByVehicle> findCurrentRentsByVehicle(int vehicleCas) {
        Select select = QueryBuilder.selectFrom(RentIDs.TABLE_NAME_NAMESPACE, RentIDs.TABLE_NAME_BY_VEHICLES)
                .all()
                .where(Relation.column("vehicle_cas").isEqualTo(QueryBuilder.literal(vehicleCas)))
                .where(Relation.column("end_time").isEqualTo(QueryBuilder.literal("1970-01-01 00:00:00")))
                .where(Relation.column("archived").isEqualTo(QueryBuilder.literal(false)))
                .allowFiltering();

        List<RentCasByVehicle> activeRents = new ArrayList<>();
        List<Row> rows = session.execute(select.build()).all();

        for (Row row : rows) {
            activeRents.add(getRentCasByVehicle(row));
        }

        return activeRents;
    }

    public List<RentCasByVehicle> findArchivedRentsByVehicle(int vehicleCas) {
        Select select = QueryBuilder.selectFrom(RentIDs.TABLE_NAME_NAMESPACE, RentIDs.TABLE_NAME)
                .all()
                .where(Relation.column("vehicle_cas").isEqualTo(QueryBuilder.literal(vehicleCas)))
                .where(Relation.column("end_time").isGreaterThan(QueryBuilder.literal("1970-01-01 00:00:00")))
                .where(Relation.column("archived").isEqualTo(QueryBuilder.literal(true)))
                .allowFiltering();

        List<RentCasByVehicle> archivedRents = new ArrayList<>();
        List<Row> rows = session.execute(select.build()).all();

        for (Row row : rows) {
            archivedRents.add(getRentCasByVehicle(row));
        }

        return archivedRents;
    }



    private RentCasByVehicle getRentCasByVehicle(Row row) {
        LocalDateTime beginDateTime = row.get(RentIDs.BEGIN_TIME, LocalDateTime.class);
        LocalDateTime endDateTime = row.get(RentIDs.END_TIME, LocalDateTime.class);

        return new RentCasByVehicle(
                row.getInt(RentIDs.RENT_ID),
                row.getInt(RentIDs.CLIENT_ACCOUNT_CAS),
                row.getInt(RentIDs.VEHICLE_CAS),
                beginDateTime,
                endDateTime,
                row.getDouble(RentIDs.RENT_COST),
                row.getBoolean(RentIDs.ARCHIVED)
        );
    }

}
