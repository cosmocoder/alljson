package org.alljson;

import org.alljson.serialization.JsonSerializer;
import org.alljson.types.JsonValue;

public class JsonMapper {
    private final JsonSerializer serializer;

    private JsonMapper(final JsonSerializer serializer) {
        this.serializer = serializer;
    }

    public JsonValue getJson(Object object) {
        return serializer.convert(object, serializer);
    }

    public static JsonMapper create() {
        return new JsonMapper(JsonSerializer.create());
    }


}
