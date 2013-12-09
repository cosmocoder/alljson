package org.alljson.types;

import org.alljson.internal.ParseResult;

final class SeparatorParser extends AbstractParser<String> {
    static final SeparatorParser INSTANCE = new SeparatorParser();

    @Override
    public ParseResult<String> doPartialParse(final String text) {
        if (text.length() > 0) {
            return new ParseResult<String>(text.substring(0, 1), 1);
        }
        throw new IllegalArgumentException();
    }
}
