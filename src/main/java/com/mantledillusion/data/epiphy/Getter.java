package com.mantledillusion.data.epiphy;

/**
 * Functional interface for retrieving a value from an object.
 *
 * @param <O>
 *            The object type.
 * @param <V>
 *            The value type.
 */
@FunctionalInterface
public interface Getter<O, V> {

	/**
	 * Retrieves the value from the object.
	 * 
	 * @param object
	 *            The object instance to retrieve the value from; might <b>not</b> be null.
	 * @return The retrieved value.
	 */
	V get(O object);
}