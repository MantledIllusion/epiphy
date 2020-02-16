package com.mantledillusion.data.epiphy.context.io;

import com.mantledillusion.data.epiphy.context.Context;
import com.mantledillusion.data.epiphy.exception.InterruptedPropertyPathException;
import com.mantledillusion.data.epiphy.Property;
import com.mantledillusion.data.epiphy.Getter;

import java.util.*;

public class ObjectReferencedGetter<O, V> implements ReferencedGetter<O, V> {

    private final Getter<O, V> getter;

    private Set<Property<?, ?>> hierarchy;

    private ObjectReferencedGetter(Getter<O, V> getter) {
        this.getter = getter;
    }

    @Override
    public V get(Property<O, V> property, O object, Context context, boolean allowNull) {
        if (object == null) {
            if (allowNull) {
                return null;
            } else {
                throw new InterruptedPropertyPathException(property);
            }
        } else {
            return this.getter.get(object);
        }
    }

    @Override
    public int occurrences(Property<O, V> property, O object) {
        return property.isNull(object) ? 0 : 1;
    }

    @Override
    public Collection<Context> contextualize(Property<O, V> property, O object, Context context, boolean includeNull) {
        return !property.isNull(object, context) || (includeNull && property.exists(object, context)) ?
                Collections.singleton(context) : Collections.emptySet();
    }

    @Override
    public Collection<Context> contextualize(Property<O, V> property, O object, V value, Context context) {
        return property.exists(object, context) && Objects.equals(this.getter.get(object), value) ?
                Collections.singleton(context) : Collections.emptySet();
    }

    @Override
    public Property<?, ?> getParent() {
        return null;
    }

    @Override
    public Set<Property<?, ?>> getHierarchy(Property<O, V> property) {
        if (this.hierarchy == null) {
            this.hierarchy = Collections.singleton(property);
        }
        return this.hierarchy;
    }

    public static <O, V> ObjectReferencedGetter<O, V> from(Getter<O, V> getter) {
        if (getter == null) {
            throw new IllegalArgumentException("Cannot create a property from a null getter");
        }
        return new ObjectReferencedGetter<>(getter);
    }
}
