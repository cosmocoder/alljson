package org.alljson.types;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class JsonObject implements Map<JsonString, JsonValue>, JsonValue {

    private static final String INITIAL_CHAR = "{";
    private static final String FINAL_CHAR = "}";
    private static final String PROPERTY_SEPARATOR = ",";
    private static final String KEY_VALUE_SERAPATOR = ":";

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
        final StringBuilder stringBuilder = new StringBuilder();
        appendStringTo(stringBuilder);
        return stringBuilder.toString();
    }

    @Override
    public void appendStringTo(StringBuilder stringBuilder) {
        stringBuilder.append(INITIAL_CHAR);
        Iterator<Entry<JsonString,JsonValue>> entryIterator = properties.entrySet().iterator();
        if(entryIterator.hasNext()) {
            appendPropertyTo(stringBuilder, entryIterator.next());
        }
        while (entryIterator.hasNext()) {
            stringBuilder.append(PROPERTY_SEPARATOR);
            appendPropertyTo(stringBuilder, entryIterator.next());
        }
        stringBuilder.append(FINAL_CHAR);
    }

    private void appendPropertyTo(final StringBuilder stringBuilder, final Entry<JsonString, JsonValue> entry) {
        entry.getKey().appendStringTo(stringBuilder);
        stringBuilder.append(KEY_VALUE_SERAPATOR);
        entry.getValue().appendStringTo(stringBuilder);
    }
}
