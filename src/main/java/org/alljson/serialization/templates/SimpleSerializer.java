package org.alljson.serialization.templates;


import org.alljson.templates.Converter;
import org.alljson.types.JsonValue;

public abstract class SimpleSerializer<I> extends NullableSerializer<I> {

    protected SimpleSerializer(final Class<I> inputClass) {
        super(inputClass);
    }

    @Override
    protected JsonValue convertNotNullValue(final I input, final Converter masterAdapter) {
        return convertNotNullValue(input);
    }

    protected abstract JsonValue convertNotNullValue(final I input);
}
