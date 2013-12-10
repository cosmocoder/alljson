package org.alljson.internal;

import com.google.common.collect.Lists;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class Types {
    public static <T> HandledType<T> of(Class<T> clazz) {
        return DelegateType.of(clazz);
    }

    public static <T> HandledType<T> of(java.lang.reflect.Type type) {
        return DelegateType.of(type);
    }

    public static <T> CustomType<T> of(Class<T> clazz, List<HandledType<? extends Object>> parameters) {
        return CustomType.of(clazz, Lists.newArrayList(parameters));
    }

    public static <T> CustomType<T> of(Class<T> clazz, HandledType<? extends Object>... parameters) {
        return of(clazz, Lists.newArrayList(parameters));
    }

    public static <K, V> CustomType<Map<K, V>> mapOf(HandledType<K> key, HandledType<V> value) {
        Class<Map<K, V>> clazz = (Class) Map.class;
        return of(clazz, key, value);
    }

    public static <K, V> CustomType<Map<K, V>> mapOf(HandledType<K> key, Class<V> value) {
        return mapOf(key, of(value));
    }

    public static <K, V> CustomType<Map<K, V>> mapOf(Class<K> key, HandledType<V> value) {
        return mapOf(of(key), value);
    }

    public static <K, V> CustomType<Map<K, V>> mapOf(Class<K> key, Class<V> value) {
        return mapOf(of(key), of(value));
    }

    public static <E> CustomType<List<E>> listOf(HandledType<E> element) {
        Class<List<E>> clazz = (Class) List.class;
        return of(clazz, element);
    }

    public static <E> CustomType<List<E>> listOf(Class<E> element) {
        return listOf(of(element));
    }

    public static <T> HandledType<T[]> arrayOf(Class<T> T) {
        return of((Class)Array.newInstance(T, 0).getClass());
    }

    public static <T> HandledType<T[]> arrayOf(HandledType<T> element) {
        return of((Class) Array.newInstance(element.toClass(), 0).getClass(), element.getParameters());
    }
}
