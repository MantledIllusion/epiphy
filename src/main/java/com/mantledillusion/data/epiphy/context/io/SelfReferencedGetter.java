package com.mantledillusion.data.epiphy.context.io;

import com.mantledillusion.data.epiphy.Property;
import com.mantledillusion.data.epiphy.context.Context;
import com.mantledillusion.data.epiphy.context.TraversingMode;

import java.util.*;

public class SelfReferencedGetter<V> implements ReferencedGetter<V, V> {

    private SelfReferencedGetter() {};

    @Override
    public V get(Property<V, V> property, V object, Context context, boolean allowNull) {
        return object;
    }

    @Override
    public Property<?, ?> getParent() {
        return null;
    }

    @Override
    public Set<Property<?, ?>> getHierarchy(Property<V, V> property) {
        return Collections.emptySet();
    }

    @Override
    public int occurrences(Property<V, V> property, V object) {
        return 0;
    }

    @Override
    public Collection<Context> contextualize(Property<V, V> property, V object, Context context, TraversingMode traversingMode, boolean includeNull) {
        return Collections.singleton(context);
    }

    @Override
    public Collection<Context> contextualize(Property<V, V> property, V object, V value, Context context) {
        return Collections.singleton(context);
    }

    public static <V>  SelfReferencedGetter<V> from() {
        return new SelfReferencedGetter();
    }
}
