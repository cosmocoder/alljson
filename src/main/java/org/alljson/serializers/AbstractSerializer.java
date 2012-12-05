package org.alljson.serializers;

import com.google.common.base.Objects;
import org.alljson.types.JsonNull;
import org.alljson.types.JsonValue;

public abstract class AbstractSerializer<T> implements Serializer<T> {
    @Override
    public JsonValue serialize(T input, SerializationContext context) {
        return (input == null)? JsonNull.INSTANCE : serializeNotNullValue(input, context);
    }

    public abstract JsonValue serializeNotNullValue(T input, SerializationContext context);

    public int hashCode() {
        return Objects.hashCode(this.getClass());
    }
}
