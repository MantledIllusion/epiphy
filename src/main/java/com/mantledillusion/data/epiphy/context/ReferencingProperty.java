package com.mantledillusion.data.epiphy.context;

import com.mantledillusion.data.epiphy.Property;

/**
 * Interface for properties that carry referencable elements.
 *
 * @param <O>
 *            The root parent object type of this {@link Property}.
 * @param <V>
 *     		  The type of the child value this {@link Property} represents.
 * @param <E>
 *            The type of the property element this {@link ReferencingProperty} represents.
 * @param <R>
 *            The type of the context reference.
 */
public interface ReferencingProperty<O, V, E, R> extends Property<O, V> {

    default boolean contains(O object, R reference) {
        return contains(object, reference, null);
    }

    /**
     * Returns whether there is an element contained by the given object that is referenced by the given reference.
     *
     * @param object
     *          The object to check in; might <b>not</b> be null.
     * @param reference
     * 			The reference to check for; might be null.
     * @param context
     * 			The {@link Context} that should be used to satisfy the contexted properties from the root property to
     * 			this {@link ReferencingProperty}; might be null.
     * @return True if the given object contains an element for the given reference, false otherwise
     */
    boolean contains(O object, R reference, Context context);
}