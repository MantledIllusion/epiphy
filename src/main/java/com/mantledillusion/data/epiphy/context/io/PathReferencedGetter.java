package com.mantledillusion.data.epiphy.context.io;

import com.mantledillusion.data.epiphy.Property;
import com.mantledillusion.data.epiphy.context.Context;
import com.mantledillusion.data.epiphy.io.ReferencedGetter;

public class PathReferencedGetter<S, O, V> implements ReferencedGetter<S, V> {

    private final Property<S, O> parent;
    private final Property<O, V> child;
    private final ReferencedGetter<O, V> getter;

    private PathReferencedGetter(Property<S, O> parent, Property<O, V> child, ReferencedGetter<O, V> getter) {
        this.parent = parent;
        this.child = child;
        this.getter = getter;
    }

    @Override
    public V get(Property<S, V> property, S instance, Context context, boolean allowNull) {
        return this.getter.get(this.child, this.parent.get(instance, context, allowNull), context, allowNull);
    }

    public static <S, O, V> PathReferencedGetter<S, O, V> from(Property<S, O> parent, Property<O, V> child, ReferencedGetter<O, V> getter) {
        return new PathReferencedGetter<>(parent, child, getter);
    }
}
