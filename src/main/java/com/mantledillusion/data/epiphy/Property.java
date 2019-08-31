package com.mantledillusion.data.epiphy;

import com.mantledillusion.data.epiphy.context.Context;
import com.mantledillusion.data.epiphy.exception.InterruptedPropertyPathException;
import com.mantledillusion.data.epiphy.exception.OutboundPropertyPathException;
import com.mantledillusion.data.epiphy.exception.ReadonlyPropertyException;
import com.mantledillusion.data.epiphy.exception.UnreferencedPropertyPathException;

import java.util.Collection;

/**
 * Interface for a value that resides in an object.
 *
 * @param <O>
 *            The root parent object type of this {@link Property}.
 * @param <V>
 *            The type of the value this {@link Property} represents.
 */
public interface Property<O, V> {

    /**
     * The id this {@link Property} is identified by in relation to the uppermost parent object its value resides in.
     * <p>
     * If not set to a specific value (or null) upon creation, this method returns this {@link Property}'s objectId as
     * provided by the JVM.
     *
     * @return This {@link Property}'s id; never null
     */
    String getId();

    /**
     * Returns whether this {@link Property} is reachable, so the property itself might be null, but all the of the
     * objects it is read from aren't.
     *
     * @param object
     *            The instance to lookup the property from; might be null.
     * @return True if all of this {@link Property}'s parents
     * @throws UnreferencedPropertyPathException
     *             If there is any referenced property in this {@link Property} value's path.
     */
    default boolean exists(O object)
            throws UnreferencedPropertyPathException {
        return exists(object, null);
    }

    /**
     * Returns whether this {@link Property} is reachable, so the property itself might be null, but all the of the
     * objects it is read from aren't.
     *
     * @param object
     *            The instance to lookup the property from; might be null.
     * @param context
     *            The {@link Context} that should be used to satisfy the referenced properties from the root object to
     *            this {@link Property}'s value; might be null.
     * @return True if all of this {@link Property}'s parents
     * @throws UnreferencedPropertyPathException
     *             If there is any referenced property in this {@link Property} value's path.
     * @throws OutboundPropertyPathException
     *             If there is a referenced property in this {@link Property} value's path that has a
     *             {@link com.mantledillusion.data.epiphy.context.reference.PropertyReference} included in the  given
     *             {@link Context} that does not match that {@link Property} value's bounds.
     */
    boolean exists(O object, Context context)
            throws UnreferencedPropertyPathException, OutboundPropertyPathException;

    /**
     * Returns whether this {@link Property}'s value or any parent object is null.
     *
     * @param object
     *            The instance to lookup the property from; might be null.
     * @return True if this {@link Property} or any of its parents is null, false otherwise
     * @throws UnreferencedPropertyPathException
     *             If there is any referenced property in this {@link Property} value's path.
     */
    default boolean isNull(O object)
            throws UnreferencedPropertyPathException {
        return get(object, true) == null;
    }

    /**
     * Returns whether this {@link Property}'s value or any parent object is null.
     *
     * @param object
     *            The instance to lookup the property from; might be null.
     * @param context
     *            The {@link Context} that should be used to satisfy the referenced properties from the root object to
     *            this {@link Property}'s value; might be null.
     * @return True if this {@link Property} or any of its parents is null, false otherwise
     * @throws UnreferencedPropertyPathException
     *             If there is any referenced property in this {@link Property} value's path.
     * @throws OutboundPropertyPathException
     *             If there is a referenced property in this {@link Property} value's path that has a
     *             {@link com.mantledillusion.data.epiphy.context.reference.PropertyReference} included in the  given
     *             {@link Context} that does not match that {@link Property} value's bounds.
     */
    default boolean isNull(O object, Context context)
            throws UnreferencedPropertyPathException, OutboundPropertyPathException {
        return get(object, context, true) == null;
    }

    /**
     * Retrieves this {@link Property}'s value out of the given object.
     *
     * @param object
     *            The instance to lookup the property value from; might be null.
     * @return This {@link Property}'s retrieved value; might return null if the value is null
     * @throws InterruptedPropertyPathException
     *             If any object on the path to this {@link Property}'s value is null.
     * @throws UnreferencedPropertyPathException
     *             If there is any referenced property in this {@link Property} value's path.
     */
    default V get(O object)
            throws InterruptedPropertyPathException, UnreferencedPropertyPathException {
        return get(object, null, false);
    }

    /**
     * Retrieves this {@link Property}'s value out of the given object.
     *
     * @param object
     *            The instance to lookup the property value from; might be null.
     * @param allowNull
     *            Whether or not any parent object of this property is allowed to be null. If set to true, instead of
     *            throwing an {@link InterruptedPropertyPathException}, the method will just return null.
     * @return This {@link Property}'s retrieved value; might return null if the value is null or if a parent object is
     * null and allowNull is set to true
     * @throws InterruptedPropertyPathException
     *             If any object on the path to this {@link Property}'s value is null and allowNull is set to false.
     * @throws UnreferencedPropertyPathException
     *             If there is any referenced property in this {@link Property} value's path.
     */
    default V get(O object, boolean allowNull)
            throws InterruptedPropertyPathException, UnreferencedPropertyPathException {
        return get(object, null, allowNull);
    }

