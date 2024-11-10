package org.example.mgd;

import org.bson.codecs.pojo.annotations.BsonProperty;

import java.io.Serializable;

public abstract class AbstractEntityMgd implements Serializable {
    @BsonProperty("_id")
    private final int entityId;

    public int getEntityId() {
        return entityId;
    }

    public AbstractEntityMgd(int entityId) {
        this.entityId = entityId;
    }
}
