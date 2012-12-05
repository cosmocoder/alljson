package org.alljson.adapters;

import org.joda.time.DateTime;

import java.util.Date;

public class DateAdapter extends AbstractTypeAdapter<Date>{
    @Override
    public String adaptNotNullValue(Date date) {
        return new DateTime(date).toString();
    }
}
