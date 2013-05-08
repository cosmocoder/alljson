package org.alljson.templates;

public interface Converter<I,O> {
    public O convert(I input, Converter masterConverter);
    public Class<I> getInputClass();
    public Class<O> getOutputClass();
}