    /**
     * Retrieves this {@link Property}'s value out of the given object.
     *
     * @param object
     *            The instance to lookup the property value from; might be null.
     * @param context
     *            The {@link Context} that should be used to satisfy the referenced properties from the root object to
     *            this {@link Property}'s value; might be null.
     * @return This {@link Property}'s retrieved value; might return null if the value is null
     * @throws InterruptedPropertyPathException
     *             If any object on the path to this {@link Property}'s value is null.
     * @throws UnreferencedPropertyPathException
     *             If there is any referenced property in this {@link Property} value's path that does not have a
     *             {@link com.mantledillusion.data.epiphy.context.reference.PropertyReference} included in the given
     *             {@link Context}.
     * @throws OutboundPropertyPathException
     *             If there is a referenced property in this {@link Property} value's path that has a
     *             {@link com.mantledillusion.data.epiphy.context.reference.PropertyReference} included in the  given
     *             {@link Context} that does not match that {@link Property} value's bounds.
     */
    default V get(O object, Context context)
            throws InterruptedPropertyPathException, UnreferencedPropertyPathException, OutboundPropertyPathException {
        return get(object, context, false);
    }

    /**
     * Retrieves this {@link Property}'s value out of the given object.
     *
     * @param object
     *            The instance to lookup the property value from; might be null.
     * @param context
     *            The {@link Context} that should be used to satisfy the referenced properties from the root object to
     *            this {@link Property}'s value; might be null.
     * @param allowNull
     *            Whether or not any parent object of this property is allowed to be null. If set to true, instead of
     *            throwing an {@link InterruptedPropertyPathException}, the method will just return null.
     * @return This {@link Property}'s retrieved value; might return null if the value is null or if a parent object is
     * null and allowNull is set to true
     * @throws InterruptedPropertyPathException
     *             If any object on the path to this {@link Property}'s value is null and allowNull is set to false.
     * @throws UnreferencedPropertyPathException
     *             If there is any referenced property in this {@link Property} value's path that does not have a
     *             {@link com.mantledillusion.data.epiphy.context.reference.PropertyReference} included in the given
     *             {@link Context}.
     * @throws OutboundPropertyPathException
     *             If there is a referenced property in this {@link Property} value's path that has a
     *             {@link com.mantledillusion.data.epiphy.context.reference.PropertyReference} included in the  given
     *             {@link Context} that does not match that {@link Property} value's bounds.
     */
    V get(O object, Context context, boolean allowNull)
            throws InterruptedPropertyPathException, UnreferencedPropertyPathException, OutboundPropertyPathException;

    /**
     * Writes the given value to this {@link Property} in the given object.
     * <p>
     * Note that this is a writing operation; the property has to {@link #exists(Object, Context)} in the given object.
     *
     * @param object
     *            The instance to write the property value to; might be null.
     * @param value
     *            The value to set; might be null.
     * @throws InterruptedPropertyPathException
     *             If any object on the path to this {@link Property}'s value is null.
     * @throws UnreferencedPropertyPathException
     *             If there is any referenced property in this {@link Property} value's path.
     * @throws ReadonlyPropertyException
     *             If this {@link Property} does not have a {@link com.mantledillusion.data.epiphy.io.Setter} defined
     *             to write its value with.
     */
    default void set(O object, V value)
            throws InterruptedPropertyPathException, UnreferencedPropertyPathException, ReadonlyPropertyException {
        set(object, value, null);
    }

    /**
     * Writes the given value to this {@link Property} in the given object.
     * <p>
     * Note that this is a writing operation; the property has to {@link #exists(Object, Context)} in the given object.
     *
     * @param object
     *            The instance to write the property value to; might be null.
     * @param value
     *            The value to set; might be null.
     * @param context
     *            The {@link Context} that should be used to satisfy the referenced properties from the root object to
     *            this {@link Property}'s value; might be null.
     * @throws InterruptedPropertyPathException
     *             If any object on the path to this {@link Property}'s value is null.
     * @throws UnreferencedPropertyPathException
     *             If there is any referenced property in this {@link Property} value's path that does not have a
     *             {@link com.mantledillusion.data.epiphy.context.reference.PropertyReference} included in the given
     *             {@link Context}.
     * @throws OutboundPropertyPathException
     *             If there is a referenced property in this {@link Property} value's path that has a
     *             {@link com.mantledillusion.data.epiphy.context.reference.PropertyReference} included in the  given
     *             {@link Context} that does not match that {@link Property} value's bounds.
     * @throws ReadonlyPropertyException
     *             If this {@link Property} does not have a {@link com.mantledillusion.data.epiphy.io.Setter} defined
     *             to write its value with.
     */
    void set(O object, V value, Context context)
            throws InterruptedPropertyPathException, UnreferencedPropertyPathException, OutboundPropertyPathException,
            ReadonlyPropertyException;

    /**
     * Builds a path with this {@link Property} as the leading and the given {@link Property} as the trailing end.
     * <p>
     * A {@link Property} path also is a property, but with at least one intermediate object between the root object
     * and the value.
     *
     * @param <T> The value type of the path.
     * @param <P1> The {@link Property} type of the child.
     * @param <P2> The {@link Property} type of the path.
     * @param child The child to append to this {@link Property} to form the path; might <b>not</b> be null.
     * @return A new {@link Property} with the same type as the child and the root object type of this {@link Property},
     * never null
     */
    default <T, P1 extends Property<V, T>, P2 extends Property<O, T>> P2 append(P1 child) {
        return (P2) child.prepend(this);
    }

    /**
     * Builds a path with the given {@link Property} as the leading and this {@link Property} as the trailing end.
     * <p>
     * A {@link Property} path also is a property, but with at least one intermediate object between the root object
     * and the value.
     *
     * @param <S> The object type of the path.
     * @param parent The parent to prepent to this {@link Property} to form the path; might <b>not</b> be null.
     * @return A new {@link Property} with the same type as this {@link Property} and the root object type of the child,
     * never null
     */
    <S> Property<S, V> prepend(Property<S, O> parent);

    Collection<Context> contextualize(O object);
}
