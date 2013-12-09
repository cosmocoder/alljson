package org.alljson.serialization;

import org.alljson.serialization.templates.SimpleSerializer;
import org.alljson.types.JsonNumber;

public class NumberSerializer extends SimpleSerializer<Number> {

    public NumberSerializer() {
        super(Number.class);
    }

    @Override
    public JsonNumber convertNotNullValue(Number input) {
        return JsonNumber.create(input);
    }
}
