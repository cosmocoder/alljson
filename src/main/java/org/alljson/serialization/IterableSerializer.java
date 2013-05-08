package org.alljson.serialization;

import org.alljson.serialization.templates.NullableSerializer;
import org.alljson.templates.Converter;
import org.alljson.types.JsonArray;
import org.alljson.types.JsonValue;

import java.util.ArrayList;
import java.util.List;

public class IterableSerializer<T> extends NullableSerializer<Iterable<T>> {

    public IterableSerializer() {
        super((Class) Iterable.class);
    }

    @Override
    public JsonArray convertNotNullValue(final Iterable<T> iterable, final Converter masterAdapter) {
        List<JsonValue> outputList = new ArrayList<JsonValue>();

        for (T object : iterable) {
            outputList.add((JsonValue) masterAdapter.convert(object, masterAdapter));
        }

        return new JsonArray(outputList);
    }

}
