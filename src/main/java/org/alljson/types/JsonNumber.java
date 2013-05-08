package org.alljson.types;

public class JsonNumber implements JsonPrimitive {
    private final Number number;

    public JsonNumber(Number number) {
        this.number = number;
    }

    @Override
    public Number getValue() {
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
}
