package com.mantledillusion.data.epiphy.context.io;

import com.mantledillusion.data.epiphy.Property;
import com.mantledillusion.data.epiphy.context.Context;
import com.mantledillusion.data.epiphy.exception.InterruptedPropertyPathException;
import com.mantledillusion.data.epiphy.io.ReferencedSetter;
import com.mantledillusion.data.epiphy.io.Setter;

public class ObjectReferencedSetter<O, V> implements ReferencedSetter<O, V> {

    private final Setter<O, V> setter;

    private ObjectReferencedSetter(Setter<O, V> setter) {
        this.setter = setter;
    }

    @Override
    public void set(Property<O, V> property, O object, V value, Context context) {
        if (object == null) {
            throw new InterruptedPropertyPathException(property);
        }
        this.setter.set(object, value);
    }

    public static <O, V> ObjectReferencedSetter<O, V> from(Setter<O, V> setter) {
        if (setter == null) {
            throw new IllegalArgumentException("Cannot create a property from a null setter");
        }
        return new ObjectReferencedSetter<>(setter);
    }
}
