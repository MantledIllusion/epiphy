package com.mantledillusion.data.epiphy;

import com.mantledillusion.data.epiphy.context.Context;

public interface Property<O, V> {

    /**
     * The id this {@link Property} is identified by in relation to the object its value resides in.
     * <p>
     * If not set to a specific value (or null) upon creation, this method returns this {@link Property}'s objectId as
     * provided by the JVM.
     *
     * @return This {@link Property}'s id; never null
     */
    String getId();

    default boolean exists(O instance) {
        return exists(instance, null);
    }

    boolean exists(O instance, Context context);

    default boolean isNull(O instance) {
        return get(instance, true) == null;
    }

    default boolean isNull(O instance, Context context) {
        return get(instance, context, true) == null;
    }

    default V get(O instance) {
        return get(instance, null, false);
    }

    default V get(O instance, boolean allowNull) {
        return get(instance, null,allowNull);
    }

    default V get(O instance, Context context) {
        return get(instance, context, false);
    }

    V get(O instance, Context context, boolean allowNull);

    default void set(O instance, V value) {
        set(instance, value, null);
    }

    void set(O instance, V value, Context context);

    default <T, P1 extends Property<V, T>, P2 extends Property<O, T>> P2 append(P1 child) {
        return (P2) child.prepend(this);
    }

    <S> Property<S, V> prepend(Property<S, O> parent);
}
