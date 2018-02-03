package com.mantledillusion.data.epiphy.io;

/**
 * Functional interface for setting a value to a parent value.
 *
 * @param <P>
 *            The parent value type.
 * @param <T>
 *            The value type.
 */
@FunctionalInterface
public interface Setter<P, T> {

	/**
	 * Sets the value to the parent value.
	 * 
	 * @param target
	 *            The parent value that is the target to set the value to; might
	 *            <b>NOT</b> be null.
	 * @param value
	 *            The value to set; might be null.
	 */
	void set(P target, T value);
}