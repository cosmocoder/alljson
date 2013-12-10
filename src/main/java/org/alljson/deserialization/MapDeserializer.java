package org.alljson.deserialization;

import com.google.common.base.Throwables;
import org.alljson.deserialization.templates.NullableDeserializer;
import org.alljson.templates.Converter;
import org.alljson.types.JsonObject;
import org.alljson.types.JsonString;
import org.alljson.types.JsonValue;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import java.util.Map;

public class MapDeserializer extends NullableDeserializer<JsonObject,Map<?,?>> {
    protected MapDeserializer(final Class<JsonObject> inputClass, final Class<Map<?,?>> outputClass) {
        super(inputClass, outputClass);
    }

    @Override
    protected Map<?,?> convertNotNullValue(final JsonObject input, Type outputType, final Converter masterConverter) {
        try {
            Map<Object,Object> outputMap = new LinkedHashMap<Object, Object>();

            ParameterizedType mapType = (ParameterizedType) outputType;
            for (JsonString jsonKey : input.keySet()) {
                final String propertyName = jsonKey.getValue();
                final JsonValue entryValueAsJson = input.get(jsonKey);
                final Type keyType = mapType.getActualTypeArguments()[0];
                final Type valueType = mapType.getActualTypeArguments()[1];
                final JsonValue entryKeyAsJson;
                if(keyType.equals(String.class)) {
                    entryKeyAsJson = jsonKey;
                } else {
                    entryKeyAsJson = JsonValue.parse(jsonKey.getValue());
                }
                final Object entryKey = masterConverter.convert(entryKeyAsJson, keyType, masterConverter);
                final Object entyValue = masterConverter.convert(entryValueAsJson, valueType, masterConverter);
                outputMap.put(entryKey,entyValue);
            }
            return outputMap;
        } catch (Exception e) {
            throw Throwables.propagate(e);
        }
    }

    private <T> T newInstanceOf(Class<T> clazz) {
        try {
            return clazz.newInstance();
        } catch (Exception e) {
            throw Throwables.propagate(e);
        }
    }
}
