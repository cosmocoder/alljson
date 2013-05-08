package org.alljson.serialization;

import org.alljson.serialization.templates.SimpleSerializer;
import org.alljson.types.JsonString;

public class StringSerializer extends SimpleSerializer<String> {

    public StringSerializer() {
        super(String.class);
    }

    @Override
    public JsonString convertNotNullValue(String input) {
        return new JsonString(input);
    }
}
