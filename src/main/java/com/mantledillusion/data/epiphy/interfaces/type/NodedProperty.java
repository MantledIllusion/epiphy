package com.mantledillusion.data.epiphy.interfaces.type;

import com.mantledillusion.data.epiphy.interfaces.ReadableProperty;
import com.mantledillusion.data.epiphy.interfaces.function.ContextableProperty;
import com.mantledillusion.data.epiphy.interfaces.function.PopulateableProperty;

/**
 * Interface for properties that represent a noded, multi instance
 * {@link ReadableProperty}.
 *
 * @param <M>
 *            The root model type of this {@link NodedProperty}'s property tree.
 * @param <T>
 *            The type of the property this {@link NodedProperty} represents.
 * @see PopulateableProperty
 */
public interface NodedProperty<M, T>
		extends ReadableProperty<M, T>, ContextableProperty<M, T, int[], Integer>, PopulateableProperty<M, T> {

}
