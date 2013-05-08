package org.alljson.serialization.adapters;

import org.alljson.templates.Converter;
import org.alljson.templates.NullableConverter;
import org.alljson.internal.FieldAccessor;
import org.alljson.internal.Getter;
import org.alljson.internal.PropertyReader;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import static com.google.common.collect.Sets.newHashSet;

public class BeanToMapAdapter extends NullableConverter<Object,Map<String,Object>> {

    public BeanToMapAdapter() {
        super(Object.class, (Class) Map.class);
    }

    @Override
    public Map<String, Object> convertNotNullValue(Object bean, final Converter masterAdapter) {
        Map<String, Getter> getters = getters(bean, newHashSet("class"));
        Map<String, FieldAccessor> fields = fields(bean, getters.keySet());
        Map<String, PropertyReader> properties = new LinkedHashMap<String, PropertyReader>();
        properties.putAll(getters);
        properties.putAll(fields);
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        for (Map.Entry<String, PropertyReader> property : properties.entrySet()) {
            map.put(property.getKey(), property.getValue().getValueFrom(bean));
        }
        return map;
    }

    private Map<String, Getter> getters(Object bean, Set<String> ignore) {
        Map<String, Getter> getters = new LinkedHashMap<String, Getter>();
        Method[] methods = bean.getClass().getMethods();
        for (Method method : methods) {
            String methodName = method.getName();
            String propertyName = propertyName(methodName, "get");
            Class<?> returnType = method.getReturnType();
            if (propertyName == null && (returnType.isAssignableFrom(boolean.class) || returnType.isAssignableFrom(Boolean.class))) {
                propertyName = propertyName(methodName, "is");
            }
            if (propertyName != null && !ignore.contains(propertyName)) {
                getters.put(propertyName, new Getter(method));
            }
        }
        return getters;
    }

    private Map<String, FieldAccessor> fields(Object bean, Set<String> ignore) {
        Map<String, FieldAccessor> fields = new LinkedHashMap<String, FieldAccessor>();
        for (Field field : bean.getClass().getFields()) {
            String name = field.getName();
            if (!ignore.contains(name)) {
                fields.put(name, new FieldAccessor(field));
            }
        }
        return fields;
    }

    private String propertyName(String methodName, String preffix) {
        int preffixLength = preffix.length();
        if (methodName.length() > preffixLength && methodName.startsWith(preffix)) {
            String firstLetter = methodName.substring(preffixLength, preffixLength + 1);
            return firstLetter.toLowerCase() + methodName.substring(preffixLength + 1);
        }
        return null;
    }
}
