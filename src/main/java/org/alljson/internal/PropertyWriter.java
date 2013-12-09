package org.alljson.internal;

import java.lang.reflect.Type;

public interface PropertyWriter extends AnnotationProvider{
    void setValueTo(Object object, Object value);
    Type getPropertyType();
}
