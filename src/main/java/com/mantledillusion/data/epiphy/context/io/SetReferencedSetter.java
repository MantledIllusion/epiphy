package com.mantledillusion.data.epiphy.context.io;

import com.mantledillusion.data.epiphy.Property;
import com.mantledillusion.data.epiphy.context.Context;
import com.mantledillusion.data.epiphy.context.reference.PropertyKey;
import com.mantledillusion.data.epiphy.exception.InterruptedPropertyPathException;
import com.mantledillusion.data.epiphy.exception.OutboundPropertyPathException;
import com.mantledillusion.data.epiphy.exception.UnreferencedPropertyPathException;

import java.util.Set;

public class SetReferencedSetter<E> implements ReferencedSetter<Set<E>, E> {

    private SetReferencedSetter() {};

    @Override
    public void set(Property<Set<E>, E> property, Set<E> object, E value, Context context) {
        if (!context.containsReference(property, PropertyKey.class)) {
            throw new UnreferencedPropertyPathException(property);
        } else if (object == null) {
            throw new InterruptedPropertyPathException(property);
        } else {
            PropertyKey<E> reference = context.getReference(property);
            E element = reference.getReference();
            if (!object.contains(element)) {
                throw new OutboundPropertyPathException(property, reference);
            } else {
                object.remove(element);
                object.add(value);
            }
        }
    }

    @Override
    public boolean isWritable() {
        return true;
    }

    public static <E> SetReferencedSetter<E> from() {
        return new SetReferencedSetter<>();
    }
}
