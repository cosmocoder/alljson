package org.alljson.serialization.adapters;

import org.alljson.templates.Converter;
import org.alljson.templates.NullableConverter;
import org.joda.time.DateTime;

import java.util.Date;

public class DateToStringAdapter extends NullableConverter<Date, String> {
    public DateToStringAdapter() {
        super(Date.class, String.class);
    }

    @Override
    public String convertNotNullValue(Date date, final Converter masterAdapter) {
        return new DateTime(date).toString();
    }
}
