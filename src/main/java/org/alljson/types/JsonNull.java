package org.alljson.types;

import com.google.common.base.CharMatcher;

import static com.google.common.collect.Lists.newArrayList;

public final class JsonNull extends JsonPrimitive {
    public static final JsonNull INSTANCE = new JsonNull();
    public static final String NULL_STRING = "null";
    static final JsonNullParser PARSER = new JsonNullParser();

    @Override
    public Void getValue() {
        return null;
    }

    @Override
    public String toString() {
        return "null";
    }

    @Override
    public void appendStringTo(final StringBuilder stringBuilder) {
        stringBuilder.append(this.toString());
    }

    public static JsonNull fromJsonString(String string) {
        final String trimmedString = CharMatcher.WHITESPACE.trimFrom(string);
        if(trimmedString.equals(NULL_STRING)) {
            return INSTANCE;
        } else {
            throw new IllegalArgumentException(String.format("Can't parse JsonNull from string = \"%s\"", trimmedString));
        }
    }

    public static JsonNull parse(String text) {
        return JsonNull.PARSER.parse(text);
    }

    static class JsonNullParser extends AbstractDomainParser<JsonNull> {

        @Override
        Iterable<JsonNull> getDomain() {
            return newArrayList(INSTANCE);
        }
    }
}
