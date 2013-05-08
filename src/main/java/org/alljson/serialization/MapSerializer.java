package org.alljson.serialization;

import org.alljson.serialization.templates.NullableSerializer;
import org.alljson.templates.Converter;
import org.alljson.types.JsonObject;
import org.alljson.types.JsonPrimitive;
import org.alljson.types.JsonString;
import org.alljson.types.JsonValue;

import java.util.LinkedHashMap;
import java.util.Map;

public class MapSerializer<K, V> extends NullableSerializer<Map<K, V>> {

    public MapSerializer() {
        super((Class) Map.class);
    }

    @Override
    public JsonObject convertNotNullValue(Map<K, V> map, Converter masterConverter) {
        Map<JsonString, JsonValue> outputMap = new LinkedHashMap<JsonString, JsonValue>();

        for (Map.Entry<K, V> entry : map.entrySet()) {
            JsonValue key = (JsonValue) masterConverter.convert(entry.getKey(), masterConverter);
            JsonString keyString;
            if (key instanceof JsonString) {
                keyString = (JsonString) key;
            } else {
                keyString = new JsonString(String.valueOf(key));
            }

            JsonValue value = (JsonValue) masterConverter.convert(entry.getValue(), masterConverter);

            outputMap.put(keyString, value);
        }

        return new JsonObject(outputMap);
    }
}
