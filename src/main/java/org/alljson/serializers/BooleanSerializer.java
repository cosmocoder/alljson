package org.alljson.serializers;

import org.alljson.types.JsonBoolean;
import org.alljson.types.JsonValue;

public class BooleanSerializer extends AbstractSerializer<Boolean> {
    @Override
    public JsonValue serializeNotNullValue(Boolean input, SerializationContext context) {
        return new JsonBoolean(input);
    }
}
