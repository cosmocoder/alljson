package org.alljson.deserialization.templates;

import org.alljson.templates.Converter;
import org.alljson.templates.SimpleConverter;
import org.alljson.types.JsonValue;

public abstract class SimpleDeserializer<I extends JsonValue, O> extends NullableDeserializer<I, O> {

    protected SimpleDeserializer(final Class<I> inputClass, final Class<O> outputClass) {
        super(inputClass, outputClass);
    }

    @Override
    protected O convertNotNullValue(final I input, final Converter masterAdapter) {
        return convertNotNullValue(input);
    }

    protected abstract O convertNotNullValue(final I input);
}
