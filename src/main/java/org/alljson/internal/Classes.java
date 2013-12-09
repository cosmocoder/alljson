package org.alljson.internal;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class Classes {
    public static Class<?> ofType(Type type) {
        //raw
        if(type instanceof Class) {
            return (Class) type;
        }

        //generics
        if(type instanceof ParameterizedType) {
            return (Class) ((ParameterizedType) type).getRawType();
        }

        if(type instanceof GenericArrayType) {
            return Object[].class;
        }

        return Object.class;
    }
}
