package org.alljson.types;

import com.google.common.base.Objects;
import org.alljson.internal.ParseResult;

public abstract class JsonPrimitive extends JsonValue {
    public abstract Object getValue();
    static final JsonPrimitiveParser PARSER = new JsonPrimitiveParser();

    @Override
    public boolean equals(final Object o) {
        if(o.getClass().equals(this.getClass())) {
            return Objects.equal(getValue(), ((JsonPrimitive) o).getValue());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getValue());
    }

    static final class JsonPrimitiveParser extends AbstractParser<JsonPrimitive> {

        public static final JsonPrimitiveParser INSTANCE = new JsonPrimitiveParser();

        @Override
        public ParseResult<JsonPrimitive> doPartialParse(final String text) {
            ParseResult<JsonPrimitive> result = parseIgnoringErrors(new JsonString.JsonStringParser(), text);
            if (result != null) return result;

            result = parseIgnoringErrors(new JsonBoolean.JsonBooleanParser(), text);
            if (result != null) return result;

            result = parseIgnoringErrors(new JsonNull.JsonNullParser(), text);
            if (result != null) return result;

            result = parseIgnoringErrors(new JsonNumber.JsonNumberParser(), text);
            if (result != null) return result;

            throw new IllegalArgumentException(String.format("Can't parse JsonPrimitive from text = \"%s\"", text));
        }

        private <T extends JsonPrimitive> ParseResult<JsonPrimitive> parseIgnoringErrors(AbstractParser<T> parser, String text) {
            try {
                final ParseResult<T> result = parser.doPartialParse(text);
                JsonPrimitive json = result.getParsedValue();
                int nextPosition = result.getNextPosition();
                return new ParseResult<JsonPrimitive>(json, nextPosition);
            } catch (IllegalArgumentException e) {
                return null;
            }
        }
    }
}
