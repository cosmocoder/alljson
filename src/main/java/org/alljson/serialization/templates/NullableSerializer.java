package org.alljson.serialization.templates;

import org.alljson.templates.Converter;
import org.alljson.types.JsonNull;
import org.alljson.types.JsonValue;

public abstract class NullableSerializer<I> implements Converter<I,JsonValue> {

    private final Class<I> inputClass;

    protected NullableSerializer(final Class<I> inputClass) {
        this.inputClass = inputClass;
    }

    @Override
    public JsonValue convert(I input, Converter masterConverter) {
        return (input == null) ? JsonNull.INSTANCE : convertNotNullValue(input, masterConverter);
    }

    protected abstract JsonValue convertNotNullValue(I input, final Converter masterAdapter);

    public Class<I> getInputClass() {
        return inputClass;
    }

    public Class<JsonValue> getOutputClass() {
        return JsonValue.class;
    }
}
