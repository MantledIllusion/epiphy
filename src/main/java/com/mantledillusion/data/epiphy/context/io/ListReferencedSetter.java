package com.mantledillusion.data.epiphy.context.io;

import com.mantledillusion.data.epiphy.Property;
import com.mantledillusion.data.epiphy.context.Context;
import com.mantledillusion.data.epiphy.context.reference.PropertyIndex;
import com.mantledillusion.data.epiphy.exception.InterruptedPropertyPathException;
import com.mantledillusion.data.epiphy.exception.OutboundPropertyPathException;
import com.mantledillusion.data.epiphy.exception.UnreferencedPropertyPathException;
import com.mantledillusion.data.epiphy.io.ReferencedSetter;

import java.util.List;

public class ListReferencedSetter<E> implements ReferencedSetter<List<E>, E> {

    private ListReferencedSetter() {};

    @Override
    public void set(Property<List<E>, E> property, List<E> instance, E value, Context context) {
        if (!context.containsReference(property, PropertyIndex.class)) {
            throw new UnreferencedPropertyPathException(property);
        } else if (instance == null) {
            throw new InterruptedPropertyPathException(property);
        } else {
            PropertyIndex reference = context.getReference(property, PropertyIndex.class);
            int index = reference.getReference();
            if (index < 0 || index >= instance.size()) {
                throw new OutboundPropertyPathException(property, reference);
            } else {
                instance.set(index, value);
            }
        }
    }

    public static <E> ListReferencedSetter from() {
        return new ListReferencedSetter();
    }
}