package com.mantledillusion.data.epiphy;

import com.mantledillusion.data.epiphy.context.Context;
import com.mantledillusion.data.epiphy.context.function.DropableProperty;
import com.mantledillusion.data.epiphy.context.function.IncludableProperty;
import com.mantledillusion.data.epiphy.context.io.*;
import com.mantledillusion.data.epiphy.exception.InterruptedPropertyPathException;
import com.mantledillusion.data.epiphy.exception.UnknownDropableElementException;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Represents a {@link Property} {@link Set}.
 * <p>
 * Instantiable using the <code>from...()</code> methods.
 *
 * @param <O>
 *          The parent object type of this {@link Property}.
 * @param <E>
 *          The element type of the {@link Set} this {@link Property} represents.
 */
public class ModelPropertySet<O, E> extends AbstractModelProperty<O, Set<E>> implements
        IncludableProperty<O, Set<E>, E, E>,
        DropableProperty<O, Set<E>, E, E> {
    
    private ModelPropertySet(String id, ReferencedGetter<O, Set<E>> getter, ReferencedSetter<O, Set<E>> setter) {
        super(id, getter, setter);
    }

    // ###########################################################################################################
    // ################################################ PATHING ##################################################
    // ###########################################################################################################

    @Override
    public <S> ModelPropertySet<S, E> prepend(Property<S, O> parent) {
        return new ModelPropertySet<>(parent.getId()+'.'+getId(),
                PathReferencedGetter.from(parent, this, getGetter()),
                PathReferencedSetter.from(parent, this, getSetter()));
    }

    // ###########################################################################################################
    // ################################################ CONTEXT ##################################################
    // ###########################################################################################################

    private Set<E> elements(O object, Context context) {
        Set<E> elements = get(object, context, false);
        if (elements == null) {
            throw new InterruptedPropertyPathException(this);
        }
        return elements;
    }

    @Override
    public E include(O object, E element, Context context) {
        elements(object, context).add(element);
        return element;
    }

    @Override
    public E drop(O object, E element, Context context) {
        if (!elements(object, context).remove(element)) {
            throw new UnknownDropableElementException(this, element);
        }
        return element;
    }

    // ###########################################################################################################
    // ################################################ FACTORY ##################################################
    // ###########################################################################################################

    /**
     * Factory method for a {@link Property} {@link Set} where the {@link Set} itself is the base.
     * <p>
     * Creates a read-only ({@link Property#isWritable()} == false) {@link Property} since no {@link Setter} is involved.
     *
     * @param <E>
     *          The element type of the {@link Set} the {@link Property} represents.
     * @return
     *          A new instance, never null
     */
    public static <E> ModelPropertySet<Set<E>, E> from() {
        return from(null);
    }

    /**
     * Factory method for a {@link Property} {@link Set} where the {@link Set} itself is the base.
     * <p>
     * Creates a read-only ({@link Property#isWritable()} == false) {@link Property} since no {@link Setter} is involved.
     *
     * @param <E>
     *          The element type of the {@link Set} the {@link Property} represents.
     * @param id
     *          The identifier of the {@link Property}; might be null, then the object id is used.
     * @return
     *          A new instance, never null
     */
    public static <E> ModelPropertySet<Set<E>, E> from(String id) {
        return new ModelPropertySet<>(id, SelfReferencedGetter.from(), ReadonlyReferencedSetter.from());
    }

    /**
     * Factory method for a {@link Property} {@link Set} that resides in an {@link Object}.
     * <p>
     * Creates a read-only ({@link Property#isWritable()} == false) {@link Property} since no {@link Setter} is involved.
     *
     * @param <O>
     *          The parent object type of the {@link Property}.
     * @param <E>
     *          The element type of the Set the {@link Property} represents.
     * @param getter
     *          A function that is able to retrieve the value from its parent object; might <b>not</b> be null.
     * @return
     *          A new instance, never null
     */
    public static <O, E> ModelPropertySet<O, E> fromObject(Getter<O, Set<E>> getter) {
        return fromObject(null, getter);
    }

    /**
     * Factory method for a {@link Property} {@link Set} that resides in an {@link Object}.
     * <p>
     * Creates a read-only ({@link Property#isWritable()} == false) {@link Property} since no {@link Setter} is involved.
     *
     * @param <O>
     *          The parent object type of the {@link Property}.
     * @param <E>
     *          The element type of the Set the {@link Property} represents.
     * @param id
     *          The identifier of the {@link Property}; might be null, then the object id is used.
     * @param getter
     *          A function that is able to retrieve the value from its parent object; might <b>not</b> be null.
     * @return
     *          A new instance, never null
     */
    public static <O, E> ModelPropertySet<O, E> fromObject(String id, Getter<O, Set<E>> getter) {
        return new ModelPropertySet<>(id, ObjectReferencedGetter.from(getter), ReadonlyReferencedSetter.from());
    }

    /**
     * Factory method for a {@link Property} {@link Set} that resides in an {@link Object}.
     *
     * @param <O>
     *          The parent object type of the {@link Property}.
     * @param <E>
     *          The element type of the Set the {@link Property} represents.
     * @param getter
     *          A function that is able to retrieve the value from its parent object; might <b>not</b> be null.
     * @param setter
     *          A function that is able to write a value to its parent object; might <b>not</b> be null.
     * @return
     *          A new instance, never null
     */
    public static <O, E> ModelPropertySet<O, E> fromObject(Getter<O, Set<E>> getter, Setter<O, Set<E>> setter) {
        return fromObject(null, getter, setter);
    }

    /**
     * Factory method for a {@link Property} {@link Set} that resides in an {@link Object}.
     *
     * @param <O>
     *          The parent object type of the {@link Property}.
     * @param <E>
     *          The element type of the Set the {@link Property} represents.
     * @param id
     *          The identifier of the {@link Property}; might be null, then the object id is used.
     * @param getter
     *          A function that is able to retrieve the value from its parent object; might <b>not</b> be null.
     * @param setter
     *          A function that is able to write a value to its parent object; might <b>not</b> be null.
     * @return
     *          A new instance, never null
     */
    public static <O, E> ModelPropertySet<O, E> fromObject(String id, Getter<O, Set<E>> getter, Setter<O, Set<E>> setter) {
        return new ModelPropertySet<>(id, ObjectReferencedGetter.from(getter), ObjectReferencedSetter.from(setter));
    }

    /**
     * Factory method for a {@link Property} {@link Set} that resides in a {@link List}.
     *
     * @param <E>
     *          The element type of the {@link Set}.
     * @return
     *          A new instance, never null
     */
    public static <E> ModelPropertySet<List<Set<E>>, E> fromList() {
        return fromList(null);
    }

    /**
     * Factory method for a {@link Property} {@link Set} that resides in a {@link List}.
     *
     * @param <E>
     *          The element type of the {@link Set}.
     * @param id
     *          The identifier of the {@link Property}; might be null, then the object id is used.
     * @return
     *          A new instance, never null
     */
    public static <E> ModelPropertySet<List<Set<E>>, E> fromList(String id) {
        return new ModelPropertySet<>(id, ListReferencedGetter.from(), ListReferencedSetter.from());
    }

    /**
     * Factory method for a {@link Property} {@link Set} that resides in another {@link Set}.
     *
     * @param <E>
     *          The element type of the {@link Set}.
     * @return
     *          A new instance, never null
     */
    public static <E> ModelPropertySet<Set<Set<E>>, E> fromSet() {
        return fromSet(null);
    }

    /**
     * Factory method for a {@link Property} {@link Set} that resides in another {@link Set}.
     *
     * @param <E>
     *          The element type of the {@link Set}.
     * @param id
     *          The identifier of the {@link Property}; might be null, then the object id is used.
     * @return
     *          A new instance, never null
     */
    public static <E> ModelPropertySet<Set<Set<E>>, E> fromSet(String id) {
        return new ModelPropertySet<>(id, SetReferencedGetter.from(), SetReferencedSetter.from());
    }

    /**
     * Factory method for a {@link Property} {@link Set} that resides in a {@link Map}.
     *
     * @param <K>
     *          The key type of the map this {@link Property} represents.
     * @param <E>
     *          The element type of the {@link Set}.
     * @return
     *          A new instance, never null
     */
    public static <K, E> ModelPropertySet<Map<K, Set<E>>, E> fromMap() {
        return fromMap(null);
    }

    /**
     * Factory method for a {@link Property} {@link Set} that resides in a {@link Map}.
     *
     * @param <K>
     *          The key type of the map this {@link Property} represents.
     * @param <E>
     *          The element type of the {@link Set}.
     * @param id
     *          The identifier of the {@link Property}; might be null, then the object id is used.
     * @return
     *          A new instance, never null
     */
    public static <K, E> ModelPropertySet<Map<K, Set<E>>, E> fromMap(String id) {
        return new ModelPropertySet<>(id, MapReferencedGetter.from(), MapReferencedSetter.from());
    }
    
    
}
