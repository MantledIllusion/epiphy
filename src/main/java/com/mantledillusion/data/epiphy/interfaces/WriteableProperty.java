package com.mantledillusion.data.epiphy.interfaces;

import com.mantledillusion.data.epiphy.exception.InterruptedPropertyPathException;
import com.mantledillusion.data.epiphy.exception.UnindexedPropertyPathException;
import com.mantledillusion.data.epiphy.index.IndexContext;
import com.mantledillusion.data.epiphy.index.PropertyIndex;
import com.mantledillusion.data.epiphy.index.impl.DefaultIndexContext;
import com.mantledillusion.data.epiphy.io.IndexedSetter;

public interface WriteableProperty<M, T> extends ReadableProperty<M, T> {

	public IndexedSetter<?, T> setter();
	
	/**
	 * Sets the given value to the property of the given model instance this
	 * {@link WriteableProperty} represents.
	 * <p>
	 * Note that this is a writing operation, so the property has to
	 * {@link #exists(Object)} in the model.
	 * 
	 * @param model
	 *            The model to set the property on; might <b>NOT</b> be null.
	 * @param value
	 *            The value to set; might be null.
	 * @throws InterruptedPropertyPathException
	 *             If any property on the path to this {@link WriteableProperty} is
	 *             null.
	 * @throws UnindexedPropertyPathException
	 *             If there is any listed property in this {@link WriteableProperty}'s
	 *             path.
	 */
	public default void set(M model, T value) {
		set(model, value, null);
	}

	/**
	 * Sets the given value to the property of the given model instance this
	 * {@link WriteableProperty} represents, using the given {@link IndexContext}.
	 * <p>
	 * Note that this is a writing operation, so the property has to
	 * {@link #exists(Object, IndexContext)} in the model.
	 * 
	 * @param model
	 *            The model to set the property on; might <b>NOT</b> be null.
	 * @param value
	 *            The value to set; might be null.
	 * @param context
	 *            The {@link IndexContext} that should be used to satisfy the listed
	 *            properties from the root property to this {@link WriteableProperty};
	 *            might be null.
	 * @throws InterruptedPropertyPathException
	 *             If any property on the path to this {@link WriteableProperty} is
	 *             null.
	 * @throws UnindexedPropertyPathException
	 *             If there is any listed property in this {@link WriteableProperty}'s
	 *             path that does not have a {@link PropertyIndex} included in the
	 *             given {@link IndexContext}.
	 */
	public default void set(M model, T value, IndexContext context) {
		if (getParent() != null) {
			context = context == null ? DefaultIndexContext.EMPTY : context;
			PropertyUtils.castAndSet(this, model, value, context);
		}
	}
}
