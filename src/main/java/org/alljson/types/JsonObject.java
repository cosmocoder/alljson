package org.alljson.types;

import java.util.*;

public class JsonObject extends JsonValue implements Map<JsonString, JsonValue> {

    private static final String INITIAL_CHAR = "{";
    private static final String FINAL_CHAR = "}";
    private static final String PROPERTY_SEPARATOR = ",";
    private static final String KEY_VALUE_SERAPATOR = ":";
    public static final JsonObjectParser PARSER = new JsonObjectParser();

    public static JsonObject parse(String text) {
        return PARSER.parse(text);
    }

    private Map<JsonString, JsonValue> properties;

    public JsonObject(Map<JsonString, JsonValue> properties) {
        this.properties = new LinkedHashMap<JsonString, JsonValue>(properties);
    }

    public JsonObject() {
        this.properties = new LinkedHashMap<JsonString, JsonValue>();
    }

    public int size() {
        return properties.size();
    }

    public boolean isEmpty() {
        return properties.isEmpty();
    }

    public boolean containsKey(Object key) {
        if (key instanceof String) {
            key = new JsonString((String) key);
        }
        return properties.containsKey(key);
    }

    public boolean containsValue(Object o) {
        return properties.containsValue(o);
    }

    public JsonValue get(Object key) {
        if (key instanceof String) {
            key = new JsonString((String) key);
        }
        return properties.get(key);
    }

    public JsonValue put(final JsonString s, final JsonValue jsonValue) {
        return properties.put(s, jsonValue);
    }

    public JsonValue remove(Object key) {
        if (key instanceof String) {
            key = new JsonString((String) key);
        }
        return properties.remove(key);
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
        Iterator<Entry<JsonString, JsonValue>> entryIterator = properties.entrySet().iterator();
        if (entryIterator.hasNext()) {
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

    static final class JsonObjectParser extends AbstractParser<JsonObject>{
        public static final JsonObjectParser INSTANCE = new JsonObjectParser();

        @Override
        public ParseResult<JsonObject> doPartialParse(final String text) {
            /* { */
            if (text.length() > 1 && text.startsWith(INITIAL_CHAR)) {
                JsonObject json = new JsonObject(new LinkedHashMap<JsonString, JsonValue>());
                char[] objectString = text.toCharArray();
                String remainingText = text.substring(1);
                for(int i=1; i< text.length()-1; i++) {
                    /* "key" */
                    ParseResult<JsonString> keyParse = JsonString.PARSER.partialParse(remainingText);
                    remainingText = remainingText.substring(keyParse.getNextPosition());

                    /* : */
                    ParseResult<String> keyValueSeparatorParse = KeyValueSeparatorParser.INSTANCE.partialParse(remainingText);
                    remainingText = remainingText.substring(keyValueSeparatorParse.getNextPosition());

                    /* value */
                    ParseResult<JsonValue> valueParse = JsonValue.PARSER.partialParse(remainingText);
                    remainingText = remainingText.substring(valueParse.getNextPosition());

                    /* , or } */
                    ParseResult<String> separatorParse = SeparatorParser.INSTANCE.partialParse(remainingText);
                    remainingText = remainingText.substring(separatorParse.getNextPosition());

                    json.put(keyParse.getParsedValue(),valueParse.getParsedValue());

                    String separator = separatorParse.getParsedValue();
                    if(separator.equals(FINAL_CHAR)) {
                        break;
                    } else if(separator.equals(PROPERTY_SEPARATOR)) {
                        continue;
                    } else {
                        throw new IllegalArgumentException(String.format("Can't parse JsonObject from text, = \"%s\", unexpected separator = %s", text, separator));
                    }
                }
                return new ParseResult<JsonObject>(json, text.length() - remainingText.length());
            }

            throw new IllegalArgumentException(String.format("Can't parse JsonObject from text = \"%s\"", text));
        }
    }

    public static class KeyValueSeparatorParser extends AbstractParser<String> {
        public static final KeyValueSeparatorParser INSTANCE = new KeyValueSeparatorParser();

        @Override
        public ParseResult<String> doPartialParse(final String text) {
            if (text.startsWith(KEY_VALUE_SERAPATOR)) {
                return new ParseResult<String>(KEY_VALUE_SERAPATOR, 1);
            }
            throw new IllegalArgumentException();
        }
    }
}