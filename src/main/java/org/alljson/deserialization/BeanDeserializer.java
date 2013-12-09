package org.alljson.deserialization;

import com.google.common.base.Throwables;
import org.alljson.deserialization.templates.NullableDeserializer;
import org.alljson.internal.Accessors;
import org.alljson.internal.Classes;
import org.alljson.internal.PropertyWriter;
import org.alljson.templates.Converter;
import org.alljson.types.JsonObject;
import org.alljson.types.JsonString;
import org.alljson.types.JsonValue;

import java.lang.reflect.Type;

public class BeanDeserializer extends NullableDeserializer<JsonObject,Object> {
    protected BeanDeserializer(final Class<JsonObject> inputClass, final Class<Object> outputClass) {
        super(inputClass, outputClass);
    }

    @Override
    protected Object convertNotNullValue(final JsonObject input, Type outputType, final Converter masterConverter) {
        try {
            Object output = newInstanceOf(Classes.ofType(outputType));
            for (JsonString jsonKey : input.keySet()) {
                final String propertyName = jsonKey.getValue();
                PropertyWriter propertyWriter = Accessors.writerFor(output.getClass(), propertyName);
                final JsonValue propertyValueAsJson = input.get(jsonKey);
                final Type propertyType = propertyWriter.getPropertyType();
                final Object propertyValue = masterConverter.convert(propertyValueAsJson, propertyType, masterConverter);
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
