package org.alljson.serializers;

import org.alljson.types.JsonValue;

public class JsonValueSerializer implements Serializer<JsonValue>{
    @Override
    public JsonValue serialize(final JsonValue value, final SerializationContext context) {
        return value;
    }
}
