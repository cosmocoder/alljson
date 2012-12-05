package org.alljson.types;

public class JsonNumber implements JsonPrimitive<Number> {
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
}
