package org.example.repo;

import static com.datastax.oss.driver.api.core.type.codec.ExtraTypeCodecs.LOCAL_TIMESTAMP_UTC;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.type.codec.registry.MutableCodecRegistry;
import com.datastax.oss.driver.api.querybuilder.SchemaBuilder;
import org.example.codec.ClientTypeCodec;
import org.example.identity.RentIDs;

import java.net.InetSocketAddress;

public abstract class AbstractCassandraRepository implements AutoCloseable {

    protected static CqlSession session;

    public AbstractCassandraRepository() {
        initSession();
    }

    public void initSession() {
        session = CqlSession.builder()
                .addContactPoint(new InetSocketAddress("cassandra1", 9042))
                .addContactPoint(new InetSocketAddress("cassandra2", 9043))
                .withLocalDatacenter("dc1")
                .withAuthCredentials("cassandra", "cassandra")
                .addTypeCodecs()
                .build();

        // Dokończyć
        session.execute(
                SchemaBuilder.createKeyspace(RentIDs.TABLE_NAME_NAMESPACE)
                        .ifNotExists()
                        .withSimpleStrategy(2)
                        .withDurableWrites(true)
                        .build()
        );

        session.execute("USE " + RentIDs.TABLE_NAME_NAMESPACE);

        MutableCodecRegistry registry = (MutableCodecRegistry) session.getContext().getCodecRegistry();
        registry.register();
        registry.register(LOCAL_TIMESTAMP_UTC);
        registry.register(new ClientTypeCodec());
    }

    @Override
    public void close() {
        session.close();
    }
}
