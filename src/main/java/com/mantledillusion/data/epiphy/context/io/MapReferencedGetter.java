package com.mantledillusion.data.epiphy.context.io;

import com.mantledillusion.data.epiphy.Property;
import com.mantledillusion.data.epiphy.context.Context;
import com.mantledillusion.data.epiphy.context.reference.PropertyKey;
import com.mantledillusion.data.epiphy.exception.InterruptedPropertyPathException;
import com.mantledillusion.data.epiphy.exception.OutboundPropertyPathException;
import com.mantledillusion.data.epiphy.exception.UnreferencedPropertyPathException;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MapReferencedGetter<K, V> implements ReferencedGetter<Map<K, V>, V> {

    private Set<Property<?, ?>> hierarchy;

    private MapReferencedGetter() {}

    @Override
    public V get(Property<Map<K, V>, V> property, Map<K, V> object, Context context, boolean allowNull) {
        if (!context.containsReference(property, PropertyKey.class)) {
            throw new UnreferencedPropertyPathException(property);
        } else if (object == null) {
            if (allowNull) {
                return null;
            } else {
                throw new InterruptedPropertyPathException(property);
            }
        } else {
            PropertyKey<K> reference = context.getReference(property);
            K key = reference.getReference();
            if (!object.containsKey(key)) {
                throw new OutboundPropertyPathException(property, reference);
            } else {
                return object.get(key);
            }
        }
    }

    @Override
    public Property<?, ?> getParent() {
        return null;
    }

    @Override
    public int occurrences(Property<Map<K, V>, V> property, Map<K, V> object) {
        return object.size();
    }

    @Override
    public Collection<Context> contextualize(Property<Map<K, V>, V> property, Map<K, V> object, Context context, boolean includeNull) {
        K key = context.containsReference(property, PropertyKey.class) ?
                ((PropertyKey<K>) context.getReference(property, PropertyKey.class)).getReference() : null;
        return (key != null ? Stream.of(key) : object.keySet().stream()).
                filter(k -> includeNull || object.get(k) != null).
                map(k -> context.union(PropertyKey.ofMap(property, k))).
                collect(Collectors.toSet());
    }

    @Override
    public Collection<Context> contextualize(Property<Map<K, V>, V> property, Map<K, V> object, V value, Context context) {
        K key = context.containsReference(property, PropertyKey.class) ?
                ((PropertyKey<K>) context.getReference(property, PropertyKey.class)).getReference() : null;
        return (key != null ? Stream.of(key) : object.keySet().stream()).
                filter(k -> Objects.equals(object.get(k), value)).
                map(k -> context.union(PropertyKey.ofMap(property, k))).
                collect(Collectors.toSet());
    }

    @Override
    public Set<Property<?, ?>> getHierarchy(Property<Map<K, V>, V> property) {
        if (this.hierarchy == null) {
            this.hierarchy = Collections.singleton(property);
        }
        return this.hierarchy;
    }

    public static <K, V> MapReferencedGetter<K, V> from() {
        return new MapReferencedGetter<>();
    }
}
