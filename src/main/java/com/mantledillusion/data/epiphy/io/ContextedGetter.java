package com.mantledillusion.data.epiphy.io;

import com.mantledillusion.data.epiphy.context.Context;
import com.mantledillusion.data.epiphy.exception.InterruptedPropertyPathException;

/**
 * Functional interface for retrieving a value from a parent value requiring a
 * {@link Context}.
 *
 * @param <P>
 *            The parent value type.
 * @param <T>
 *            The value type.
 */
@FunctionalInterface
public interface ContextedGetter<P, T> {

	/**
	 * Retrieves the value from the parent value.
	 * 
	 * @param source
	 *            The parent value that is the source to retrieve from; might
	 *            <b>not</b> be null.
	 * @param context
	 *            The {@link Context} to use; might <b>not</b> be null.
	 * @param allowNull
	 *            Allows the parent property to be null. If set to true, instead of
	 *            throwing an {@link InterruptedPropertyPathException}, the method
	 *            will just return null.
	 * @return The retrieved value.
	 */
	T get(P source, Context context, boolean allowNull);
}