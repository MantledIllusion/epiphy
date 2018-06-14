package com.mantledillusion.data.epiphy.interfaces.type;

import com.mantledillusion.data.epiphy.interfaces.ReadableProperty;
import com.mantledillusion.data.epiphy.interfaces.function.PopulateableProperty;

/**
 * Interface for properties that represent a definite, single instance
 * {@link ReadableProperty}.
 *
 * @param <M>
 *            The root model type of this {@link DefiniteProperty}'s property
 *            tree.
 * @param <T>
 *            The type of the property this {@link DefiniteProperty} represents.
 * @see PopulateableProperty
 */
public interface DefiniteProperty<M, T> extends ReadableProperty<M, T>, PopulateableProperty<M, T> {

}
