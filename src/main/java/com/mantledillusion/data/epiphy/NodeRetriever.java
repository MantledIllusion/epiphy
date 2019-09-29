package com.mantledillusion.data.epiphy;

/**
 * Interface for the special case of a {@link Property} that whose object and value are of the same type, so the
 * {@link NodeRetriever} is able to access the next deeper level of a node tree.
 *
 * @param <N>
 *            Type representing both the object and the value type of this {@link Property}.
 */
public interface NodeRetriever<N> extends Property<N, N> {

}
