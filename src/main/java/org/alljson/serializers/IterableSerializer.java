package org.alljson.serializers;

import org.alljson.types.JsonArray;
import org.alljson.types.JsonValue;

import java.util.ArrayList;
import java.util.List;

public class IterableSerializer<T> extends AbstractSerializer<Iterable<T>> {

    @Override
    public JsonArray serializeNotNullValue(Iterable<T> iterable, SerializationContext context) {
        List<JsonValue> outputList = new ArrayList<JsonValue>();

        for (T object : iterable) {
            outputList.add(context.serialize(object));
        }

        return new JsonArray(outputList);
    }
}
