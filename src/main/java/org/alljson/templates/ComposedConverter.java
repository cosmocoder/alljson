package org.alljson.templates;

import org.alljson.templates.Converter;

import java.lang.reflect.Type;

public class ComposedConverter<I,O> implements Converter<I,O> {

    private final Class<I> inputClass;
    private final Class<O> outputClass;
    private final Converter<I,Object> first;
    private final Converter<Object,O> second;

    public <T> ComposedConverter(final Converter<I, T> first, final Converter<? super T, O> second, final Class<I> inputClass, final Class<O> outputClass) {
        this.inputClass = inputClass;
        this.outputClass = outputClass;
        this.first = (Converter<I,Object>) first;
        this.second = (Converter<Object,O>) second;
    }

    @Override
    public O convert(final I input, Type outputType, final Converter masterConverter) {
        Object object = first.convert(input, masterConverter);
        return second.convert(object, outputType, masterConverter);
    }

    @Override
    public O convert(final I input, Class<O> outputType, final Converter masterConverter) {
        Object object = first.convert(input, masterConverter);
        return second.convert(object, outputType, masterConverter);
    }

    @Override
    public O convert(final I input, final Converter masterConverter) {
        return convert(input, outputClass, masterConverter);
    }

    @Override
    public Class<I> getInputType() {
        return inputClass;
    }

    @Override
    public Class<O> getOutputClass() {
        return outputClass;
    }
}
