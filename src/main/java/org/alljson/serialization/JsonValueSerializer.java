package org.alljson.serialization;

import org.alljson.serialization.templates.SimpleSerializer;
import org.alljson.types.JsonValue;

public class JsonValueSerializer extends SimpleSerializer<JsonValue> {

    public JsonValueSerializer() {
        super(JsonValue.class);
    }

    @Override
    protected JsonValue convertNotNullValue(final JsonValue input) {
        return input;
    }
}
