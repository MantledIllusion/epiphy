package com.mantledillusion.data.epiphy.context.io;

import com.mantledillusion.data.epiphy.Property;
import com.mantledillusion.data.epiphy.context.Context;
import com.mantledillusion.data.epiphy.context.reference.PropertyIndex;
import com.mantledillusion.data.epiphy.exception.InterruptedPropertyPathException;
import com.mantledillusion.data.epiphy.exception.OutboundPropertyPathException;
import com.mantledillusion.data.epiphy.exception.UnreferencedPropertyPathException;
import com.mantledillusion.data.epiphy.io.ReferencedGetter;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ListReferencedGetter<E> implements ReferencedGetter<List<E>, E> {

    private Set<Property<?, ?>> hierarchy;

    private ListReferencedGetter() {}

    @Override
    public E get(Property<List<E>, E> property, List<E> object, Context context, boolean allowNull) {
        if (!context.containsReference(property, PropertyIndex.class)) {
            throw new UnreferencedPropertyPathException(property);
        } else if (object == null) {
            if (allowNull) {
                return null;
            } else {
                throw new InterruptedPropertyPathException(property);
            }
        } else {
            PropertyIndex reference = context.getReference(property);
            int index = reference.getReference();
            if (index < 0 || index >= object.size()) {
                throw new OutboundPropertyPathException(property, reference);
            } else {
                return object.get(index);
            }
        }
    }

    @Override
    public int occurrences(Property<List<E>, E> property, List<E> object) {
        return object.size();
    }

    @Override
    public Collection<Context> contextualize(Property<List<E>, E> property, List<E> object) {
        return IntStream.range(0, object.size()).
                mapToObj(index -> Context.of(PropertyIndex.of(property, index))).
                collect(Collectors.toSet());
    }

    @Override
    public Set<Property<?, ?>> getHierarchy(Property<List<E>, E> property) {
        if (this.hierarchy == null) {
            this.hierarchy = Collections.singleton(property);
        }
        return this.hierarchy;
    }

    public static <E> ListReferencedGetter<E> from() {
        return new ListReferencedGetter<>();
    }
}
