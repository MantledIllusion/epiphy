package com.mantledillusion.data.epiphy.io;

import com.mantledillusion.data.epiphy.context.Context;
import com.mantledillusion.data.epiphy.exception.InterruptedPropertyPathException;

/**
 * Functional interface for setting a value to a parent value requiring a
 * {@link Context}.
 *
 * @param <P>
 *            The parent value type.
 * @param <T>
 *            The value type.
 */
@FunctionalInterface
public interface ContextedSetter<P, T> {

	/**
	 * Sets the value to the parent value.
	 * 
	 * @param target
	 *            The parent value that is the target to set the value to; might
	 *            <b>not</b> be null.
	 * @param context
	 *            The {@link Context} to use; might <b>not</b> be null.
	 * @param allowNull
	 *            Allows the parent property to be null. If set to true, instead of
	 *            throwing an {@link InterruptedPropertyPathException}, the method
	 *            will just return null.
	 * @param value
	 *            The value to set; might be null.
	 */
	void set(P target, T value, Context context);
}