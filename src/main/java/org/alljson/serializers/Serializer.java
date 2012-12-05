package org.alljson.serializers;

import org.alljson.types.JsonValue;

public interface Serializer<I> {
    JsonValue serialize(I input, SerializationContext context);
}
