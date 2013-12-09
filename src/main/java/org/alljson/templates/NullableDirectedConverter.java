package org.alljson.templates;

import org.alljson.templates.Converter;

import java.lang.reflect.Type;

public abstract class NullableDirectedConverter<I,O> implements Converter<I,O> {

    private final Class<I> inputClass;
    private final Class<O> outputClass;

    protected NullableDirectedConverter(final Class<I> inputClass, final Class<O> outputClass) {
        this.inputClass = inputClass;
        this.outputClass = outputClass;
    }

    @Override
    public O convert(I input, Converter masterConverter) {
        return convert(input, outputClass, masterConverter);
    }

    @Override
    public O convert(final I input, final Type outputType, final Converter masterConverter) {
        return (input == null) ? null : convertNotNullValue(input, outputClass, masterConverter);
    }

    protected abstract O convertNotNullValue(final I input, final Type outputType, final Converter masterAdapter);

    public Class<I> getInputClass() {
        return inputClass;
    }

    public Class<O> getOutputClass() {
        return outputClass;
    }
}
