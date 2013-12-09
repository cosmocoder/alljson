package org.alljson.types;

import com.google.common.base.CharMatcher;

import java.math.BigDecimal;
import java.math.BigInteger;

public class JsonNumber extends JsonPrimitive {
    private final BigDecimal number;
    static final JsonNumberParser PARSER = new JsonNumberParser();

    private JsonNumber(int number) {
        this.number = new BigDecimal(number);
    }

    private JsonNumber(long number) {
        this.number = new BigDecimal(number);
    }

    private JsonNumber(double number) {
        this.number = new BigDecimal(number);
    }

    private JsonNumber(BigDecimal number) {
        this.number = number;
    }

    private JsonNumber(BigInteger number) {
        this.number = new BigDecimal(number);
    }

    private JsonNumber(Number number) {
        this.number = new BigDecimal(number.doubleValue());
    }

    public static JsonNumber create (Number number) {
        if(number instanceof Integer || number instanceof Short || number instanceof Byte) {
            return new JsonNumber(number.intValue());
        }

        if(number instanceof Long) {
            return new JsonNumber(number.longValue());
        }

        if(number instanceof Double || number instanceof Float) {
            return new JsonNumber(number.doubleValue());
        }

        if(number instanceof BigDecimal) {
            return new JsonNumber((BigDecimal) number);
        }

        if(number instanceof BigInteger) {
            return new JsonNumber((BigInteger) number);
        }

        return new JsonNumber(number.doubleValue());
    }

    @Override
    public BigDecimal getValue() {
        return number;
    }

    @Override
    public String toString() {
        return number.toString();
    }

    @Override
    public void appendStringTo(final StringBuilder stringBuilder) {
        stringBuilder.append(this.toString());
    }

    public static JsonNumber parse(String string) {
        final String trimmedString = CharMatcher.WHITESPACE.trimFrom(string);
        try {
            return new JsonNumber(new BigDecimal(trimmedString));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(String.format("Can't parse JsonNumber from string = \"%s\"", trimmedString));
        }
    }

    static final class JsonNumberParser extends AbstractParser<JsonNumber> {
        @Override
        public ParseResult<JsonNumber> doPartialParse(final String text) {
            int next = CharMatcher.DIGIT.or(CharMatcher.anyOf(".eE-+")).negate().indexIn(text);
            if (next == -1) {
                next = text.length();
            }
            JsonNumber json = exactParse(text.substring(0, next));
            return new ParseResult<JsonNumber>(json, next);
        }

        public JsonNumber exactParse(final String text) {
            try {
                BigDecimal number = new BigDecimal(text);
                return new JsonNumber(number);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException(String.format("Can't parse JsonString from text = \"%s\"", text), e);
            }
        }
    }
}
