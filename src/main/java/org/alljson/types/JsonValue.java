package org.alljson.types;

import com.google.common.base.CharMatcher;

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

    static final class SeparatorParser extends AbstractParser<String> {
        static final SeparatorParser INSTANCE = new SeparatorParser();

        @Override
        public ParseResult<String> doPartialParse(final String text) {
            if (text.length() > 0) {
                return new ParseResult<String>(text.substring(0, 1), 1);
            }
            throw new IllegalArgumentException();
        }
    }

    static interface Parser<T> {
        T parse(String text);
    }

    static class ParseResult<T> {
        private final T parsedValue;
        private final int nextPosition;

        public ParseResult(final T parsedValue, final int nextPosition) {
            this.parsedValue = parsedValue;
            this.nextPosition = nextPosition;
        }

        public T getParsedValue() {
            return parsedValue;
        }

        public int getNextPosition() {
            return nextPosition;
        }
    }

    abstract static class AbstractParser<T> implements Parser<T> {
        public ParseResult<T> partialParse(String text) {
            final String trimmed = CharMatcher.WHITESPACE.trimLeadingFrom(text);
            final ParseResult<T> result = doPartialParse(trimmed);
            int trimmedOffset = text.length() - trimmed.length();
            return new ParseResult<T>(result.getParsedValue(), result.getNextPosition() + trimmedOffset);
        }

        public T parse(String text) {
            final ParseResult<T> result = partialParse(text);
            if(result.getNextPosition() != text.length()) {
                throw new IllegalArgumentException(String.format("Can't parse %s, there are undefined characters at the end of the string = \"%s\"", getClass().getSimpleName(), text));
            }
            return result.getParsedValue();
        }

        public abstract ParseResult<T> doPartialParse(String text);
    }
}
