package org.alljson.adapters;

public interface TypeAdapter<I> {
    public Object adapt(I input);
}
