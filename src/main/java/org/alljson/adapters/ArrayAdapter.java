package org.alljson.adapters;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

public class ArrayAdapter<T> extends AbstractTypeAdapter<T[]>{
    @Override
    public List<T> adaptNotNullValue(T[] input) {
        return newArrayList(input);
    }
}
