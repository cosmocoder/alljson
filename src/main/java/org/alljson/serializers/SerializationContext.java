package org.alljson.serializers;

import com.google.common.base.Objects;
import org.alljson.adapters.TypeAdapter;
import org.alljson.types.JsonNull;
import org.alljson.types.JsonValue;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.newTreeMap;

public class SerializationContext {

    private final Map<Class<?>, TypeAdapter<?>> primaryAdapters;
    private final Map<Class<?>, TypeAdapter<?>> secondaryAdapters;
    private final Map<Class<?>, Serializer> serializers;

    public SerializationContext(Map<Class<?>, TypeAdapter<?>> primaryAdapters, Map<Class<?>, TypeAdapter<?>> secondaryAdapters, Map<Class<?>, Serializer<?>> serializers) {
        this.primaryAdapters = newTreeMap(new ClassComparator(primaryAdapters.keySet()));
        this.primaryAdapters.putAll(primaryAdapters);
        this.secondaryAdapters = newTreeMap(new ClassComparator(secondaryAdapters.keySet()));
        this.secondaryAdapters.putAll(secondaryAdapters);
        this.serializers = new TreeMap<Class<?>, Serializer>(new ClassComparator(serializers.keySet()));
        this.serializers.putAll(serializers);
    }

    private <T> T findAndStore(Class<?> clazz, final Map<Class<?>, T> findHere, Map<Class<?>, T> storeHere) {
        T adapter;

        if (findHere.containsKey(clazz)) {
            adapter = findHere.get(clazz); //even if null
        } else {
            adapter = findFromSuperclasses(clazz, findHere);
            storeHere.put(clazz, adapter); //even if null
        }

        return adapter;
    }

    private <T> T findFromSuperclasses(final Class<?> clazz, final Map<Class<?>, T> findHere) {
        for (Class<?> findedClass : findHere.keySet()) {
            if (findedClass.isAssignableFrom(clazz)) {
                return findHere.get(findedClass);
            }
        }
        return null;
    }


    public JsonValue serialize(Object object) {
        if (object == null) {
            return JsonNull.INSTANCE;
        }

        Object adaptedObject = adapt(object, primaryAdapters);

        Serializer serializer = findAndStore(adaptedObject.getClass(), serializers, serializers);
        if (serializer != null) {
            return serializer.serialize(adaptedObject, this);
        }

        TypeAdapter fallBackAdapter = findAndStore(adaptedObject.getClass(), secondaryAdapters, primaryAdapters);
        if (fallBackAdapter != null) {
            return serialize(fallBackAdapter.adapt(adaptedObject));
        }

        throw new IllegalArgumentException(String.format("Could not serialize %s", object.getClass().getCanonicalName()));
    }

    private Object adapt(final Object object, Map<Class<?>, TypeAdapter<?>> adapters) {
        Object last;
        Object current = object;
        TypeAdapter adapter;
        do {
            adapter = findAndStore(current.getClass(), adapters, adapters);
            last = current;
            if(adapter != null) {
                current = adapter.adapt(current);
            }
        } while (!Objects.equal(current, last) );
        return current;
    }

    private static class ClassComparator implements Comparator<Class<?>> {

        private final List<Class<?>> originalOrder;

        private ClassComparator(Iterable<Class<?>> originalOrder) {
            this.originalOrder = newArrayList(originalOrder);
        }

        @Override
        public int compare(Class<?> first, Class<?> second) {

            //Same class, same order
            if (first.equals(second)) {
                return 0;
            }

            //More specific class first
            if (second.isAssignableFrom(first)) {
                return -1;
            }

            //More specific class first
            if (first.isAssignableFrom(second)) {
                return 1;
            }

            //Both classes are not original, sort by name
            if(!originalOrder.contains(first) && !originalOrder.contains(second)) {
                return first.getCanonicalName().compareTo(second.getCanonicalName());
            }

            //Newly added class first
            if(!originalOrder.contains(first)) {
                return -1;
            }

            //Newly added class first
            if(!originalOrder.contains(second)) {
                return 1;
            }

            //Newly added class first
            return Integer.valueOf(originalOrder.indexOf(second)).compareTo(originalOrder.indexOf(first));
        }
    }

    public boolean isIgnoreNullProperties() {
        return false;
    }
}
