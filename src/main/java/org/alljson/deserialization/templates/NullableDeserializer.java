package org.alljson.deserialization.templates;

import org.alljson.templates.Converter;
import org.alljson.templates.NullableDirectedConverter;
import org.alljson.types.JsonNull;
import org.alljson.types.JsonValue;

import java.lang.reflect.Type;

public abstract class NullableDeserializer<I extends JsonValue,O> extends NullableDirectedConverter<I,O> {

    protected NullableDeserializer(final Class<I> inputClass, final Class<O> outputClass) {
        super(inputClass, outputClass);
    }

    /* Overrides the internal convert handling JsonNulls */
    @Override
    public O convert(I input, Type outputType, Converter masterConverter) {
        return JsonNull.INSTANCE.equals(input) || null == input? null : convertNotNullValue(input, outputType, masterConverter);
    }

}
