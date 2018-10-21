package com.mantledillusion.data.epiphy.interfaces;

import com.mantledillusion.data.epiphy.context.Context;
import com.mantledillusion.data.epiphy.context.PropertyIndex;
import com.mantledillusion.data.epiphy.exception.InterruptedPropertyPathException;
import com.mantledillusion.data.epiphy.exception.UncontextedPropertyPathException;

/**
 * Interface for properties representing a writeable property of a model.
 *
 * @param <M>
 *            The root model type of this {@link WriteableProperty}'s property
 *            tree.
 * @param <T>
 *            The type of the property this {@link WriteableProperty}
 *            represents.
 */
public interface WriteableProperty<M, T> extends ReadableProperty<M, T> {

	/**
	 * Sets the given value to the property of the given model instance this
	 * {@link WriteableProperty} represents.
	 * <p>
	 * Note that this is a writing operation, so the property has to
	 * {@link #exists(Object)} in the model.
	 * 
	 * @param model
	 *            The model to set the property on; might <b>not</b> be null.
	 * @param value
	 *            The value to set; might be null.
	 * @throws InterruptedPropertyPathException
	 *             If any property on the path to this {@link WriteableProperty} is
	 *             null.
	 * @throws UncontextedPropertyPathException
	 *             If there is any listed property in this
	 *             {@link WriteableProperty}'s path.
	 */
	public default void set(M model, T value) {
		set(model, value, null);
	}

	/**
	 * Sets the given value to the property of the given model instance this
	 * {@link WriteableProperty} represents, using the given {@link Context}.
	 * <p>
	 * Note that this is a writing operation, so the property has to
	 * {@link #exists(Object, Context)} in the model.
	 * 
	 * @param model
	 *            The model to set the property on; might <b>not</b> be null.
	 * @param value
	 *            The value to set; might be null.
	 * @param context
	 *            The {@link Context} that should be used to satisfy the listed
	 *            properties from the root property to this
	 *            {@link WriteableProperty}; might be null.
	 * @throws InterruptedPropertyPathException
	 *             If any property on the path to this {@link WriteableProperty} is
	 *             null.
	 * @throws UncontextedPropertyPathException
	 *             If there is any listed property in this
	 *             {@link WriteableProperty}'s path that does not have a
	 *             {@link PropertyIndex} included in the given {@link Context}.
	 */
	public void set(M model, T value, Context context);
}
