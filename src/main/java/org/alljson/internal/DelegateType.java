package org.alljson.internal;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

public class DelegateType<T> extends AbstractType<T> {
    private java.lang.reflect.Type javaType;
    private DelegateType(java.lang.reflect.Type javaType) {
        this.javaType = javaType;
    }

    public static <T> DelegateType<T> of(Class<T> javaType) {
        return new DelegateType<T>(javaType);
    }

    public static DelegateType of(java.lang.reflect.Type javaType) {
        return new DelegateType(javaType);
    }

    @Override
    public List<HandledType<?>> getParameters() {
        if(javaType instanceof ParameterizedType) {
            List<HandledType<?>> parameters = new ArrayList<HandledType<?>>();
            for (java.lang.reflect.Type type : ((ParameterizedType) javaType).getActualTypeArguments()) {
                parameters.add(new DelegateType(javaType));
            }
            return parameters;
        }
        return new ArrayList<HandledType<?>>(0);
    }

    @Override
    public Class<T> toClass() {
        if(javaType instanceof Class) {  //Alfa ===> Alfa
            return (Class) javaType;
        } else if(javaType instanceof ParameterizedType) { //Alfa<T> =============> Alfa
            return (Class) ((ParameterizedType) javaType).getRawType();
        } else if(javaType instanceof GenericArrayType) {   // T[]  ===========> Object[]
            return (Class<T>) Object[].class;
        } else if(javaType instanceof TypeVariable) { //T extends Alfa & Beta & Gama   =====> Alfa
            return new DelegateType(((TypeVariable) javaType).getBounds()[0]).toClass();
        } else if(javaType instanceof WildcardType) {     //? extends Alfa =======> Alfa
            return new DelegateType(((WildcardType) javaType).getUpperBounds()[0]).toClass();
        }
        return (Class<T>) Object.class;
    }

}
