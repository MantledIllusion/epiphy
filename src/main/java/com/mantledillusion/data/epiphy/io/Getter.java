package com.mantledillusion.data.epiphy.io;

/**
 * Functional interface for retrieving a value from a parent value.
 *
 * @param <P>
 *            The parent value type.
 * @param <T>
 *            The value type.
 */
@FunctionalInterface
public interface Getter<P, T> {

	/**
	 * Retrieves the value from the parent value.
	 * 
	 * @param source
	 *            The parent value that is the source to retrieve from; might
	 *            <b>not</b> be null.
	 * @return The retrieved value.
	 */
	T get(P source);
}