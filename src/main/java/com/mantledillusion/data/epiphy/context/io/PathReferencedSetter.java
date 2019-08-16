package com.mantledillusion.data.epiphy.context.io;

import com.mantledillusion.data.epiphy.Property;
import com.mantledillusion.data.epiphy.context.Context;
import com.mantledillusion.data.epiphy.exception.InterruptedPropertyPathException;
import com.mantledillusion.data.epiphy.io.ReferencedGetter;
import com.mantledillusion.data.epiphy.io.ReferencedSetter;

public class PathReferencedSetter<S, O, V> implements ReferencedSetter<S, V> {

    private final Property<S, O> parent;
    private final Property<O, V> child;
    private final ReferencedSetter<O, V> setter;

    private PathReferencedSetter(Property<S, O> parent, Property<O, V> child, ReferencedSetter<O, V> setter) {
        this.parent = parent;
        this.child = child;
        this.setter = setter;
    }

    @Override
    public void set(Property<S, V> property, S instance, V value, Context context) {
        O intermediate = this.parent.get(instance, context, false);
        if (intermediate == null) {
            throw new InterruptedPropertyPathException(this.child);
        }
        this.setter.set(this.child, intermediate, value, context);
    }

    public static <S, O, V> PathReferencedSetter<S, O, V> from(Property<S, O> parent, Property<O, V> child, ReferencedSetter<O, V> getter) {
        return new PathReferencedSetter<>(parent, child, getter);
    }
}
