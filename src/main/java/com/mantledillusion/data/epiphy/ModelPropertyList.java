package com.mantledillusion.data.epiphy;

import com.mantledillusion.data.epiphy.context.function.*;
import com.mantledillusion.data.epiphy.context.io.ReferencedGetter;
import com.mantledillusion.data.epiphy.context.io.ReferencedSetter;
import com.mantledillusion.data.epiphy.context.Context;
import com.mantledillusion.data.epiphy.context.reference.ReferencedValue;
import com.mantledillusion.data.epiphy.context.io.*;
import com.mantledillusion.data.epiphy.exception.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Represents a {@link Property} {@link List}.
 * <p>
 * Instantiable using the <code>from...()</code> methods.
 *
 * @param <O>
 *          The parent object type of this {@link Property}.
 * @param <E>
 *          The element type of the {@link List} this {@link Property} represents.
 */
public class ModelPropertyList<O, E> extends AbstractModelProperty<O, List<E>> implements
        IncludableProperty<O, List<E>, E, Integer>,
        InsertableProperty<O, List<E>, E, Integer>,
        StripableProperty<O, List<E>, E, Integer>,
        ExtractableProperty<O, List<E>, E, Integer>,
        DropableProperty<O, List<E>, E, Integer> {

    private ModelPropertyList(String id, ReferencedGetter<O, List<E>> getter, ReferencedSetter<O, List<E>> setter) {
        super(id, getter, setter);
    }

    // ###########################################################################################################
    // ################################################ PATHING ##################################################
    // ###########################################################################################################

    @Override
    public <S> ModelPropertyList<S, E> obfuscate(Class<O> objectType) {
        return new ModelPropertyList<>(getId(), getGetter().obfuscate(objectType), getSetter().obfuscate(objectType));
    }

    @Override
    public <S> ModelPropertyList<S, E> prepend(Property<S, O> parent) {
        return new ModelPropertyList<>(parent.getId()+'.'+getId(),
                PathReferencedGetter.from(parent, this, getGetter()),
                PathReferencedSetter.from(parent, this, getSetter()));
    }

    // ###########################################################################################################
    // ################################################ CONTEXT ##################################################
    // ###########################################################################################################

    private List<E> elements(O object, Context context) {
        List<E> elements = get(object, context, false);
        if (elements == null) {
            throw new InterruptedPropertyPathException(this);
        }
        return elements;
    }

    @Override
    public boolean contains(O object, Integer reference, Context context) {
        return reference >= 0 && reference < elements(object, context).size();
    }

    @Override
    public Integer include(O object, E element, Context context) {
        List<E> elements = elements(object, context);
        elements.add(element);
        return elements.size()-1;
    }

    @Override
    public void insert(O object, E element, Integer reference, Context context) {
        List<E> elements = elements(object, context);
        if (reference < 0 || reference > elements.size()) {
            throw new OutboundInsertableReferenceException(this, reference);
        }
        elements(object, context).add(reference, element);
    }

    @Override
    public ReferencedValue<Integer, E> strip(O object, Context context) {
        List<E> elements = elements(object, context);
        return elements.isEmpty() ? null : ReferencedValue.of(elements.size()-1, elements.remove(elements.size()-1));
    }

    @Override
    public E extract(O object, Integer reference, Context context) {
        List<E> elements = elements(object, context);
        if (reference < 0 || reference >= elements.size()) {
            throw new OutboundExtractableReferenceException(this, reference);
        }
        return elements.remove((int) reference);
    }

    @Override
    public Integer drop(O object, E element, Context context) {
        List<E> elements = elements(object, context);
        Integer index = elements.indexOf(element);
        if (index == -1) {
            throw new UnknownDropableElementException(this, element);
        }
        elements.remove(element);
        return index;
    }

    // ###########################################################################################################
    // ################################################ FACTORY ##################################################
    // ###########################################################################################################

    /**
     * Factory method for a {@link Property} {@link List} where the {@link List} itself is the base.
     * <p>
     * Creates a read-only ({@link Property#isWritable()} == false) {@link Property} since no {@link Setter} is involved.
     *
     * @param <E>
     *          The element type of the list the {@link Property} represents.
     * @return
     *          A new instance, never null
     */
    public static <E> ModelPropertyList<List<E>, E> from() {
        return from(null);
    }

    /**
     * Factory method for a {@link Property} {@link List} where the {@link List} itself is the base.
     * <p>
     * Creates a read-only ({@link Property#isWritable()} == false) {@link Property} since no {@link Setter} is involved.
     *
     * @param <E>
     *          The element type of the list the {@link Property} represents.
     * @param id
     *          The identifier of the {@link Property}; might be null, then the object id is used.
     * @return
     *          A new instance, never null
     */
    public static <E> ModelPropertyList<List<E>, E> from(String id) {
        return new ModelPropertyList<>(id, SelfReferencedGetter.from(), ReadonlyReferencedSetter.from());
    }

    /**
     * Factory method for a {@link Property} {@link List} that resides in an {@link Object}.
     * <p>
     * Creates a read-only ({@link Property#isWritable()} == false) {@link Property} since no {@link Setter} is involved.
     *
     * @param <O>
     *          The parent object type of the {@link Property}.
     * @param <E>
     *          The element type of the {@link List} the {@link Property} represents.
     * @param getter
     *          A function that is able to retrieve the value from its parent object; might <b>not</b> be null.
     * @return
     *          A new instance, never null
     */
    public static <O, E> ModelPropertyList<O, E> fromObject(Getter<O, List<E>> getter) {
        return fromObject(null, getter);
    }

    /**
     * Factory method for a {@link Property} {@link List} that resides in an {@link Object}.
     * <p>
     * Creates a read-only ({@link Property#isWritable()} == false) {@link Property} since no {@link Setter} is involved.
     *
     * @param <O>
     *          The parent object type of the {@link Property}.
     * @param <E>
     *          The element type of the {@link List} the {@link Property} represents.
     * @param id
     *          The identifier of the {@link Property}; might be null, then the object id is used.
     * @param getter
     *          A function that is able to retrieve the value from its parent object; might <b>not</b> be null.
     * @return
     *          A new instance, never null
     */
    public static <O, E> ModelPropertyList<O, E> fromObject(String id, Getter<O, List<E>> getter) {
        return new ModelPropertyList<>(id, ObjectReferencedGetter.from(getter), ReadonlyReferencedSetter.from());
    }

    /**
     * Factory method for a {@link Property} {@link List} that resides in an {@link Object}.
     *
     * @param <O>
     *          The parent object type of the {@link Property}.
     * @param <E>
     *          The element type of the {@link List} the {@link Property} represents.
     * @param getter
     *          A function that is able to retrieve the value from its parent object; might <b>not</b> be null.
     * @param setter
     *          A function that is able to write a value to its parent object; might <b>not</b> be null.
     * @return
     *          A new instance, never null
     */
    public static <O, E> ModelPropertyList<O, E> fromObject(Getter<O, List<E>> getter, Setter<O, List<E>> setter) {
        return fromObject(null, getter, setter);
    }

    /**
     * Factory method for a {@link Property} {@link List} that resides in an {@link Object}.
     *
     * @param <O>
     *          The parent object type of the {@link Property}.
     * @param <E>
     *          The element type of the {@link List} the {@link Property} represents.
     * @param id
     *          The identifier of the {@link Property}; might be null, then the object id is used.
     * @param getter
     *          A function that is able to retrieve the value from its parent object; might <b>not</b> be null.
     * @param setter
     *          A function that is able to write a value to its parent object; might <b>not</b> be null.
     * @return
     *          A new instance, never null
     */
    public static <O, E> ModelPropertyList<O, E> fromObject(String id, Getter<O, List<E>> getter, Setter<O, List<E>> setter) {
        return new ModelPropertyList<>(id, ObjectReferencedGetter.from(getter), ObjectReferencedSetter.from(setter));
    }

    /**
     * Factory method for a {@link Property} {@link List} that resides in another {@link List}.
     *
     * @param <E>
     *          The element type of the {@link List}.
     * @return
     *          A new instance, never null
     */
    public static <E> ModelPropertyList<List<List<E>>, E> fromList() {
        return fromList(null);
    }

    /**
     * Factory method for a {@link Property} {@link List} that resides in another {@link List}.
     *
     * @param <E>
     *          The element type of the {@link List}.
     * @param id
     *          The identifier of the {@link Property}; might be null, then the object id is used.
     * @return
     *          A new instance, never null
     */
    public static <E> ModelPropertyList<List<List<E>>, E> fromList(String id) {
        return new ModelPropertyList<>(id, ListReferencedGetter.from(), ListReferencedSetter.from());
    }

    /**
     * Factory method for a {@link Property} {@link List} that resides in a {@link Set}.
     *
     * @param <E>
     *          The element type of the {@link List}.
     * @return
     *          A new instance, never null
     */
    public static <E> ModelPropertyList<Set<List<E>>, E> fromSet() {
        return fromSet(null);
    }

    /**
     * Factory method for a {@link Property} {@link List} that resides in a {@link List}.
     *
     * @param <E>
     *          The element type of the {@link List}.
     * @param id
     *          The identifier of the {@link Property}; might be null, then the object id is used.
     * @return
     *          A new instance, never null
     */
    public static <E> ModelPropertyList<Set<List<E>>, E> fromSet(String id) {
        return new ModelPropertyList<>(id, SetReferencedGetter.from(), SetReferencedSetter.from());
    }

    /**
     * Factory method for a {@link Property} {@link List} that resides in a {@link Map}.
     *
     * @param <K>
     *          The key type of the map this {@link Property} represents.
     * @param <E>
     *          The element type of the {@link List}.
     * @return
     *          A new instance, never null
     */
    public static <K, E> ModelPropertyList<Map<K, List<E>>, E> fromMap() {
        return fromMap(null);
    }

    /**
     * Factory method for a {@link Property} {@link List} that resides in a {@link Map}.
     *
     * @param <K>
     *          The key type of the map this {@link Property} represents.
     * @param <E>
     *          The element type of the {@link List}.
     * @param id
     *          The identifier of the {@link Property}; might be null, then the object id is used.
     * @return
     *          A new instance, never null
     */
    public static <K, E> ModelPropertyList<Map<K, List<E>>, E> fromMap(String id) {
        return new ModelPropertyList<>(id, MapReferencedGetter.from(), MapReferencedSetter.from());
    }
}