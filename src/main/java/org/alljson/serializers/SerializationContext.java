package org.alljson.serializers;

import org.alljson.adapters.TypeAdapter;
import org.alljson.serializers.*;
import org.alljson.types.JsonNull;
import org.alljson.types.JsonValue;

import java.util.*;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.newTreeMap;

public class SerializationContext {

    private final Map<Class, TypeAdapter> primaryAdapters;
    private final Map<Class, TypeAdapter> secondaryAdapters;
    private final Map<Class, Serializer> serializers;

    public SerializationContext(Map<Class, TypeAdapter> primaryAdapters, Map<Class, TypeAdapter> secondaryAdapters, Map<Class, Serializer> serializers) {
        this.primaryAdapters = newTreeMap(new ClassComparator(primaryAdapters.keySet()));
        this.primaryAdapters.putAll(primaryAdapters);
        this.secondaryAdapters = newTreeMap(new ClassComparator(secondaryAdapters.keySet()));
        this.secondaryAdapters.putAll(secondaryAdapters);
        this.serializers = new TreeMap<Class, Serializer>(new ClassComparator(serializers.keySet()));
        this.serializers.putAll(serializers);
    }

    public JsonValue serialize(Object object) {
        if(object == null) {
            return JsonNull.INSTANCE;
        }

        Object primaryAdapted = adapt(object, primaryAdapters.keySet(), primaryAdapters);

        Serializer serializer = findSerializer(primaryAdapted.getClass());
        if (serializer != null) {
            return serializer.serialize(primaryAdapted, this);
        }

        Object secondaryAdapted = adapt(primaryAdapted, secondaryAdapters.keySet(), secondaryAdapters);
        if(secondaryAdapted != primaryAdapted) {
            return serialize(secondaryAdapted);
        }

        throw new IllegalArgumentException(String.format("Could not serialize %s", object.getClass()));
    }

    private Serializer findSerializer(Class objectClass) {
        for (Class serializableClass : serializers.keySet()){
            if(serializableClass.isAssignableFrom(objectClass)) {
                return serializers.get(serializableClass);
            }
        }
        return null;
    }

    private Object adapt(Object object, Iterable<Class> adaptableClasses, Map<Class, TypeAdapter> adaptersMap) {
        List<Class> hierarchy = filterHierarchy(adaptableClasses, object.getClass());
        if (hierarchy.isEmpty()) {
            return object;
        }

        Class adaptableClass = hierarchy.get(0);
        Object adapted = adaptersMap.get(adaptableClass).adapt(object);

        List<Class> usedHierarchy = filterHierarchy(hierarchy, adaptableClass);
        List<Class> nextAdaptableClasses = newArrayList(adaptableClasses);
        nextAdaptableClasses.removeAll(usedHierarchy);

        return adapt(adapted, nextAdaptableClasses, adaptersMap);
    }

    private List<Class> filterHierarchy(Iterable<Class> classes, Class objectClass) {
        List<Class> filtered = new ArrayList<Class>();
        for (Class clazz : classes) {
            if (clazz.isAssignableFrom(objectClass)) {
                filtered.add(clazz);
            }
        }
        return filtered;
    }

    private static class ClassComparator implements Comparator<Class> {

        private final List<Class> originalOrder;

        private ClassComparator(Iterable<Class> originalOrder) {
            this.originalOrder = newArrayList(originalOrder);
        }

        @Override
        public int compare(Class first, Class second) {

            if (first.equals(second)) {
                return 0;
            }

            if (second.isAssignableFrom(first)) {
                return -1;
            }

            if (first.isAssignableFrom(second)) {
                return 1;
            }

            return Integer.valueOf(originalOrder.indexOf(first)).compareTo(originalOrder.indexOf(second));
        }
    }

    public boolean isIgnoreNullProperties() {
        return false;
    }
}
