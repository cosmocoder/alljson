package org.alljson.types;

import org.alljson.internal.ParseResult;

public abstract class JsonValue {
    static final JsonValueParser PARSER = new JsonValueParser();
    abstract void appendStringTo(StringBuilder stringBuilder);

    public static JsonValue parse(String text) {
        return PARSER.parse(text);
    }

    static final class JsonValueParser extends AbstractParser<JsonValue> {
        @Override
        public ParseResult<JsonValue> doPartialParse(final String text) {
            ParseResult<JsonValue> result;

            result = parseIgnoringErrors(JsonPrimitive.PARSER, text);
            if (result != null) return result;

            result = parseIgnoringErrors(JsonObject.PARSER, text);
            if (result != null) return result;

            result = parseIgnoringErrors(JsonArray.PARSER, text);
            if (result != null) return result;

            throw new IllegalArgumentException(String.format("Can't parse JsonValue from text = \"%s\"", text));
        }

        private <T extends JsonValue> ParseResult<JsonValue> parseIgnoringErrors(AbstractParser<T> parser, String text) {
            try {
                final ParseResult<T> result = parser.doPartialParse(text);
                JsonValue json = result.getParsedValue();
                int nextPosition = result.getNextPosition();
                return new ParseResult<JsonValue>(json, nextPosition);
            } catch (IllegalArgumentException e) {
                return null;
            }
        }
    }
}
