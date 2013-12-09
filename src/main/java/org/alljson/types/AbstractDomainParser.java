package org.alljson.types;

import org.alljson.internal.ParseResult;

abstract class AbstractDomainParser<T> extends AbstractParser<T> {

    private final Class domainClass = getDomain().iterator().next().getClass();

    public ParseResult<T> doPartialParse(String text) {
        for (T json : getDomain()) {
            final String jsonText = json.toString();
            if(text.startsWith(jsonText)) {
                return new ParseResult<T>(json, jsonText.length());
            }
        }
        throw new IllegalArgumentException(String.format("Can't parse %s from string = \"%s\"", domainClass.getSimpleName(), text));
    }

    abstract Iterable<T> getDomain();
}
