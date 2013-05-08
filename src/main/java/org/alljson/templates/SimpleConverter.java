package org.alljson.templates;

public abstract class SimpleConverter<I,O> extends NullableConverter<I, O> {

    protected SimpleConverter(final Class<I> inputClass, final Class<O> outputClass) {
        super(inputClass, outputClass);
    }

    @Override
    protected O convertNotNullValue(final I input, final Converter masterAdapter) {
        return convertNotNullValue(input);
    }

    protected abstract O convertNotNullValue(final I input);
}
