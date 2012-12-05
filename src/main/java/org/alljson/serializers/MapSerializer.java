package org.alljson.serializers;

import org.alljson.types.JsonObject;
import org.alljson.types.JsonString;
import org.alljson.types.JsonValue;

import java.util.LinkedHashMap;
import java.util.Map;

public class MapSerializer<K, V> extends AbstractSerializer<Map<K, V>> {

    @Override
    public JsonObject serializeNotNullValue(Map<K, V> map, SerializationContext context) {
        Map<JsonString, JsonValue> outputMap = new LinkedHashMap<JsonString, JsonValue>();

        for (Map.Entry<K, V> entry : map.entrySet()) {
            JsonValue key = context.serialize(entry.getKey());
            JsonString keyString;
            if(key instanceof JsonString) {
                keyString = (JsonString) key;
            } else {
                keyString = new JsonString(String.valueOf(key));
            }

            if (!context.isIgnoreNullProperties()) {
                JsonValue value = context.serialize(entry.getValue());
                outputMap.put(keyString, value);
            }
        }

        return new JsonObject(outputMap);
    }
}
