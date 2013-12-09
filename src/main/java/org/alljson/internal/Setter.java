package org.alljson.internal;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

public class Setter implements PropertyWriter {
    private final Method setter;

    public Setter(final Method setter) {
        this.setter = setter;
    }

    @Override
    public Annotation getAnnotationOfType(final Class<Annotation> type) {
        return setter.getAnnotation(type);
    }

    @Override
    public void setValueTo(final Object object, final Object value) {
        try {
            setter.setAccessible(true);
            setter.invoke(object, value);
        } catch (IllegalAccessException e) {
            throw new IllegalStateException(e);
        } catch (InvocationTargetException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public Type getPropertyType() {
        return setter.getGenericParameterTypes()[0];
    }
}