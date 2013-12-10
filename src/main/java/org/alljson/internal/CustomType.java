package org.alljson.internal;

import com.google.common.base.Objects;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.Map;

import static com.google.common.collect.Lists.newArrayList;

public class CustomType<T> extends AbstractType<T> {

    private final Class<T> clazz;
    private final List<HandledType<?>> parameters;

    private CustomType(Class<T> clazz, List<HandledType<?>> parameters) {
        this.clazz = clazz;
        this.parameters = parameters;
    }

    @Override
    public List<HandledType<?>> getParameters() {
        return parameters;
    }

    @Override
    public Class<T> toClass() {
        return clazz;
    }

    public static <T> CustomType<T> of(Class<T> clazz, List<HandledType<? extends Object>> parameters) {
        return new CustomType<T>(clazz, parameters);
    }
}
