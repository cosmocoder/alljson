package org.alljson.deserialization.templates;

import org.alljson.templates.Converter;
import org.alljson.types.JsonNull;
import org.alljson.types.JsonValue;

public abstract class NullableDeserializer<I extends JsonValue,O> implements Converter<I,O> {

    private final Class<I> inputClass;
    private final Class<O> outputClass;

    protected NullableDeserializer(final Class<I> inputClass, final Class<O> outputClass) {
        this.inputClass = inputClass;
        this.outputClass = outputClass;
    }

    @Override
    public O convert(I input, Converter masterConverter) {
        return JsonNull.INSTANCE.equals(input) || null == input? null : convertNotNullValue(input, masterConverter);
    }

    protected abstract O convertNotNullValue(I input, final Converter masterConverter);

    public Class<I> getInputClass() {
        return inputClass;
    }

    public Class<O> getOutputClass() {
        return outputClass;
    }
}
