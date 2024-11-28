package org.example.red;

import jakarta.json.bind.annotation.JsonbCreator;
import jakarta.json.bind.annotation.JsonbProperty;
import java.io.Serializable;

public abstract class AbstractEntityJsonb implements Serializable {
    @JsonbProperty("_id")
    private int entityId;

    @JsonbCreator
    public AbstractEntityJsonb(@JsonbProperty int entityId) {
        this.entityId = entityId;
    }

    public int getEntityId() {
        return entityId;
    }

    public void setEntityId(int entityId) {
        this.entityId = entityId;
    }
}
