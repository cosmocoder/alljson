package org.alljson.serialization.adapters;

import org.alljson.templates.Converter;
import org.alljson.templates.NullableConverter;
import org.alljson.templates.SimpleConverter;
import org.joda.time.DateTime;

import java.util.Date;

public class DateToStringAdapter extends SimpleConverter<Date, String> {
    public DateToStringAdapter() {
        super(Date.class, String.class);
    }

    @Override
    public String convertNotNullValue(Date date) {
        return new DateTime(date).toString();
    }
}
