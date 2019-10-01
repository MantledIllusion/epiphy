package com.mantledillusion.data.epiphy.context.io;

import com.mantledillusion.data.epiphy.Property;
import com.mantledillusion.data.epiphy.context.Context;
import com.mantledillusion.data.epiphy.context.reference.PropertyIndex;
import com.mantledillusion.data.epiphy.exception.InterruptedPropertyPathException;
import com.mantledillusion.data.epiphy.exception.OutboundPropertyPathException;
import com.mantledillusion.data.epiphy.exception.UnreferencedPropertyPathException;

import java.util.List;

public class ListReferencedSetter<E> implements ReferencedSetter<List<E>, E> {

    private ListReferencedSetter() {};

    @Override
    public void set(Property<List<E>, E> property, List<E> object, E value, Context context) {
        if (!context.containsReference(property, PropertyIndex.class)) {
            throw new UnreferencedPropertyPathException(property);
        } else if (object == null) {
            throw new InterruptedPropertyPathException(property);
        } else {
            PropertyIndex reference = context.getReference(property);
            int index = reference.getReference();
            if (index < 0 || index >= object.size()) {
                throw new OutboundPropertyPathException(property, reference);
            } else {
                object.set(index, value);
            }
        }
    }

    @Override
    public boolean isWritable() {
        return true;
    }

    public static <E> ListReferencedSetter from() {
        return new ListReferencedSetter();
    }
}
