package org.alljson.types;

import com.google.common.base.CharMatcher;
import org.alljson.internal.ParseResult;
import org.alljson.types.JsonBoolean;

abstract class AbstractParser<T> implements Parser<T> {
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
