package org.alljson.serialization.adapters;

import org.alljson.templates.Converter;
import org.alljson.templates.NullableConverter;
import org.alljson.templates.SimpleConverter;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

public class ArrayToListAdapter<T> extends SimpleConverter<T[],List<T>> {

    public ArrayToListAdapter() {
        super((Class) Object[].class, (Class) List.class);
    }

    @Override
    public List<T> convertNotNullValue(T[] input) {
        return newArrayList(input);
    }
}
