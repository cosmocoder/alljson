package org.alljson.internal;

import java.lang.annotation.Annotation;

public interface AnnotationProvider {
    Annotation getAnnotationOfType(Class<Annotation> type);
}
