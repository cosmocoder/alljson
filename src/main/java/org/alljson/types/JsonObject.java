package org.alljson.types;

import com.google.common.base.Joiner;

import java.util.*;

public class JsonObject implements Map<JsonString, JsonValue>, JsonValue {
    private Map<JsonString, JsonValue> properties;

    public JsonObject(Map<JsonString, JsonValue> properties) {
        this.properties = properties;
    }

    public int size() {
        return properties.size();
    }

    public boolean isEmpty() {
        return properties.isEmpty();
    }

    public boolean containsKey(final Object o) {
        return properties.containsKey(o);
    }

    public boolean containsValue(final Object o) {
        return properties.containsValue(o);
    }

    public JsonValue get(final Object o) {
        return properties.get(o);
    }

    public JsonValue put(final JsonString s, final JsonValue jsonValue) {
        return properties.put(s, jsonValue);
    }

    public JsonValue remove(final Object o) {
        return properties.remove(o);
    }

    public void putAll(final Map<? extends JsonString, ? extends JsonValue> map) {
        properties.putAll(map);
    }

    public void clear() {
        properties.clear();
    }

    public Set<JsonString> keySet() {
        return properties.keySet();
    }

    public Collection<JsonValue> values() {
        return properties.values();
    }

    public Set<Entry<JsonString, JsonValue>> entrySet() {
        return properties.entrySet();
    }

    @Override
    public boolean equals(final Object o) {
        return properties.equals(o);
    }

    @Override
    public int hashCode() {
        return properties.hashCode();
    }

    public String toString() {

        List<String> jsonProperties = new ArrayList<String>();
        for (Entry<JsonString, JsonValue> property : this.properties.entrySet()) {
            jsonProperties.add(property.getKey() + ":" + property.getValue());
        }
        return "{" + Joiner.on(",").join(jsonProperties) + "}";
    }
      /*
    public static JsonValue fromMap(final Object object) {
    }

    public static JsonObject fromBean(Object object) {
        Map<String, JsonValue> properties = new LinkedHashMap<String, JsonValue>();
        final Class<?> clazz = object.getClass();
        for (Field field : org.alljson.properties.Properties.getAnnotatedFields(clazz, JsonProperty.class)) {
            final String propertyName = field.getName();
            if (!properties.containsKey(propertyName)) { //TODO resolve name by annotation first
                final Object value = org.alljson.properties.Properties.getValue(object, field);
                properties.put(propertyName, JsonValues.fromObject(value));
            } else {
                //TODO: log duplicated as debug
            }
        }
        for (Method method : org.alljson.properties.Properties.getAnnotatedMethods(clazz, JsonProperty.class)) {
            final String propertyName = method.getName(); //TODO: throw if its not a getter and don't have name in annotation
            if (!properties.containsKey(propertyName)) { //TODO check if method have parameters and throw , substring getterName
                final Object value = org.alljson.properties.Properties.getValue(object, method);
                properties.put(propertyName, JsonValues.fromObject(value));
            } else {
                //TODO: log duplicated as debug
            }
        }
        for (Field field : org.alljson.properties.Properties.getPublicFields(clazz)) {
            final String propertyName = field.getName();
            if (!properties.containsKey(propertyName)) {
                final Object value = org.alljson.properties.Properties.getValue(object, field);
                properties.put(propertyName, JsonValues.fromObject(value));
            } else {
                //TODO: log duplicated as debug
            }
        }
        for (Method getter : org.alljson.properties.Properties.getPublicGetters(clazz)) {
            final String propertyName = getter.getName(); //TODO: substring
            if (!properties.containsKey(propertyName)) {
                final Object value = org.alljson.properties.Properties.getValue(object, getter);
                properties.put(propertyName, JsonValues.fromObject(value));
            } else {
                //TODO: log duplicated as debug
            }
        }
        return new JsonObject(properties);
    }
    */
}
