package org.alljson.serializers;

import org.alljson.types.JsonString;

public class StringSerializer extends AbstractSerializer<String>{
    @Override
    public JsonString serializeNotNullValue(String input, SerializationContext context) {
        return new JsonString(input);
    }
}
