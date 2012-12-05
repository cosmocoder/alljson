package org.alljson.adapters;

import com.google.common.collect.Multimap;

import java.util.Collection;
import java.util.Map;

public class MultimapAdapter<K,V> extends AbstractTypeAdapter<Multimap<K,V>> {
    @Override
    public Map<K,Collection<V>> adaptNotNullValue(final Multimap<K, V> multimap) {
        return multimap.asMap();
    }
}
