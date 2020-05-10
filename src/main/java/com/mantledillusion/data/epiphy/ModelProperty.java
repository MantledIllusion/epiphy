package com.mantledillusion.data.epiphy;

import com.mantledillusion.data.epiphy.context.io.*;
import com.mantledillusion.data.epiphy.context.io.ReferencedGetter;
import com.mantledillusion.data.epiphy.context.io.ReferencedSetter;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Represents a simple {@link Property} where an object value resides in another object.
 * <p>
 * Instantiable using the <code>from...()</code> methods.
 *
 * @param <O>
 *          The parent object type of this {@link Property}.
 * @param <V>
 *          The type of the child value this {@link Property} represents.
 */
public class ModelProperty<O, V> extends AbstractModelProperty<O, V> {

    private ModelProperty(String id, ReferencedGetter<O, V> getter, ReferencedSetter<O, V> setter) {
        super(id, getter, setter);
    }

    // ###########################################################################################################
    // ################################################ PATHING ##################################################
    // ###########################################################################################################

    @Override
    public <S> ModelProperty<S, V> obfuscate(Class<O> objectType) {
        return new ModelProperty<>(getId(), getGetter().obfuscate(objectType), getSetter().obfuscate(objectType));
    }

    @Override
    public <S> ModelProperty<S, V> prepend(Property<S, O> parent) {
        return new ModelProperty<>(parent.getId()+'.'+getId(),
                PathReferencedGetter.from(parent, this, getGetter()),
                PathReferencedSetter.from(parent, this, getSetter()));
    }

    // ###########################################################################################################
    // ################################################ FACTORY ##################################################
    // ###########################################################################################################

    /**
     * Factory method for a {@link Property} that resides in an {@link Object}.
     * <p>
     * Creates a read-only ({@link Property#isWritable()} == false) {@link Property} since no {@link Setter} is involved.
     *
     * @param <O>
     *          The parent object type of the {@link Property}.
     * @param <V>
     *          The type of the child value the {@link Property} represents.
     * @param getter
     *          A function that is able to retrieve the value from its parent object; might <b>not</b> be null.
     * @return
     *          A new instance, never null
     */
    public static <O, V> ModelProperty<O, V> fromObject(Getter<O, V> getter) {
        return fromObject(null, getter);
    }

    /**
     * Factory method for a {@link Property} that resides in an {@link Object}.
     * <p>
     * Creates a read-only ({@link Property#isWritable()} == false) {@link Property} since no {@link Setter} is involved.
     *
     * @param <O>
     *          The parent object type of the {@link Property}.
     * @param <V>
     *          The type of the child value the {@link Property} represents.
     * @param id
     *          The identifier of the {@link Property}; might be null, then the object id is used.
     * @param getter
     *          A function that is able to retrieve the value from its parent object; might <b>not</b> be null.
     * @return
     *          A new instance, never null
     */
    public static <O, V> ModelProperty<O, V> fromObject(String id, Getter<O, V> getter) {
        return new ModelProperty<>(id, ObjectReferencedGetter.from(getter), ReadonlyReferencedSetter.from());
    }

    /**
     * Factory method for a {@link Property} that resides in an {@link Object}.
     *
     * @param <O>
     *          The parent object type of the {@link Property}.
     * @param <V>
     *          The type of the child value the {@link Property} represents.
     * @param getter
     *          A function that is able to retrieve the value from its parent object; might <b>not</b> be null.
     * @param setter
     *          A function that is able to write a value to its parent object; might <b>not</b> be null.
     * @return
     *          A new instance, never null
     */
    public static <O, V> ModelProperty<O, V> fromObject(Getter<O, V> getter, Setter<O, V> setter) {
        return fromObject(null, getter, setter);
    }

    /**
     * Factory method for a {@link Property} that resides in an {@link Object}.
     *
     * @param <O>
     *          The parent object type of the {@link Property}.
     * @param <V>
     *          The type of the child value the {@link Property} represents.
     * @param id
     *          The identifier of the {@link Property}; might be null, then the object id is used.
     * @param getter
     *          A function that is able to retrieve the value from its parent object; might <b>not</b> be null.
     * @param setter
     *          A function that is able to write a value to its parent object; might <b>not</b> be null.
     * @return
     *          A new instance, never null
     */
    public static <O, V> ModelProperty<O, V> fromObject(String id, Getter<O, V> getter, Setter<O, V> setter) {
        return new ModelProperty<>(id, ObjectReferencedGetter.from(getter), ObjectReferencedSetter.from(setter));
    }

    /**
     * Factory method for a {@link Property} that resides in a {@link List}.
     *
     * @param <E>
     *          The element type of the {@link List}.
     * @return
     *          A new instance, never null
     */
    public static <E> ModelProperty<List<E>, E> fromList() {
        return fromList(null);
    }

    /**
     * Factory method for a {@link Property} that resides in a {@link List}.
     *
     * @param <E>
     *          The element type of the {@link List}.
     * @param id
     *          The identifier of the {@link Property}; might be null, then the object id is used.
     * @return
     *          A new instance, never null
     */
    public static <E> ModelProperty<List<E>, E> fromList(String id) {
        return new ModelProperty<>(id, ListReferencedGetter.from(), ListReferencedSetter.from());
    }

    /**
     * Factory method for a {@link Property} that resides in a {@link Set}.
     *
     * @param <E>
     *          The element type of the {@link Set}.
     * @return
     *          A new instance, never null
     */
    public static <E> ModelProperty<Set<E>, E> fromSet() {
        return fromSet(null);
    }

    /**
     * Factory method for a {@link Property} that resides in a {@link Set}.
     *
     * @param <E>
     *          The element type of the {@link Set}.
     * @param id
     *          The identifier of the {@link Property}; might be null, then the object id is used.
     * @return
     *          A new instance, never null
     */
    public static <E> ModelProperty<Set<E>, E> fromSet(String id) {
        return new ModelProperty<>(id, SetReferencedGetter.from(), SetReferencedSetter.from());
    }

    /**
     * Factory method for a {@link Property} that resides in a {@link Map}.
     *
     * @param <K>
     *          The key type of the map this {@link Property} represents.
     * @param <V>
     *          The value type of the map this {@link Property} represents.
     * @return
     *          A new instance, never null
     */
    public static <K, V> ModelProperty<Map<K, V>, V> fromMap() {
        return fromMap(null);
    }

    /**
     * Factory method for a {@link Property} that resides in a {@link Map}.
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
    public static <K, V> ModelProperty<Map<K, V>, V> fromMap(String id) {
        return new ModelProperty<>(id, MapReferencedGetter.from(), MapReferencedSetter.from());
    }
}
