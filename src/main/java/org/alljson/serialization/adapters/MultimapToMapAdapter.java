package org.alljson.serialization.adapters;

import com.google.common.collect.Multimap;
import org.alljson.templates.Converter;
import org.alljson.templates.NullableConverter;
import org.alljson.templates.SimpleConverter;

import java.util.Collection;
import java.util.Map;

public class MultimapToMapAdapter<K,V> extends SimpleConverter<Multimap<K,V>,Map<K,Collection<V>>> {

    public MultimapToMapAdapter() {
        super((Class) Multimap.class, (Class) Map.class);
    }

    @Override
    public Map<K,Collection<V>> convertNotNullValue(final Multimap<K, V> multimap) {
        return multimap.asMap();
    }
}
