package com.mantledillusion.data.epiphy.context.io;

import com.mantledillusion.data.epiphy.context.Context;
import com.mantledillusion.data.epiphy.exception.InterruptedPropertyPathException;
import com.mantledillusion.data.epiphy.Property;
import com.mantledillusion.data.epiphy.io.Getter;
import com.mantledillusion.data.epiphy.io.ReferencedGetter;

public class ObjectReferencedGetter<O, V> implements ReferencedGetter<O, V> {

    private final Getter<O, V> getter;

    private ObjectReferencedGetter(Getter<O, V> getter) {
        this.getter = getter;
    }

    @Override
    public V get(Property<O, V> property, O instance, Context context, boolean allowNull) {
        if (instance == null) {
            if (allowNull) {
                return null;
            } else {
                throw new InterruptedPropertyPathException(property);
            }
        } else {
            return this.getter.get(instance);
        }
    }

    public static <O, V> ObjectReferencedGetter<O, V> from(Getter<O, V> getter) {
        if (getter == null) {
            throw new IllegalArgumentException("Cannot create a property from a null getter");
        }
        return new ObjectReferencedGetter<>(getter);
    }
}
