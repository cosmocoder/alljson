package org.alljson.types;

public interface JsonPrimitive<T> extends JsonValue {
     T getValue();
}
