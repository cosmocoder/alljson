package org.alljson.types;

import static com.google.common.collect.Lists.newArrayList;

public final class JsonBoolean extends JsonPrimitive {
    public static final JsonBoolean TRUE = new JsonBoolean(true);
    public static final JsonBoolean FALSE = new JsonBoolean(false);
    static final JsonBooleanParser PARSER = new JsonBooleanParser();

    private final boolean value;

    private JsonBoolean(boolean value) {
        this.value = value;
    }

    @Override
    public Boolean getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public void appendStringTo(final StringBuilder stringBuilder) {
        stringBuilder.append(this.toString());
    }

    public static Iterable<JsonBoolean> getDomain() {
        return newArrayList(TRUE, FALSE);
    }

    public static JsonBoolean create(boolean value) {
        return value ? TRUE : FALSE;
    }

    public static JsonBoolean parse(String text) {
        return PARSER.parse(text);
    }

    static final class JsonBooleanParser extends AbstractDomainParser<JsonBoolean> {
        @Override
        Iterable<JsonBoolean> getDomain() {
            return JsonBoolean.getDomain();
        }
    }
}
