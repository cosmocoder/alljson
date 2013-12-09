package org.alljson.internal;

import java.lang.reflect.Type;

public interface PropertyReader extends AnnotationProvider {
    Object getValueFrom(Object object);
    Type getPropertyType();
}