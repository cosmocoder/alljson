package org.alljson.templates;

import org.alljson.templates.Converter;

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

    protected abstract O convertNotNullValue(I input, final Converter masterAdapter);

    public Class<I> getInputClass() {
        return inputClass;
    }

    public Class<O> getOutputClass() {
        return outputClass;
    }
}
