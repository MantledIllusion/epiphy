package com.mantledillusion.data.epiphy.io;

/**
 * Functional interface for setting a value to a parent value.
 *
 * @param <O>
 *            The parent value type.
 * @param <V>
 *            The value type.
 */
@FunctionalInterface
public interface Setter<O, V> {

	/**
	 * Sets the value to the parent value.
	 * 
	 * @param instance
	 *            The parent value that is the target to set the value to; might
	 *            <b>not</b> be null.
	 * @param value
	 *            The value to set; might be null.
	 */
	void set(O instance, V value);
}