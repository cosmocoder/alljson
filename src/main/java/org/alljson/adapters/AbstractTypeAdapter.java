package org.alljson.adapters;

public abstract class AbstractTypeAdapter<I> implements TypeAdapter<I> {
    @Override
    public Object adapt(I input) {
        return (input == null) ? null : adaptNotNullValue(input);
    }

    public abstract Object adaptNotNullValue(I input);
}
