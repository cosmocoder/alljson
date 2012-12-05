package org.alljson.properties;

import java.lang.reflect.Field;

public class FieldAccessor implements PropertyReader {
    private final Field field;

    public FieldAccessor(final Field field) {
        this.field = field;
    }

    @Override
    public Object getValueFrom(Object object) {
        try {
            field.setAccessible(true);
            return field.get(object);
        } catch (IllegalAccessException e) {
            throw new IllegalStateException(e);
        }
    }
}
