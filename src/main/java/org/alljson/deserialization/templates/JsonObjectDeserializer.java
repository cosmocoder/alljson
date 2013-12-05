package org.alljson.deserialization.templates;

import com.google.common.base.Throwables;
import org.alljson.internal.Accessors;
import org.alljson.internal.PropertyWriter;
import org.alljson.templates.Converter;
import org.alljson.types.JsonObject;
import org.alljson.types.JsonString;
import org.alljson.types.JsonValue;

public class JsonObjectDeserializer extends NullableDeserializer<JsonObject,Object> {
    protected JsonObjectDeserializer(final Class<JsonObject> inputClass, final Class<Object> outputClass) {
        super(inputClass, outputClass);
    }

    @Override
    protected Object convertNotNullValue(final JsonObject input, Class<Object> outputClass, final Converter masterConverter) {
        try {
            Object output = newInstanceOf(outputClass);
            for (JsonString jsonKey : input.keySet()) {
                final String propertyName = jsonKey.getValue();
                PropertyWriter propertyWriter = Accessors.writerFor(output.getClass(), propertyName);
                final JsonValue propertyValueAsJson = input.get(jsonKey);
                final Class<?> propertyClass = propertyWriter.getPropertyClass();
                final Object propertyValue = masterConverter.convert(propertyValueAsJson, propertyClass, masterConverter);
                propertyWriter.setValueTo(output, propertyValue);
            }
            return output;
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
