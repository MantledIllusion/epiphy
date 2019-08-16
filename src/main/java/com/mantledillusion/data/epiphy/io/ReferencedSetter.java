package com.mantledillusion.data.epiphy.io;

import com.mantledillusion.data.epiphy.Property;
import com.mantledillusion.data.epiphy.context.Context;

/**
 * Functional interface for setting a value to a parent value requiring a
 * {@link Context}.
 *
 * @param <O>
 *            The parent value type.
 * @param <V>
 *            The value type.
 */
@FunctionalInterface
public interface ReferencedSetter<O, V> {

	/**
	 * Sets the value to the parent value.
	 * 
	 * @param instance
	 *            The parent value that is the target to set the value to; might
	 *            <b>not</b> be null.
	 * @param value
	 *            The value to set; might be null.
	 * @param context
	 *            The {@link Context} to use; might <b>not</b> be null.
	 */
	void set(Property<O, V> property, O instance, V value, Context context);
}