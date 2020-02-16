package com.mantledillusion.data.epiphy;

import com.mantledillusion.data.epiphy.context.Context;
import com.mantledillusion.data.epiphy.exception.InterruptedPropertyPathException;
import com.mantledillusion.data.epiphy.exception.OutboundPropertyPathException;
import com.mantledillusion.data.epiphy.exception.ReadonlyPropertyException;
import com.mantledillusion.data.epiphy.exception.UnreferencedPropertyPathException;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Stream;

/**
 * Interface for a value that resides in an object.
 *
 * @param <O>
 *          The parent object type of this {@link Property}.
 * @param <V>
 *          The type of the child value this {@link Property} represents.
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

    // ###########################################################################################################
    // ############################################### EXISTENCE #################################################
    // ###########################################################################################################

    /**
     * Returns whether this {@link Property} is reachable, so the property itself might be null, but all the of the
     * objects it is read from aren't.
     *
     * @param object
     *          The instance to lookup the property from; might be null.
     * @return
     *          True if all of this {@link Property}'s parent objects are not null, false otherwise
     * @throws UnreferencedPropertyPathException
     *          If there is any referenced property in this {@link Property} value's path.
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
     *          The instance to lookup the property from; might be null.
     * @param context
     *          The {@link Context} that should be used to satisfy the referenced properties from the root object to
     *          this {@link Property}'s value; might be null.
     * @return
     *          True if all of this {@link Property}'s parent objects are not null, false otherwise
     * @throws UnreferencedPropertyPathException
     *          If there is any referenced property in this {@link Property} value's path.
     * @throws OutboundPropertyPathException
     *          If there is a referenced property in this {@link Property} value's path that has a
     *          {@link com.mantledillusion.data.epiphy.context.reference.PropertyReference} included in the  given
     *          {@link Context} that does not match that {@link Property} value's bounds.
     */
    boolean exists(O object, Context context)
            throws UnreferencedPropertyPathException, OutboundPropertyPathException;

    /**
     * Returns whether this {@link Property}'s value or any parent object is null.
     * <p>
     * Equals {@link #exists(Object)}, with the addition that the value is not allowed to be null as well as the
     * parents.
     *
     * @param object
     *          The instance to lookup the property from; might be null.
     * @return
     *          True if this {@link Property} or any of its parents is null, false otherwise
     * @throws UnreferencedPropertyPathException
     *          If there is any referenced property in this {@link Property} value's path.
     */
    default boolean isNull(O object)
            throws UnreferencedPropertyPathException {
        return get(object, true) == null;
    }

    /**
     * Returns whether this {@link Property}'s value or any parent object is null.
     * <p>
     * Equals {@link #exists(Object, Context)}, with the addition that the value is not allowed to be null as well as
     * the parents.
     *
     * @param object
     *          The instance to lookup the property from; might be null.
     * @param context
     *          The {@link Context} that should be used to satisfy the referenced properties from the root object to
     *          this {@link Property}'s value; might be null.
     * @return
     *          True if this {@link Property} or any of its parents is null, false otherwise
     * @throws UnreferencedPropertyPathException
     *          If there is any referenced property in this {@link Property} value's path.
     * @throws OutboundPropertyPathException
     *          If there is a referenced property in this {@link Property} value's path that has a
     *          {@link com.mantledillusion.data.epiphy.context.reference.PropertyReference} included in the  given
     *          {@link Context} that does not match that {@link Property} value's bounds.
     */
    default boolean isNull(O object, Context context)
            throws UnreferencedPropertyPathException, OutboundPropertyPathException {
        return get(object, context, true) == null;
    }

    /**
     * Returns whether this {@link Property} was created including a {@link Setter}, so it is writable.
     *
     * @return True if this {@link Property} is writeable, false otherwise
     */
    boolean isWritable();

    // ###########################################################################################################
    // ################################################ GETTING ##################################################
    // ###########################################################################################################

    /**
     * Retrieves this {@link Property}'s value out of the given object.
     *
     * @param object
     *          The instance to lookup the property value from; might be null.
     * @return
     *          This {@link Property}'s retrieved value; might return null if the value is null
     * @throws InterruptedPropertyPathException
     *          If any object on the path to this {@link Property}'s value is null.
     * @throws UnreferencedPropertyPathException
     *          If there is any referenced property in this {@link Property} value's path.
     */
    default V get(O object)
            throws InterruptedPropertyPathException, UnreferencedPropertyPathException {
        return get(object, null, false);
    }

    /**
     * Retrieves this {@link Property}'s value out of the given object.
     *
     * @param object
     *          The instance to lookup the property value from; might be null.
     * @param allowNull
     *          Whether or not any parent object of this property is allowed to be null. If set to true, instead of
     *          throwing an {@link InterruptedPropertyPathException}, the method will just return null.
     * @return
     *          This {@link Property}'s retrieved value; might return null if the value is null or if a parent object
     *          is null and allowNull is set to true
     * @throws InterruptedPropertyPathException
     *          If any object on the path to this {@link Property}'s value is null and allowNull is set to false.
     * @throws UnreferencedPropertyPathException
     *          If there is any referenced property in this {@link Property} value's path.
     */
    default V get(O object, boolean allowNull)
            throws InterruptedPropertyPathException, UnreferencedPropertyPathException {
        return get(object, null, allowNull);
    }

    /**
     * Retrieves this {@link Property}'s value out of the given object.
     *
     * @param object
     *          The instance to lookup the property value from; might be null.
     * @param context
     *          The {@link Context} that should be used to satisfy the referenced properties from the root object to
     *          this {@link Property}'s value; might be null.
     * @return
     *          This {@link Property}'s retrieved value; might return null if the value is null
     * @throws InterruptedPropertyPathException
     *          If any object on the path to this {@link Property}'s value is null.
     * @throws UnreferencedPropertyPathException
     *          If there is any referenced property in this {@link Property} value's path that does not have a
     *          {@link com.mantledillusion.data.epiphy.context.reference.PropertyReference} included in the given
     *          {@link Context}.
     * @throws OutboundPropertyPathException
     *          If there is a referenced property in this {@link Property} value's path that has a
     *          {@link com.mantledillusion.data.epiphy.context.reference.PropertyReference} included in the given
     *          {@link Context} that does not match that {@link Property} value's bounds.
     */
    default V get(O object, Context context)
            throws InterruptedPropertyPathException, UnreferencedPropertyPathException, OutboundPropertyPathException {
        return get(object, context, false);
    }

    /**
     * Retrieves this {@link Property}'s value out of the given object.
     *
     * @param object
     *          The instance to lookup the property value from; might be null.
     * @param context
     *          The {@link Context} that should be used to satisfy the referenced properties from the root object to
     *          this {@link Property}'s value; might be null.
     * @param allowNull
     *          Whether or not any parent object of this property is allowed to be null. If set to true, instead of
     *          throwing an {@link InterruptedPropertyPathException}, the method will just return null.
     * @return
     *          This {@link Property}'s retrieved value; might return null if the value is null or if a parent object
     *          is null and allowNull is set to true
     * @throws InterruptedPropertyPathException
     *          If any object on the path to this {@link Property}'s value is null and allowNull is set to false.
     * @throws UnreferencedPropertyPathException
     *          If there is any referenced property in this {@link Property} value's path that does not have a
     *          {@link com.mantledillusion.data.epiphy.context.reference.PropertyReference} included in the given
     *          {@link Context}.
     * @throws OutboundPropertyPathException
     *          If there is a referenced property in this {@link Property} value's path that has a
     *          {@link com.mantledillusion.data.epiphy.context.reference.PropertyReference} included in the given
     *          {@link Context} that does not match that {@link Property} value's bounds.
     */
    V get(O object, Context context, boolean allowNull)
            throws InterruptedPropertyPathException, UnreferencedPropertyPathException, OutboundPropertyPathException;

    // ###########################################################################################################
    // ################################################ SETTING ##################################################
    // ###########################################################################################################

    /**
     * Writes the given value to this {@link Property} in the given object.
     * <p>
     * Note that this is a writing operation; the property has to {@link #exists(Object, Context)} in the given object.
     *
     * @param object
     *          The instance to write the property value to; might be null.
     * @param value
     *          The value to set; might be null.
     * @throws InterruptedPropertyPathException
     *          If any object on the path to this {@link Property}'s value is null.
     * @throws UnreferencedPropertyPathException
     *          If there is any referenced property in this {@link Property} value's path.
     * @throws ReadonlyPropertyException
     *          If this {@link Property} does not have a {@link Setter} defined to write its value with.
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
     *          The instance to write the property value to; might be null.
     * @param value
     *          The value to set; might be null.
     * @param context
     *          The {@link Context} that should be used to satisfy the referenced properties from the root object to
     *          this {@link Property}'s value; might be null.
     * @throws InterruptedPropertyPathException
     *          If any object on the path to this {@link Property}'s value is null.
     * @throws UnreferencedPropertyPathException
     *          If there is any referenced property in this {@link Property} value's path that does not have a
     *          {@link com.mantledillusion.data.epiphy.context.reference.PropertyReference} included in the given
     *          {@link Context}.
     * @throws OutboundPropertyPathException
     *          If there is a referenced property in this {@link Property} value's path that has a
     *          {@link com.mantledillusion.data.epiphy.context.reference.PropertyReference} included in the given
     *          {@link Context} that does not match that {@link Property} value's bounds.
     * @throws ReadonlyPropertyException
     *          If this {@link Property} does not have a {@link Setter} defined to write its value with.
     */
    void set(O object, V value, Context context)
            throws InterruptedPropertyPathException, UnreferencedPropertyPathException, OutboundPropertyPathException,
            ReadonlyPropertyException;

    // ###########################################################################################################
    // ################################################ PATHING ##################################################
    // ###########################################################################################################

    /**
     * Builds a path with this {@link Property} as the leading and the given {@link Property} as the trailing end.
     * <p>
     * A {@link Property} path also is a property, but with at least one intermediate object between the root object
     * and the value.
     *
     * @param <T>
     *          The value type of the path.
     * @param <P1>
     *          The {@link Property} type of the child.
     * @param <P2>
     *          The {@link Property} type of the path.
     * @param child
     *          The child to append to this {@link Property} to form the path; might <b>not</b> be null.
     * @return
     *          A new {@link Property} with the same type as the child and the root object type of this
     *          {@link Property}, never null
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
     * @param <S>
     *          The object type of the path.
     * @param parent
     *          The parent to prepent to this {@link Property} to form the path; might <b>not</b> be null.
     * @return
     *          A new {@link Property} with the same type as this {@link Property} and the root object type of the
     *          child, never null
     */
    <S> Property<S, V> prepend(Property<S, O> parent);

    Property<?, ?> getParent();

    /**
     * Returns a {@link Set} of all {@link Property}s in the parent hierarchy of this {@link Property}, including this
     * one.
     *
     * @return
     * 			A {@link Set} of {@link Property}s, never null, might be empty
     */
    Set<Property<?, ?>> getHierarchy();

    // ###########################################################################################################
    // ############################################## CONTEXTING #################################################
    // ###########################################################################################################

    /**
     * Returns the count of occurrences ({@link Property#exists(Object)} != null) there are of this {@link Property}
     * in the given object.
     *
     * @param object
     * 			The instance to check the value occurrences in; might be null.
     * @return
     * 			The occurrence count; always &gt;=0
     */
    int occurrences(O object);

    /**
     * Returns a {@link Collection} of {@link Context}s for every occurrence of this {@link Property} in the given
     * object.
     * <p>
     * The amount of returned {@link Context}s equals the result of {@link #occurrences(Object)} on the same object.
     *
     * @param object
     * 			The instance to check the value occurrences in; might be null.
     * @return
     * 			A {@link Collection} of {@link Context}s, never null, might be empty
     */
    default Collection<Context> contextualize(O object) {
        return contextualize(object, null,false);
    }

    /**
     * Returns a {@link Collection} of {@link Context}s for every occurrence of this {@link Property} in the given
     * object.
     * <p>
     * The amount of returned {@link Context}s equals the result of {@link #occurrences(Object)} on the same object.
     *
     * @param object
     * 			The instance to check the value occurrences in; might be null.
     * @param context
     *          The context to use as a base; might be null.
     * @return
     * 			A {@link Collection} of {@link Context}s, never null, might be empty
     */
    default Collection<Context> contextualize(O object, Context context) {
        return contextualize(object, context, false);
    }

    /**
     * Returns a {@link Collection} of {@link Context}s for every occurrence of this {@link Property} in the given
     * object.
     * <p>
     * The amount of returned {@link Context}s equals the result of {@link #occurrences(Object)} on the same object.
     *
     * @param object
     * 			The instance to check the value occurrences in; might be null.
     * @param includeNull
     *          Include {@link Context}s for all values where {@link #exists(Object, Context)} would return true, so
     *          they might be null.
     * @return
     * 			A {@link Collection} of {@link Context}s, never null, might be empty
     */
    default Collection<Context> contextualize(O object, boolean includeNull) {
        return contextualize(object, null, includeNull);
    }

    /**
     * Returns a {@link Collection} of {@link Context}s for every occurrence of this {@link Property} in the given
     * object.
     * <p>
     * The amount of returned {@link Context}s equals the result of {@link #occurrences(Object)} on the same object.
     *
     * @param object
     * 			The instance to check the value occurrences in; might be null.
     * @param context
     *          The context to use as a base; might be null.
     * @param includeNull
     *          Include {@link Context}s for all values where {@link #exists(Object, Context)} would return true, so
     *          they might be null.
     * @return
     * 			A {@link Collection} of {@link Context}s, never null, might be empty
     */
    Collection<Context> contextualize(O object, Context context, boolean includeNull);

    /**
     * Returns a {@link Collection} of {@link Context}s for every occurrence where this {@link Property} is represented
     * by the given value in the given object.
     *
     * @param object
     * 			The instance to check the value occurrences in; might be null.
     * @param value
     *          The value to look for; might be null.
     * @return
     * 			A {@link Collection} of {@link Context}s, never null, might be empty
     */
    default Collection<Context> contextualize(O object, V value) {
        return contextualize(object, value, null);
    }

    /**
     * Returns a {@link Collection} of {@link Context}s for every occurrence where this {@link Property} is represented
     * by the given value in the given object.
     *
     * @param object
     * 			The instance to check the value occurrences in; might be null.
     * @param value
     *          The value to look for; might be null, though to equal the {@link Property} has to
     *          {@link Property#exists(Object, Context)} in the {@link Context}.
     * @param context
     *          The context in which to find the given value; might be null.
     * @return
     * 			A {@link Collection} of {@link Context}s, never null, might be empty
     */
    Collection<Context> contextualize(O object, V value, Context context);

    /**
     * Returns a {@link Stream} of all of this {@link Property}'s values occurring in the given object.
     * <p>
     * The count of streamed values exactly matches the result of {@link #occurrences(Object)} on the same object.
     *
     * @param object
     * 			The instance to get the value occurrences from; might be null.
     * @return
     *          A {@link Stream} of values, never null, might be empty
     */
    default Stream<V> stream(O  object) {
        return contextualize(object).stream().
                map(context -> get(object, context, true));
    }

    /**
     * Returns a {@link Stream} of all of this {@link Property}'s values occurring in the given object.
     * <p>
     * The count of streamed values exactly matches the result of {@link #occurrences(Object)} on the same object.
     *
     * @param object
     * 			The instance to get the value occurrences from; might be null.
     * @param context
     *          The context to use as a base; might be null.
     * @return
     *          A {@link Stream} of values, never null, might be empty
     */
    default Stream<V> stream(O  object, Context context) {
        return contextualize(object, context).stream().
                map(ctx -> get(object, ctx, true));
    }

    /**
     * Returns a {@link Iterable} of all of this {@link Property}'s values occurring in the given object.
     * <p>
     * The count of iterable values exactly matches the result of {@link #occurrences(Object)} on the same object.
     *
     * @param object
     * 			The instance to get the value occurrences from; might be null.
     * @return
     *          A {@link Stream} of values, never null, might be empty
     */
    default Iterable<V> iterate(O object) {
        return () -> stream(object).iterator();
    }

    /**
     * Returns a {@link Iterable} of all of this {@link Property}'s values occurring in the given object.
     * <p>
     * The count of iterable values exactly matches the result of {@link #occurrences(Object)} on the same object.
     *
     * @param object
     * 			The instance to get the value occurrences from; might be null.
     * @param context
     *          The context to use as a base; might be null.
     * @return
     *          A {@link Stream} of values, never null, might be empty
     */
    default Iterable<V> iterate(O object, Context context) {
        return () -> stream(object, context).iterator();
    }

    /**
     * Returns the predecessor of the given value in the given object.
     *
     * @param object
     *          The object to get the value from; might be null.
     * @param value
     *          The value to search a predecessor for; might be null.
     * @return
     *          The predecessor of the given value, might be null if the given value is the first element
     * @throws IllegalArgumentException
     *          If the given value is not a value of the given object.
     */
    default V predecessor(O object, V value) throws IllegalArgumentException {
        V predecessor = null;
        for (V element: iterate(object)) {
            if (element == value) {
                return predecessor;
            } else {
                predecessor = element;
            }
        }
        throw new IllegalArgumentException("The given value '" + value +
                "' is not an element of the given object '" +  object + "'");
    }

    /**
     * Returns the successor of the given value in the given object.
     *
     * @param object
     *          The object to get the value from; might be null.
     * @param value
     *          The value to search a successor for; might be null.
     * @return
     *          The successor of the given value, might be null if the given value is the last element
     * @throws IllegalArgumentException
     *          If the given value is not a value of the given object.
     */
    default V successor(O object, V value) throws IllegalArgumentException {
        boolean next = false;
        for (V element: iterate(object)) {
            if (next) {
                return element;
            } else if (element == value) {
                next = true;
            }
        }
        if (next) {
            return null;
        } else {
            throw new IllegalArgumentException("The given value '" + value +
                    "' is not an element of the given object '" + object + "'");
        }
    }

    // ###########################################################################################################
    // ################################################# MISC ####################################################
    // ###########################################################################################################

    @Override
    String toString();

    @Override
    int hashCode();

    @Override
    boolean equals(Object obj);
}
