package org.alljson.serializers;

import org.alljson.types.JsonNumber;

public class NumberSerializer extends AbstractSerializer<Number>{
    @Override
    public JsonNumber serializeNotNullValue(Number input, SerializationContext context) {
        return new JsonNumber(input);
    }
}
