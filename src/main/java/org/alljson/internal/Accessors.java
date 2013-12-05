package org.alljson.internal;

import com.google.common.base.Objects;
import com.google.common.base.Throwables;
import sun.reflect.MethodAccessor;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import static com.google.common.base.Objects.firstNonNull;
import static com.google.common.collect.Lists.newArrayList;

public class Accessors {
    public static PropertyWriter writerFor(Class<?> clazz, String propertyName) {
        //public setter
        final String setterName = setterName(propertyName);
        try {
            final Method method = clazz.getMethod(setterName);
            return new Setter(method);
        } catch (NoSuchMethodException e) {
        }

        //public field
        try {
            final Field field = clazz.getField(propertyName);
            return new FieldAccessor(field);
        } catch (NoSuchFieldException e) {
        }

        //non public setter
        Method method = method(clazz, setterName);
        if(method != null) {
            return new Setter(method);
        }

        //non public field
        Field field = field(clazz, setterName);
        if(field != null) {
            return new FieldAccessor(field);
        }

        throw new IllegalArgumentException(
                String.format("Could not create a property writer for property: %s of class: %s",
                        propertyName, clazz.getCanonicalName())
        );
    }


    private static String setterName(String propertyName) {
        String firstLetter = propertyName.substring(1);
        return "set" + firstLetter.toUpperCase();
    }

    private static Method method(Class<?> clazz, String methodName) {
        Class<?> currentClazz = clazz;
        do {
            try {
                return clazz.getMethod(methodName);
            } catch (NoSuchMethodException e) {
                clazz = clazz.getSuperclass();
            }
        } while (clazz != null);
        return null;
    }

    private static Field field(Class<?> clazz, String fieldName) {
        Class<?> currentClazz = clazz;
        do {
            try {
                return clazz.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                clazz = clazz.getSuperclass();
            }
        } while (clazz != null);
        return null;
    }
}
