package com.mantledillusion.data.epiphy.interfaces.function;

import com.mantledillusion.data.epiphy.context.Context;
import com.mantledillusion.data.epiphy.context.PropertyIndex;
import com.mantledillusion.data.epiphy.context.PropertyReference;
import com.mantledillusion.data.epiphy.exception.InterruptedPropertyPathException;
import com.mantledillusion.data.epiphy.exception.OutboundPropertyPathException;
import com.mantledillusion.data.epiphy.exception.UncontextedPropertyPathException;
import com.mantledillusion.data.epiphy.interfaces.ReadableProperty;

/**
 * Interface for properties that carry elements which can be reached by using a
 * {@link Context}.
 *
 * @param <M>
 *            The root model type of this {@link ContextableProperty}'s property
 *            tree.
 * @param <E>
 *            The type of the property element this {@link ContextableProperty}
 *            represents.
 * @param <R>
 *            The type of the context reference.
 */
public interface ContextableProperty<M, E, R> extends IdentifyableProperty {

	/**
	 * Returns the size of the contextable property without context.
	 * 
	 * @param model
	 *            The model to add the element to; might <b>not</b> be null.
	 * @return The size of the property
	 * @throws InterruptedPropertyPathException
	 *             If any property on the path to this {@link ContextableProperty}
	 *             is null.
	 * @throws UncontextedPropertyPathException
	 *             If there is any uncontexted property in this
	 *             {@link ContextableProperty}'s path that does not have a
	 *             {@link PropertyIndex} included in the given {@link Context}.
	 */
	public default int size(M model) {
		return size(model, null);
	}

	/**
	 * Returns the size of the contextable property at the given context.
	 * 
	 * @param model
	 *            The model to add the element to; might <b>not</b> be null.
	 * @param context
	 *            The {@link Context} that should be used to satisfy the contexted
	 *            properties from the root property to this
	 *            {@link ContextableProperty}; might be null.
	 * @return The size of the property
	 * @throws InterruptedPropertyPathException
	 *             If any property on the path to this {@link ContextableProperty}
	 *             is null.
	 * @throws UncontextedPropertyPathException
	 *             If there is any uncontexted property in this
	 *             {@link ContextableProperty}'s path that does not have a
	 *             {@link PropertyIndex} included in the given {@link Context}.
	 * @throws OutboundPropertyPathException
	 *             If the property reference provided by the given {@link Context} is out of bounds
	 *             on the given model's elements this {@link ContextableProperty}
	 *             represents.
	 */
	public int size(M model, Context context);

	/**
	 * Adds the given element to the elements represented by this
	 * {@link ContextableProperty} in the given model.
	 * <p>
	 * The {@link PropertyReference} of this {@link ContextableProperty} in the
	 * given context is used to determine where to add the given element.
	 * <p>
	 * Note that this is a writing operation, so the property has to
	 * {@link ReadableProperty#exists(Object, Context)} in the model.
	 * 
	 * @param model
	 *            The model to add the element to; might <b>not</b> be null.
	 * @param element
	 *            The element to add; might be null.
	 * @param context
	 *            The {@link Context} that should be used to satisfy the contexted
	 *            properties from the root property to this
	 *            {@link ContextableProperty}; might <b>not</b> be null.
	 * @throws InterruptedPropertyPathException
	 *             If any property on the path to this {@link ContextableProperty}
	 *             is null.
	 * @throws UncontextedPropertyPathException
	 *             If there is any uncontexted property in this
	 *             {@link ContextableProperty}'s path that does not have a
	 *             {@link PropertyIndex} included in the given {@link Context}.
	 * @throws OutboundPropertyPathException
	 *             If the property reference provided by the given {@link Context} is out of bounds
	 *             on the given model's elements this {@link ContextableProperty}
	 *             represents.
	 */
	public R addAt(M model, E element, Context context);

	/**
	 * Removes the element from its elements that is represented by this
	 * {@link ContextableProperty} in the given model.
	 * <p>
	 * The {@link PropertyReference} of this {@link ContextableProperty} in the
	 * given context is used to determine where to remove the given element.
	 * <p>
	 * Note that this is a writing operation, so the property has to
	 * {@link ReadableProperty#exists(Object, Context)} in the model.
	 * 
	 * @param model
	 *            The model to remove the element from; might <b>not</b> be null.
	 * @param context
	 *            The {@link Context} that should be used to satisfy the contexted
	 *            properties from the root property to this
	 *            {@link ContextableProperty}; might <b>not</b> be null.
	 * @return The element that has been removed; might be null if the removed
	 *         element was null or the elements were empty, so nothing could be
	 *         removed
	 * @throws InterruptedPropertyPathException
	 *             If any property on the path to this {@link ContextableProperty}
	 *             is null.
	 * @throws UncontextedPropertyPathException
	 *             If there is any uncontexted property in this
	 *             {@link ContextableProperty}'s path that does not have a
	 *             {@link PropertyIndex} included in the given {@link Context}.
	 * @throws OutboundPropertyPathException
	 *             If the property reference provided by the given {@link Context} is out of bounds
	 *             on the given model's elements this {@link ContextableProperty}
	 *             represents.
	 */
	public E removeAt(M model, Context context);

	/**
	 * Removes the element from its elements that is represented by this
	 * {@link ContextableProperty} in the given model.
	 * <p>
	 * The element is removed by equality; the equality is determined in relation to
	 * the given element.
	 * <p>
	 * Note that this is a writing operation, so the property has to
	 * {@link ReadableProperty#exists(Object)} in the model.
	 * 
	 * @param model
	 *            The model to remove the element from; might <b>not</b> be null.
	 * @param element
	 *            The element to remove. If it is contained by the elements multiple
	 *            times, the first occurrence is removed; might be null.
	 * @return The property reference of the element that has been removed; might be null if the
	 *         given element was not included in its element
	 * @throws InterruptedPropertyPathException
	 *             If any property on the path to this {@link ContextableProperty}
	 *             is null.
	 * @throws UncontextedPropertyPathException
	 *             If there is any uncontexted property in this
	 *             {@link ContextableProperty}'s path.
	 */
	public default R remove(M model, E element) {
		return remove(model, element, null);
	}

	/**
	 * Removes the element from its elements that is represented by this
	 * {@link ContextableProperty} in the given model.
	 * <p>
	 * The element is removed by equality; the equality is determined in relation to
	 * the given element.
	 * <p>
	 * Note that this is a writing operation, so the property has to
	 * {@link ReadableProperty#exists(Object, Context)} in the model.
	 * 
	 * @param model
	 *            The model to remove the element from; might <b>not</b> be null.
	 * @param element
	 *            The element to remove. If it is contained by the elements multiple
	 *            times, the first occurrence is removed; might be null.
	 * @param context
	 *            The {@link Context} that should be used to satisfy the contexted
	 *            properties from the root property to this
	 *            {@link ContextableProperty}; might be null.
	 * @return The property reference of the element that has been removed; might be
	 *         null if the given element was not included in its element
	 * @throws InterruptedPropertyPathException
	 *             If any property on the path to this {@link ContextableProperty}
	 *             is null.
	 * @throws UncontextedPropertyPathException
	 *             If there is any uncontexted property in this
	 *             {@link ContextableProperty}'s path that does not have a
	 *             {@link PropertyIndex} included in the given {@link Context}.
	 * @throws OutboundPropertyPathException
	 *             If the property reference provided by the given {@link Context}
	 *             is out of bounds on the given model's elements this
	 *             {@link ContextableProperty} represents.
	 */
	public R remove(M model, E element, Context context);
}
