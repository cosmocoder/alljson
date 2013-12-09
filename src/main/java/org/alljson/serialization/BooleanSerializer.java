package org.alljson.serialization;

import org.alljson.serialization.templates.SimpleSerializer;
import org.alljson.types.JsonBoolean;

public class BooleanSerializer extends SimpleSerializer<Boolean> {

    public BooleanSerializer() {
        super(Boolean.class);
    }

    @Override
    public JsonBoolean convertNotNullValue(Boolean input) {
        return JsonBoolean.create(input);
    }
}
