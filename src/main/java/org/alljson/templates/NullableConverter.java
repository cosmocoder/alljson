package org.alljson.templates;

import org.alljson.templates.Converter;

import java.lang.reflect.Type;

public abstract class NullableConverter<I,O> implements Converter<I,O> {

    private final Class<I> inputClass;
    private final Class<O> outputClass;

    protected NullableConverter(final Class<I> inputClass, final Class<O> outputClass) {
        this.inputClass = inputClass;
        this.outputClass = outputClass;
    }

    @Override
    public O convert(I input, Converter masterConverter) {
        return (input == null) ? null : convertNotNullValue(input, masterConverter);
    }

    @Override
    public O convert(final I input, final Type outputType, final Converter masterConverter) {
        return convert(input, masterConverter);
    }

    @Override
    public O convert(final I input, final Class<O> outputType, final Converter masterConverter) {
        return convert(input, masterConverter);
    }

    protected abstract O convertNotNullValue(I input, final Converter masterAdapter);

    public Class<I> getInputType() {
        return inputClass;
    }

    public Class<O> getOutputClass() {
        return outputClass;
    }
}
