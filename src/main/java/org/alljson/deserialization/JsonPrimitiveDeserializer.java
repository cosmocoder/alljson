package org.alljson.deserialization;

import org.alljson.deserialization.templates.SimpleDeserializer;
import org.alljson.templates.Converter;
import org.alljson.types.JsonPrimitive;

public class JsonPrimitiveDeserializer extends SimpleDeserializer<JsonPrimitive,Object> {

    protected JsonPrimitiveDeserializer() {
        super(JsonPrimitive.class, Object.class);
    }

    @Override
    protected Object convertNotNullValue(final JsonPrimitive input) {
        return input.getValue();
    }

}
