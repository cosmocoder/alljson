package org.alljson.deserialization;

import com.google.common.base.Throwables;
import org.alljson.deserialization.templates.NullableDeserializer;
import org.alljson.deserialization.templates.SimpleDeserializer;
import org.alljson.templates.Converter;
import org.alljson.types.JsonArray;
import org.alljson.types.JsonString;
import org.alljson.types.JsonValue;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ListDeserializer extends NullableDeserializer<JsonArray,List<?>> {

    protected ListDeserializer(final Class<JsonArray> inputClass, final Class<List<?>> outputClass) {
        super(inputClass, outputClass);
    }

    @Override
    protected List<?> convertNotNullValue(final JsonArray jsonArray, final Type outputType, final Converter masterConverter) {
        try {
            List<Object> outputList = new ArrayList<Object>(jsonArray.size());

            ParameterizedType listType = (ParameterizedType) outputType;
            for (JsonValue json : jsonArray) {
                final Type valueType = listType.getActualTypeArguments()[1];
                final Object entyValue = masterConverter.convert(json, valueType, masterConverter);
                outputList.add(entyValue);
            }
            return outputList;
        } catch (Exception e) {
            throw Throwables.propagate(e);
        }
    }
}
