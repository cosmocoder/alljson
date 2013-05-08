package org.alljson.deserialization.templates;

import org.alljson.types.JsonObject;

public class JsonObjectDeserializer extends SimpleDeserializer<JsonObject,Object> {
    protected JsonObjectDeserializer(final Class<JsonObject> inputClass, final Class<Object> outputClass) {
        super(inputClass, outputClass);
    }

    @Override
    protected Object convertNotNullValue(final JsonObject input) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
