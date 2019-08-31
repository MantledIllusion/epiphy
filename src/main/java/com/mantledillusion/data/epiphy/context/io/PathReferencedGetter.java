package com.mantledillusion.data.epiphy.context.io;

import com.mantledillusion.data.epiphy.Property;
import com.mantledillusion.data.epiphy.context.Context;
import com.mantledillusion.data.epiphy.io.ReferencedGetter;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

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
    public V get(Property<S, V> property, S object, Context context, boolean allowNull) {
        return this.getter.get(this.child, this.parent.get(object, context, allowNull), context, allowNull);
    }

    @Override
    public Collection<Context> contextualize(Property<S, V> property, S object) {
        return this.parent.contextualize(object).stream().
                flatMap(parentContext -> this.child.contextualize(this.parent.get(object, parentContext)).stream().
                        map(childContext -> parentContext.merge(childContext))).
                collect(Collectors.toSet());
    }

    public static <S, O, V> PathReferencedGetter<S, O, V> from(Property<S, O> parent, Property<O, V> child, ReferencedGetter<O, V> getter) {
        return new PathReferencedGetter<>(parent, child, getter);
    }
}
