package org.alljson.serialization;

import org.alljson.templates.Converter;

public class ComposedTypeAdapter<I,O> implements Converter<I,O> {

    private final Class<I> inputClass;
    private final Class<O> outputClass;
    private final Converter<I,Object> first;
    private final Converter<Object,O> second;

    public <T> ComposedTypeAdapter(final Converter<I, T> first, final Converter<? super T, O> second, final Class<I> inputClass, final Class<O> outputClass) {
        this.inputClass = inputClass;
        this.outputClass = outputClass;
        this.first = (Converter<I,Object>) first;
        this.second = (Converter<Object,O>) second;
    }

    @Override
    public O convert(final I input, final Converter masterConverter) {
        Object object = first.convert(input, masterConverter);
        return second.convert(object, masterConverter);
    }

    @Override
    public Class<I> getInputClass() {
        return inputClass;
    }

    @Override
    public Class<O> getOutputClass() {
        return outputClass;
    }
}
