package org.alljson.serialization.adapters;

import org.alljson.templates.Converter;
import org.alljson.templates.NullableConverter;

public class ToStringAdapter extends NullableConverter<Object,String> {
    public ToStringAdapter() {
        super(Object.class, String.class);
    }

    @Override
    public String convertNotNullValue(Object input, final Converter masterAdapter) {
        return input.toString();
    }
}
