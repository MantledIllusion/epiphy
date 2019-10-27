package com.mantledillusion.data.epiphy.context.io;

import com.mantledillusion.data.epiphy.Property;
import com.mantledillusion.data.epiphy.context.Context;

import java.util.*;

public class SelfReferencedGetter<V> implements ReferencedGetter<V, V> {

    private SelfReferencedGetter() {};

    @Override
    public V get(Property<V, V> property, V object, Context context, boolean allowNull) {
        return object;
    }

    @Override
    public int occurrences(Property<V, V> property, V object) {
        return 0;
    }

    @Override
    public Collection<Context> contextualize(Property<V, V> property, V object) {
        return Collections.singleton(Context.EMPTY);
    }

    @Override
    public Collection<Context> contextualize(Property<V, V> property, V object, V value, Context context) {
        return Collections.singleton(Context.EMPTY);
    }

    @Override
    public Set<Property<?, ?>> getHierarchy(Property<V, V> property) {
        return Collections.emptySet();
    }

    public static <V>  SelfReferencedGetter<V> from() {
        return new SelfReferencedGetter();
    }
}
