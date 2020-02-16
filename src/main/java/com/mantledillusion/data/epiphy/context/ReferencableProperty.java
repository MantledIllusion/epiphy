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
 *            The type of the property element this {@link ReferencableProperty} represents.
 * @param <R>
 *            The type of the context reference.
 */
public interface ReferencableProperty<O, V, E, R> extends Property<O, V> {

}