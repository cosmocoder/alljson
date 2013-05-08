package org.alljson.types;

public class JsonBoolean implements JsonPrimitive {

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

    @Override
    public void appendStringTo(final StringBuilder stringBuilder) {
        stringBuilder.append(this.toString());
    }
}
