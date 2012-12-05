package org.alljson.types;

public enum JsonNull implements JsonPrimitive<Void> {
    INSTANCE;

    @Override
    public Void getValue() {
        return null;
    }

    @Override
    public String toString() {
        return "null";
    }
}
