package org.alljson.internal;

public interface PropertyReader extends AnnotationProvider {
    Object getValueFrom(Object object);
    Class<?> getPropertyClass();
}