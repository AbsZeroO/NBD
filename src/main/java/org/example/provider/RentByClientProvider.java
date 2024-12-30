package org.example.provider;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.Row;
import com.datastax.oss.driver.api.mapper.MapperContext;
import com.datastax.oss.driver.api.querybuilder.QueryBuilder;
import com.datastax.oss.driver.api.querybuilder.relation.Relation;
import com.datastax.oss.driver.api.querybuilder.select.Select;
import org.example.identity.ClientAccountIDs;
import org.example.identity.RentIDs;
import org.example.model.cassandra.RentCasByClient;
import org.example.model.cassandra.RentCasByVehicle;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class RentByClientProvider {

    private final CqlSession session;

    public RentByClientProvider(MapperContext context) {
        this.session = context.getSession();
    }

    public List<RentCasByClient> findCurrentRentsByClient(int vehicleCas) {
        Select select = QueryBuilder.selectFrom(RentIDs.TABLE_NAME_NAMESPACE, RentIDs.TABLE_NAME_BY_CLIENTS)
                .all()
                .where(Relation.column(RentIDs.CLIENT_ACCOUNT_CAS).isEqualTo(QueryBuilder.literal(vehicleCas)))
                .where(Relation.column(RentIDs.END_TIME).isEqualTo(QueryBuilder.literal("1970-01-01 00:00:00")))
                .where(Relation.column(RentIDs.ARCHIVED).isEqualTo(QueryBuilder.literal(false)))
                .allowFiltering();

        List<RentCasByClient> activeRents = new ArrayList<>();
        List<Row> rows = session.execute(select.build()).all();

        for (Row row : rows) {
            activeRents.add(getRentCasByClient(row));
        }

        return activeRents;
    }



    public List<RentCasByClient> findArchivedRentsByClient(int vehicleCas) {
        Select select = QueryBuilder.selectFrom(RentIDs.TABLE_NAME_NAMESPACE, RentIDs.TABLE_NAME)
                .all()
                .where(Relation.column(RentIDs.CLIENT_ACCOUNT_CAS).isEqualTo(QueryBuilder.literal(vehicleCas)))
                .where(Relation.column(RentIDs.END_TIME).isGreaterThan(QueryBuilder.literal("1970-01-01 00:00:00")))
                .where(Relation.column(RentIDs.ARCHIVED).isEqualTo(QueryBuilder.literal(true)))
                .allowFiltering();

        List<RentCasByClient> archivedRents = new ArrayList<>();
        List<Row> rows = session.execute(select.build()).all();

        for (Row row : rows) {
            archivedRents.add(getRentCasByClient(row));
        }

        return archivedRents;
    }

    private RentCasByClient getRentCasByClient(Row row) {
        LocalDateTime beginDateTime = row.get(RentIDs.BEGIN_TIME, LocalDateTime.class);
        LocalDateTime endDateTime = row.get(RentIDs.END_TIME, LocalDateTime.class);

        return new RentCasByClient(
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
