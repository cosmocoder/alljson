package org.alljson.types;

public class JsonBoolean implements JsonPrimitive<Boolean> {

    private final boolean value;

    public JsonBoolean(boolean value) {
        this.value = value;
    }

    @Override
    public Boolean getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
