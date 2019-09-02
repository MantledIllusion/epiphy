package com.mantledillusion.data.epiphy.context.io;

import com.mantledillusion.data.epiphy.Property;
import com.mantledillusion.data.epiphy.context.Context;
import com.mantledillusion.data.epiphy.context.DefaultContext;
import com.mantledillusion.data.epiphy.io.ReferencedGetter;

import java.util.*;

public class SelfReferencedGetter<V> implements ReferencedGetter<V, V> {

    private SelfReferencedGetter() {};

    @Override
    public V get(Property<V, V> property, V object, Context context, boolean allowNull) {
        return object;
    }

    @Override
    public Collection<Context> contextualize(Property<V, V> property, V object) {
        return Collections.singleton(DefaultContext.EMPTY);
    }

    @Override
    public SortedSet<Property<?, ?>> getHierarchy(Property<V, V> property) {
        return Collections.emptySortedSet();
    }

    public static <V>  SelfReferencedGetter<V> from() {
        return new SelfReferencedGetter();
    }
}
