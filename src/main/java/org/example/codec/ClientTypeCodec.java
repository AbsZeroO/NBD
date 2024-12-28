package org.example.codec;

import com.datastax.oss.driver.api.core.ProtocolVersion;
import com.datastax.oss.driver.api.core.type.DataType;
import com.datastax.oss.driver.api.core.type.DataTypes;
import com.datastax.oss.driver.api.core.type.codec.TypeCodec;
import com.datastax.oss.driver.api.core.type.reflect.GenericType;
import edu.umd.cs.findbugs.annotations.NonNull;
import edu.umd.cs.findbugs.annotations.Nullable;
import org.example.model.domain.ClientType;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class ClientTypeCodec implements TypeCodec<ClientType> {

    @NonNull
    @Override
    public GenericType<ClientType> getJavaType() {
        return GenericType.of(ClientType.class);
    }

    @NonNull
    @Override
    public DataType getCqlType() {
        return DataTypes.TEXT;
    }

    @Nullable
    @Override
    public ByteBuffer encode(@Nullable ClientType clientType, @NonNull ProtocolVersion protocolVersion) {
        if (clientType == null) {
            return null;
        }
        String clientTypeString = clientType.name();
        return ByteBuffer.wrap(clientTypeString.getBytes(StandardCharsets.UTF_8));
    }

    @Nullable
    @Override
    public ClientType decode(@Nullable ByteBuffer byteBuffer, @NonNull ProtocolVersion protocolVersion) {
        if (byteBuffer == null) {
            return null;
        }
        String clientTypeString = new String(byteBuffer.array(), StandardCharsets.UTF_8).trim();
        return ClientType.valueOf(clientTypeString);
    }

    @NonNull
    @Override
    public String format(@Nullable ClientType clientType) {
        if (clientType == null) {
            return "";
        }
        return clientType.name();
    }

    @Nullable
    @Override
    public ClientType parse(@Nullable String s) {
        if (s == null || s.isEmpty()) {
            return null;
        }
        return ClientType.valueOf(s);
    }
}
