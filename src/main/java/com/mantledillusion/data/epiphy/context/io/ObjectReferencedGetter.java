package com.mantledillusion.data.epiphy.context.io;

import com.mantledillusion.data.epiphy.context.Context;
import com.mantledillusion.data.epiphy.context.DefaultContext;
import com.mantledillusion.data.epiphy.exception.InterruptedPropertyPathException;
import com.mantledillusion.data.epiphy.Property;
import com.mantledillusion.data.epiphy.io.Getter;
import com.mantledillusion.data.epiphy.io.ReferencedGetter;

import java.util.*;

public class ObjectReferencedGetter<O, V> implements ReferencedGetter<O, V> {

    private final Getter<O, V> getter;

    private SortedSet<Property<?, ?>> hierarchy;

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
    public Collection<Context> contextualize(Property<O, V> property, O object) {
        return property.isNull(object) ? Collections.emptySet() : Collections.singleton(DefaultContext.EMPTY);
    }

    @Override
    public SortedSet<Property<?, ?>> getHierarchy(Property<O, V> property) {
        if (this.hierarchy == null) {
            this.hierarchy = Collections.unmodifiableSortedSet(new TreeSet<>(Arrays.asList(property)));
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
