package org.alljson.internal;

public class ParseResult<T> {
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
