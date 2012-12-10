package org.alljson.support;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Getter implements PropertyReader {
    private final Method getter;

    public Getter(final Method getter) {
        this.getter = getter;
    }

    @Override
    public Object getValueFrom(Object object) {
        try {
            getter.setAccessible(true);
            return getter.invoke(object);
        } catch (IllegalAccessException e) {
            throw new IllegalStateException(e);
        } catch (InvocationTargetException e) {
            throw new IllegalStateException(e);
        }
    }
}