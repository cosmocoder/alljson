package org.alljson.templates;

import java.lang.reflect.Type;

public interface Converter<I,O> {
    public O convert(I input, Converter masterConverter);
    public O convert(I input, Type type, Converter masterConverter);
    public O convert(I input, Class<O> type, Converter masterConverter);
    public Class<I> getInputType();
    public Class<O> getOutputClass();
}
