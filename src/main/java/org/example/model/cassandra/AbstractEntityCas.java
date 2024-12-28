package org.example.model.cassandra;


import com.datastax.oss.driver.api.mapper.annotations.PartitionKey;

import java.io.Serializable;

public abstract class AbstractEntityCas implements Serializable {

    @PartitionKey
    private int entityId;

    public AbstractEntityCas(int entityId) {
        this.entityId = entityId;
    }

    public AbstractEntityCas() {
    }

    public int getEntityId() {
        return entityId;
    }

    public void setEntityId(int entityId) {
        this.entityId = entityId;
    }


}
