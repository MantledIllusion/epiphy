package com.mantledillusion.data.epiphy.context.io;

import com.mantledillusion.data.epiphy.Property;
import com.mantledillusion.data.epiphy.context.Context;
import com.mantledillusion.data.epiphy.context.reference.PropertyKey;
import com.mantledillusion.data.epiphy.exception.InterruptedPropertyPathException;
import com.mantledillusion.data.epiphy.exception.OutboundPropertyPathException;
import com.mantledillusion.data.epiphy.exception.UnreferencedPropertyPathException;

import java.util.Map;

public class MapReferencedSetter<K, V> implements ReferencedSetter<Map<K, V>, V> {

    @Override
    public void set(Property<Map<K, V>, V> property, Map<K, V> object, V value, Context context) {
        if (!context.containsReference(property, PropertyKey.class)) {
            throw new UnreferencedPropertyPathException(property);
        } else if (object == null) {
            throw new InterruptedPropertyPathException(property);
        } else {
            PropertyKey<K> reference = context.getReference(property);
            K key = reference.getReference();
            if (!object.containsKey(key)) {
                throw new OutboundPropertyPathException(property, reference);
            } else {
                object.put(key, value);
            }
        }
    }

    @Override
    public boolean isWritable() {
        return true;
    }

    public static <K, V> MapReferencedSetter<K, V> from() {
        return new MapReferencedSetter<>();
    }
}
