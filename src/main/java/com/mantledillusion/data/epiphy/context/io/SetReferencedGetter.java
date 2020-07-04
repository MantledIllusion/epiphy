package com.mantledillusion.data.epiphy.context.io;

import com.mantledillusion.data.epiphy.Property;
import com.mantledillusion.data.epiphy.context.Context;
import com.mantledillusion.data.epiphy.context.TraversingMode;
import com.mantledillusion.data.epiphy.context.reference.PropertyKey;
import com.mantledillusion.data.epiphy.exception.InterruptedPropertyPathException;
import com.mantledillusion.data.epiphy.exception.OutboundPropertyPathException;
import com.mantledillusion.data.epiphy.exception.UnreferencedPropertyPathException;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class SetReferencedGetter<E> implements ReferencedGetter<Set<E>, E> {

    private Set<Property<?, ?>> hierarchy;

    private SetReferencedGetter() {}

    @Override
    public E get(Property<Set<E>, E> property, Set<E> object, Context context, boolean allowNull) {
        if (!context.containsReference(property, PropertyKey.class)) {
            throw new UnreferencedPropertyPathException(property);
        } else if (object == null) {
            if (allowNull) {
                return null;
            } else {
                throw new InterruptedPropertyPathException(property);
            }
        } else {
            PropertyKey<E> reference = context.getReference(property);
            E element = reference.getReference();
            if (!object.contains(element)) {
                throw new OutboundPropertyPathException(property, reference);
            } else {
                return element;
            }
        }
    }

    @Override
    public Property<?, ?> getParent() {
        return null;
    }

    @Override
    public Set<Property<?, ?>> getHierarchy(Property<Set<E>, E> property) {
        if (this.hierarchy == null) {
            this.hierarchy = Collections.singleton(property);
        }
        return this.hierarchy;
    }

    @Override
    public int occurrences(Property<Set<E>, E> property, Set<E> object) {
        return object.size();
    }

    @Override
    public Collection<Context> contextualize(Property<Set<E>, E> property, Set<E> object, Context context, TraversingMode traversingMode, boolean includeNull) {
        boolean hasReference = context.containsReference(property, PropertyKey.class);
        E element = hasReference ? ((PropertyKey<E>) context.getReference(property, PropertyKey.class)).getReference() : null;
        return hasReference ? (element != null || includeNull ?
                Collections.singleton(context.union(PropertyKey.ofSet(property, element))) : Collections.emptySet()) :
                object.parallelStream().
                        filter(e -> e != null || includeNull).
                        map(e -> context.union(PropertyKey.ofSet(property, e))).
                        collect(Collectors.toSet());
    }

    @Override
    public Collection<Context> contextualize(Property<Set<E>, E> property, Set<E> object, E value, Context context) {
        boolean hasReference = context.containsReference(property, PropertyKey.class);
        E element = hasReference ? ((PropertyKey<E>) context.getReference(property, PropertyKey.class)).getReference() : null;
        return hasReference ? (Objects.equals(element, value) ?
                Collections.singleton(context.union(PropertyKey.ofSet(property, element))) : Collections.emptySet()) :
                object.parallelStream().
                        filter(e -> Objects.equals(e, value)).
                        map(e -> context.union(PropertyKey.ofSet(property, e))).
                        collect(Collectors.toSet());
    }

    public static <E> SetReferencedGetter<E> from() {
        return new SetReferencedGetter<>();
    }
}
