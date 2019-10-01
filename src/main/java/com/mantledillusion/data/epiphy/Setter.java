package com.mantledillusion.data.epiphy;

/**
 * Functional interface for writing a value to an object.
 *
 * @param <O>
 *            The object type.
 * @param <V>
 *            The value type.
 */
@FunctionalInterface
public interface Setter<O, V> {

	/**
	 * Writes the value to the object.
	 *
	 * @param object
	 *            The object instance to write the value to; might <b>not</b> be null.
	 * @param value
	 *            The value to write; might be null.
	 */
	void set(O object, V value);
}