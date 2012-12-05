package org.alljson;

import org.alljson.serializers.SerializationContext;
import org.alljson.types.JsonValue;

public class JsonMapper {
    private final SerializationContext serializationContext;

    public JsonMapper(final SerializationContext serializationContext) {
        this.serializationContext = serializationContext;
    }

    public JsonValue getJson(Object object) {
        return serializationContext.serialize(object);
    }
}
