package org.alljson.serialization.adapters;

import org.alljson.templates.Converter;
import org.alljson.templates.NullableConverter;
import org.alljson.templates.SimpleConverter;

public class ToStringAdapter extends SimpleConverter<Object,String> {
    public ToStringAdapter() {
        super(Object.class, String.class);
    }

    @Override
    public String convertNotNullValue(Object input) {
        return input.toString();
    }
}
