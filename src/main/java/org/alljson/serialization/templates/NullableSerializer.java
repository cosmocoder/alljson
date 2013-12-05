package org.alljson.serialization.templates;

import org.alljson.templates.Converter;
import org.alljson.templates.NullableConverter;
import org.alljson.types.JsonNull;
import org.alljson.types.JsonValue;

public abstract class NullableSerializer<I> extends NullableConverter<I,JsonValue> {

    protected NullableSerializer(final Class<I> inputClass) {
        super(inputClass, JsonValue.class);
    }

    @Override
    public JsonValue convert(I input, Converter masterConverter) {
        return (input == null) ? JsonNull.INSTANCE : convertNotNullValue(input, masterConverter);
    }

}
