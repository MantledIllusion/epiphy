package com.mantledillusion.data.epiphy.interfaces;

import com.mantledillusion.data.epiphy.exception.InterruptedPropertyPathException;
import com.mantledillusion.data.epiphy.exception.UnindexedPropertyPathException;
import com.mantledillusion.data.epiphy.index.IndexContext;
import com.mantledillusion.data.epiphy.index.PropertyIndex;
import com.mantledillusion.data.epiphy.index.impl.DefaultIndexContext;
import com.mantledillusion.data.epiphy.io.IndexedGetter;

/**
 * Interface for types identifying a readable {@link Property} to a model.
 * 
 * @param <M>
 *            The root model type of this {@link Property}'s property tree.
 * @param <T>
 *            The type of the property this {@link Property} represents.
 */
public interface ReadableProperty<M, T> extends Property<M, T> {

	public IndexedGetter<?, T> getter();
	
	/**
	 * Returns whether this {@link ReadableProperty}'s property is null,
	 * including the case when a parent property is null.
	 * 
	 * @param model
	 *            The model to lookup the property from; might be null.
	 * @return True if this {@link ReadableProperty} or any of its parents is
	 *         null, false if it has a non-null value
	 */
	public default boolean isNull(M model) {
		return get(model, true) == null;
	}

	/**
	 * Returns whether this {@link ReadableProperty}'s property is null,
	 * including the case when a parent property is null.
	 * 
	 * @param model
	 *            The model to lookup the property from; might be null.
	 * @param context
	 *            The {@link IndexContext} that should be used to satisfy the listed
	 *            properties from the root property to his
	 *            {@link ReadableProperty}; might be null.
	 * @return True if this {@link ReadableProperty} or any of its parents is
	 *         null, false if it has a non-null value
	 */
	public default boolean isNull(M model, IndexContext context) {
		return get(model, context, true) == null;
	}

	/**
	 * Retrieves this {@link ReadableProperty}'s property out of the given
	 * model.
	 * 
	 * @param model
	 *            The model to retrieve the property from; might <b>NOT</b> be null.
	 * @return The retrieved property; might return null if the property is null
	 * @throws InterruptedPropertyPathException
	 *             If any property on the path to this
	 *             {@link ReadableProperty} is null.
	 * @throws UnindexedPropertyPathException
	 *             If there is any listed property in this
	 *             {@link ReadableProperty}'s path.
	 */
	public default T get(M model) {
		return get(model, null, false);
	}

	/**
	 * Retrieves this {@link ReadableProperty}'s property out of the given
	 * model.
	 * 
	 * @param model
	 *            The model to retrieve the property from; might be null if
	 *            allowNull is set to true.
	 * @param allowNull
	 *            Allows any parent property of this property to be null. If set to
	 *            true, instead of throwing an
	 *            {@link InterruptedPropertyPathException}, the method will just
	 *            return null.
	 * @return The retrieved property; might return null if the property is null or
	 *         if a parent property is null and allowNull is set to true
	 * @throws InterruptedPropertyPathException
	 *             If any property on the path to this
	 *             {@link ReadableProperty} is null and allowNull is set to
	 *             false.
	 * @throws UnindexedPropertyPathException
	 *             If there is any listed property in this
	 *             {@link ReadableProperty}'s path.
	 */
	public default T get(M model, boolean allowNull) {
		return get(model, null, allowNull);
	}

	/**
	 * Retrieves this {@link ReadableProperty}'s property out of the given
	 * model, using the given {@link IndexContext}.
	 * 
	 * @param model
	 *            The model to retrieve the property from; might <b>NOT</b> be null.
	 * @param context
	 *            The {@link IndexContext} that should be used to satisfy the listed
	 *            properties from the root property to this
	 *            {@link ReadableProperty}; might be null.
	 * @return The retrieved property; might return null if the property is null
	 * @throws InterruptedPropertyPathException
	 *             If any property on the path to this
	 *             {@link ReadableProperty} is null.
	 * @throws UnindexedPropertyPathException
	 *             If there is any listed property in this
	 *             {@link ReadableProperty}'s path that does not have a
	 *             {@link PropertyIndex} included in the given {@link IndexContext}.
	 */
	public default T get(M model, IndexContext context) {
		return get(model, context, false);
	}

	/**
	 * Retrieves this {@link ReadableProperty}'s property out of the given
	 * model, using the given {@link IndexContext}.
	 * 
	 * @param model
	 *            The model to retrieve the property from; might be null if
	 *            allowNull is set to true.
	 * @param context
	 *            The {@link IndexContext} that should be used to satisfy the listed
	 *            properties from the root property to this
	 *            {@link ReadableProperty}; might be null.
	 * @param allowNull
	 *            Allows any parent property of this property to be null. If set to
	 *            true, instead of throwing an
	 *            {@link InterruptedPropertyPathException}, the method will just
	 *            return null.
	 * @return The retrieved property; might return null if the property is null or
	 *         if a parent property is null and allowNull is set to true
	 * @throws InterruptedPropertyPathException
	 *             If any property on the path to this
	 *             {@link ReadableProperty} is null and allowNull is set to
	 *             false.
	 * @throws UnindexedPropertyPathException
	 *             If there is any listed property in this
	 *             {@link ReadableProperty}'s path that does not have a
	 *             {@link PropertyIndex} included in the given {@link IndexContext}.
	 */
	@SuppressWarnings("unchecked")
	public default T get(M model, IndexContext context, boolean allowNull) {
		if (getParent() == null) {
			return (T) model;
		} else {
			context = context == null ? DefaultIndexContext.EMPTY : context;
			return PropertyUtils.castAndGet(this, model, context, allowNull);
		}
	}
}
