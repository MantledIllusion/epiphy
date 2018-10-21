package com.mantledillusion.data.epiphy.interfaces.type;

import com.mantledillusion.data.epiphy.context.Context;
import com.mantledillusion.data.epiphy.context.PropertyIndex;
import com.mantledillusion.data.epiphy.exception.InterruptedPropertyPathException;
import com.mantledillusion.data.epiphy.exception.UncontextedPropertyPathException;
import com.mantledillusion.data.epiphy.interfaces.ReadableProperty;
import com.mantledillusion.data.epiphy.interfaces.function.EnumerableProperty;
import com.mantledillusion.data.epiphy.interfaces.function.PopulateableProperty;

/**
 * Interface for properties that represent a noded, multi instance
 * {@link ReadableProperty}.
 *
 * @param <M>
 *            The root model type of this {@link NodedProperty}'s property tree.
 * @param <T>
 *            The type of the property this {@link NodedProperty} represents.
 * @see PopulateableProperty
 */
public interface NodedProperty<M, T>
		extends ReadableProperty<M, T>, EnumerableProperty<M, T, int[]>, PopulateableProperty<M, T> {

	/**
	 * Returns the root element of the node tree this {@link NodedProperty} refers
	 * to.
	 * 
	 * @param model
	 *            The model to retrieve the property from; might <b>not</b> be null.
	 * @return The node root, might return null if the property is null
	 * @throws InterruptedPropertyPathException
	 *             If any property on the path to this {@link ReadableProperty} is
	 *             null.
	 * @throws UncontextedPropertyPathException
	 *             If there is any listed property in this
	 *             {@link ReadableProperty}'s path.
	 */
	default T getNodeRoot(M model) {
		return getNodeRoot(model, null, false);
	}

	/**
	 * Returns the root element of the node tree this {@link NodedProperty} refers
	 * to.
	 * 
	 * @param model
	 *            The model to retrieve the property from; might be null if
	 *            allowNull is set to true.
	 * @param allowNull
	 *            Allows any parent property of this property to be null. If set to
	 *            true, instead of throwing an
	 *            {@link InterruptedPropertyPathException}, the method will just
	 *            return null.
	 * @return The node root, might return null if the property is null or if a
	 *         parent property is null and allowNull is set to true
	 * @throws InterruptedPropertyPathException
	 *             If any property on the path to this {@link ReadableProperty} is
	 *             null and allowNull is set to false.
	 * @throws UncontextedPropertyPathException
	 *             If there is any listed property in this
	 *             {@link ReadableProperty}'s path.
	 */
	default T getNodeRoot(M model, boolean allowNull) {
		return getNodeRoot(model, null, allowNull);
	}

	/**
	 * Returns the root element of the node tree this {@link NodedProperty} refers
	 * to.
	 * 
	 * @param model
	 *            The model to retrieve the property from; might <b>not</b> be null.
	 * @param context
	 *            The {@link Context} that should be used to satisfy the listed
	 *            properties from the root property to this
	 *            {@link ReadableProperty}; might be null.
	 * @return The node root, might return null if the property is null
	 * @throws InterruptedPropertyPathException
	 *             If any property on the path to this {@link ReadableProperty} is
	 *             null.
	 * @throws UncontextedPropertyPathException
	 *             If there is any listed property in this
	 *             {@link ReadableProperty}'s path that does not have a
	 *             {@link PropertyIndex} included in the given {@link Context}.
	 */
	default T getNodeRoot(M model, Context context) {
		return getNodeRoot(model, context, false);
	}

	/**
	 * Returns the root element of the node tree this {@link NodedProperty} refers
	 * to.
	 * 
	 * @param model
	 *            The model to retrieve the property from; might be null if
	 *            allowNull is set to true.
	 * @param context
	 *            The {@link Context} that should be used to satisfy the listed
	 *            properties from the root property to this
	 *            {@link ReadableProperty}; might be null.
	 * @param allowNull
	 *            Allows any parent property of this property to be null. If set to
	 *            true, instead of throwing an
	 *            {@link InterruptedPropertyPathException}, the method will just
	 *            return null.
	 * @return The node root, might return null if the property is null or if a
	 *         parent property is null and allowNull is set to true
	 * @throws InterruptedPropertyPathException
	 *             If any property on the path to this {@link ReadableProperty} is
	 *             null and allowNull is set to false.
	 * @throws UncontextedPropertyPathException
	 *             If there is any listed property in this
	 *             {@link ReadableProperty}'s path that does not have a
	 *             {@link PropertyIndex} included in the given {@link Context}.
	 */
	T getNodeRoot(M model, Context context, boolean allowNull);
}