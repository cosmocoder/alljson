package org.alljson.internal;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Type;

public class FieldAccessor implements PropertyReader, PropertyWriter {
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

    @Override
    public Type getPropertyType() {
        return field.getGenericType();
    }

    @Override
    public void setValueTo(final Object object, final Object value) {
        try {
            field.setAccessible(true);
            field.set(object, value);
        } catch (IllegalAccessException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public Annotation getAnnotationOfType(final Class<Annotation> type) {
        return field.getAnnotation(type);
    }
}