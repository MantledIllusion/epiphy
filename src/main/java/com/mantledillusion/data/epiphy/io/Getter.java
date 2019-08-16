package com.mantledillusion.data.epiphy.io;

/**
 * Functional interface for retrieving a value from a parent value.
 *
 * @param <O>
 *            The parent value type.
 * @param <V>
 *            The value type.
 */
@FunctionalInterface
public interface Getter<O, V> {

	/**
	 * Retrieves the value from the parent value.
	 * 
	 * @param instance
	 *            The parent value that is the source to retrieve from; might
	 *            <b>not</b> be null.
	 * @return The retrieved value.
	 */
	V get(O instance);
}