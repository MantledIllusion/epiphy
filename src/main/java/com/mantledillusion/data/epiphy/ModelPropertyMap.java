package com.mantledillusion.data.epiphy;

import com.mantledillusion.data.epiphy.context.Context;
import com.mantledillusion.data.epiphy.context.function.DropableProperty;
import com.mantledillusion.data.epiphy.context.function.ExtractableProperty;
import com.mantledillusion.data.epiphy.context.function.InsertableProperty;
import com.mantledillusion.data.epiphy.context.io.*;
import com.mantledillusion.data.epiphy.exception.InterruptedPropertyPathException;

import java.util.*;

/**
 * Represents a {@link Property} {@link Map}.
 * <p>
 * Instantiable using the <code>from...()</code> methods.
 *
 * @param <O>
 *          The parent object type of this {@link Property}.
 * @param <K>
 *          The key type of the map this {@link Property} represents.
 * @param <V>
 *          The value type of the map this {@link Property} represents.
 */
public class ModelPropertyMap<O, K, V>  extends AbstractModelProperty<O, Map<K, V>> implements
        InsertableProperty<O, Map<K, V>, V, K>,
        ExtractableProperty<O, Map<K, V>, V, K>,
        DropableProperty<O, Map<K, V>, V, K> {

    private ModelPropertyMap(String id, ReferencedGetter<O, Map<K, V>> getter, ReferencedSetter<O, Map<K, V>> setter) {
        super(id, getter, setter);
    }

    // ###########################################################################################################
    // ################################################ PATHING ##################################################
    // ###########################################################################################################

    @Override
    public <S> ModelPropertyMap<S, K, V> prepend(Property<S, O> parent) {
        return new ModelPropertyMap<>(parent.getId()+'.'+getId(),
                PathReferencedGetter.from(parent, this, getGetter()),
                PathReferencedSetter.from(parent, this, getSetter()));
    }

    // ###########################################################################################################
    // ################################################ CONTEXT ##################################################
    // ###########################################################################################################

    private Map<K, V> elements(O object, Context context) {
        Map<K, V> elements = get(object, context, false);
        if (elements == null) {
            throw new InterruptedPropertyPathException(this);
        }
        return elements;
    }

    @Override
    public void insert(O object, V element, K reference, Context context) {
        elements(object, context).put(reference, element);
    }

    @Override
    public V extract(O object, K reference, Context context) {
        return elements(object, context).remove(reference);
    }

    @Override
    public K drop(O object, V element, Context context) {
        Map<K, V> elements = elements(object, context);
        Optional<K> key = elements.entrySet().parallelStream().
                filter(entry -> Objects.equals(entry.getValue(), element)).
                map(Map.Entry::getKey).
                findFirst();
        return key.filter(k -> elements.remove(k, element)).orElse(null);
    }

    // ###########################################################################################################
    // ################################################ FACTORY ##################################################
    // ###########################################################################################################

    /**
     * Factory method for a {@link Property} {@link Map} where the {@link Map} itself is the base.
     * <p>
     * Creates a read-only ({@link Property#isWritable()} == false) {@link Property} since no {@link Setter} is involved.
     *
     * @param <K>
     *          The key type of the map this {@link Property} represents.
     * @param <V>
     *          The value type of the map this {@link Property} represents.
     * @return
     *          A new instance, never null
     */
    public static <K, V> ModelPropertyMap<Map<K, V>, K, V> from() {
        return from(null);
    }

    /**
     * Factory method for a {@link Property} {@link Map} where the {@link Map} itself is the base.
     * <p>
     * Creates a read-only ({@link Property#isWritable()} == false) {@link Property} since no {@link Setter} is involved.
     *
     * @param <K>
     *          The key type of the map this {@link Property} represents.
     * @param <V>
     *          The value type of the map this {@link Property} represents.
     * @param id
     *          The identifier of the {@link Property}; might be null, then the object id is used.
     * @return
     *          A new instance, never null
     */
    public static <K, V> ModelPropertyMap<Map<K, V>, K, V> from(String id) {
        return new ModelPropertyMap<>(id, SelfReferencedGetter.from(), ReadonlyReferencedSetter.from());
    }

    /**
     * Factory method for a {@link Property} {@link Map} that resides in an {@link Object}.
     * <p>
     * Creates a read-only ({@link Property#isWritable()} == false) {@link Property} since no {@link Setter} is involved.
     *
     * @param <O>
     *          The parent object type of the {@link Property}.
     * @param <K>
     *          The key type of the map this {@link Property} represents.
     * @param <V>
     *          The value type of the map this {@link Property} represents.
     * @param getter
     *          A function that is able to retrieve the value from its parent object; might <b>not</b> be null.
     * @return
     *          A new instance, never null
     */
    public static <O, K, V> ModelPropertyMap<O, K, V> fromObject(Getter<O, Map<K, V>> getter) {
        return fromObject(null, getter);
    }

    /**
     * Factory method for a {@link Property} {@link Map} that resides in an {@link Object}.
     * <p>
     * Creates a read-only ({@link Property#isWritable()} == false) {@link Property} since no {@link Setter} is involved.
     *
     * @param <O>
     *          The parent object type of the {@link Property}.
     * @param <K>
     *          The key type of the map this {@link Property} represents.
     * @param <V>
     *          The value type of the map this {@link Property} represents.
     * @param id
     *          The identifier of the {@link Property}; might be null, then the object id is used.
     * @param getter
     *          A function that is able to retrieve the value from its parent object; might <b>not</b> be null.
     * @return
     *          A new instance, never null
     */
    public static <O, K, V> ModelPropertyMap<O, K, V> fromObject(String id, Getter<O, Map<K, V>> getter) {
        return new ModelPropertyMap<>(id, ObjectReferencedGetter.from(getter), ReadonlyReferencedSetter.from());
    }

    /**
     * Factory method for a {@link Property} {@link Map} that resides in an {@link Object}.
     *
     * @param <O>
     *          The parent object type of the {@link Property}.
     * @param <K>
     *          The key type of the map this {@link Property} represents.
     * @param <V>
     *          The value type of the map this {@link Property} represents.
     * @param getter
     *          A function that is able to retrieve the value from its parent object; might <b>not</b> be null.
     * @param setter
     *          A function that is able to write a value to its parent object; might <b>not</b> be null.
     * @return
     *          A new instance, never null
     */
    public static <O, K, V> ModelPropertyMap<O, K, V> fromObject(Getter<O, Map<K, V>> getter, Setter<O, Map<K, V>> setter) {
        return fromObject(null, getter, setter);
    }

    /**
     * Factory method for a {@link Property} {@link Map} that resides in an {@link Object}.
     *
     * @param <O>
     *          The parent object type of the {@link Property}.
     * @param <K>
     *          The key type of the map this {@link Property} represents.
     * @param <V>
     *          The value type of the map this {@link Property} represents.
     * @param id
     *          The identifier of the {@link Property}; might be null, then the object id is used.
     * @param getter
     *          A function that is able to retrieve the value from its parent object; might <b>not</b> be null.
     * @param setter
     *          A function that is able to write a value to its parent object; might <b>not</b> be null.
     * @return
     *          A new instance, never null
     */
    public static <O, K, V> ModelPropertyMap<O, K, V> fromObject(String id, Getter<O, Map<K, V>> getter, Setter<O, Map<K, V>> setter) {
        return new ModelPropertyMap<>(id, ObjectReferencedGetter.from(getter), ObjectReferencedSetter.from(setter));
    }

    /**
     * Factory method for a {@link Property} {@link Map} that resides in a {@link List}.
     *
     * @param <K>
     *          The key type of the map this {@link Property} represents.
     * @param <V>
     *          The value type of the map this {@link Property} represents.
     * @return
     *          A new instance, never null
     */
    public static <K, V> ModelPropertyMap<List<Map<K, V>>, K, V> fromList() {
        return fromList(null);
    }

    /**
     * Factory method for a {@link Property} {@link Map} that resides in a {@link List}.
     *
     * @param <K>
     *          The key type of the map this {@link Property} represents.
     * @param <V>
     *          The value type of the map this {@link Property} represents.
     * @param id
     *          The identifier of the {@link Property}; might be null, then the object id is used.
     * @return
     *          A new instance, never null
     */
    public static <K, V> ModelPropertyMap<List<Map<K, V>>, K, V> fromList(String id) {
        return new ModelPropertyMap<>(id, ListReferencedGetter.from(), ListReferencedSetter.from());
    }

    /**
     * Factory method for a {@link Property} {@link Map} that resides in another {@link Map}.
     *
     * @param <K1>
     *          The key type of the this {@link Property}'s parent map represents.
     * @param <K2>
     *          The key type of the map this {@link Property} represents.
     * @param <V>
     *          The value type of the map this {@link Property} represents.
     * @return
     *          A new instance, never null
     */
    public static <K1, K2, V> ModelPropertyMap<Map<K1, Map<K2, V>>, K2, V> fromMap() {
        return fromMap(null);
    }

    /**
     * Factory method for a {@link Property} {@link Map} that resides in another {@link Map}.
     *
     * @param <K1>
     *          The key type of the this {@link Property}'s parent map represents.
     * @param <K2>
     *          The key type of the map this {@link Property} represents.
     * @param <V>
     *          The value type of the map this {@link Property} represents.
     * @param id
     *          The identifier of the {@link Property}; might be null, then the object id is used.
     * @return
     *          A new instance, never null
     */
    public static <K1, K2, V> ModelPropertyMap<Map<K1, Map<K2, V>>, K2, V> fromMap(String id) {
        return new ModelPropertyMap<>(id, MapReferencedGetter.from(), MapReferencedSetter.from());
    }
}
