package org.alljson.internal;

import java.util.List;

public interface HandledType<T> {
    List<HandledType<?>> getParameters();
    Class<T> toClass();
    boolean isAssignableFrom(HandledType<?> other);
}
