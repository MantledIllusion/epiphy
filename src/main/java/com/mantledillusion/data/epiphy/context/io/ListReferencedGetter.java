package com.mantledillusion.data.epiphy.context.io;

import com.mantledillusion.data.epiphy.Property;
import com.mantledillusion.data.epiphy.context.Context;
import com.mantledillusion.data.epiphy.context.reference.PropertyIndex;
import com.mantledillusion.data.epiphy.exception.InterruptedPropertyPathException;
import com.mantledillusion.data.epiphy.exception.OutboundPropertyPathException;
import com.mantledillusion.data.epiphy.exception.UnreferencedPropertyPathException;
import com.mantledillusion.data.epiphy.io.ReferencedGetter;

import java.util.List;

public class ListReferencedGetter<E> implements ReferencedGetter<List<E>, E> {

    private ListReferencedGetter() {}

    @Override
    public E get(Property<List<E>, E> property, List<E> instance, Context context, boolean allowNull) {
        if (!context.containsReference(property, PropertyIndex.class)) {
            throw new UnreferencedPropertyPathException(property);
        } else if (instance == null) {
            if (allowNull) {
                return null;
            } else {
                throw new InterruptedPropertyPathException(property);
            }
        } else {
            PropertyIndex reference = context.getReference(property, PropertyIndex.class);
            int index = reference.getReference();
            if (index < 0 || index >= instance.size()) {
                throw new OutboundPropertyPathException(property, reference);
            } else {
                return instance.get(index);
            }
        }
    }

    public static <E> ListReferencedGetter<E> from() {
        return new ListReferencedGetter<>();
    }
}
