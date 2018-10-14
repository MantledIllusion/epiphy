package com.mantledillusion.data.epiphy.interfaces.function;

import com.mantledillusion.data.epiphy.context.Context;
import com.mantledillusion.data.epiphy.context.PropertyIndex;
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
 * @param <K>
 *            The type of the context key.
 * @param <O>
 *            The type of the operation parameter.
 */
public interface ContextableProperty<M, E, K, O> extends IdentifyableProperty {

	/**
	 * Returns the key at the specified context.
	 * 
	 * @param model
	 *            The model to get the key at; might <b>NOT</b> be null.
	 * @return The key at the specified context, might be null if the property does
	 *         not exist in the given model
	 * @throws InterruptedPropertyPathException
	 *             If any property on the path to this {@link ContextableProperty}
	 *             is null.
	 * @throws UncontextedPropertyPathException
	 *             If there is any uncontexted property in this
	 *             {@link ContextableProperty}'s path that does not have a
	 *             {@link PropertyIndex} included in the given {@link Context}.
	 * @throws OutboundPropertyPathException
	 *             If the key provided by the given {@link Context} is out of bounds
	 *             on the given model's elements this {@link ContextableProperty}
	 *             represents.
	 */
	public default K keyAt(M model) {
		return keyAt(model, null, null);
	}

	/**
	 * Returns the key at the specified context.
	 * 
	 * @param model
	 *            The model to get the key at; might <b>NOT</b> be null.
	 * @param operator
	 *            The operator; might be null
	 * @return The key at the specified context, might be null if the property does
	 *         not exist in the given model
	 * @throws InterruptedPropertyPathException
	 *             If any property on the path to this {@link ContextableProperty}
	 *             is null.
	 * @throws UncontextedPropertyPathException
	 *             If there is any uncontexted property in this
	 *             {@link ContextableProperty}'s path that does not have a
	 *             {@link PropertyIndex} included in the given {@link Context}.
	 * @throws OutboundPropertyPathException
	 *             If the key provided by the given {@link Context} is out of bounds
	 *             on the given model's elements this {@link ContextableProperty}
	 *             represents.
	 */
	public default K keyAt(M model, O operator) {
		return keyAt(model, operator, null);
	}

	/**
	 * Returns the key at the specified context.
	 * 
	 * @param model
	 *            The model to get the key at; might <b>NOT</b> be null.
	 * @param operator
	 *            The operator; might be null
	 * @param context
	 *            The {@link Context} that should be used to satisfy the contexted
	 *            properties from the root property to this
	 *            {@link ContextableProperty}; might be null.
	 * @return The key at the specified context, might be null if the property does
	 *         not exist in the given model
	 * @throws InterruptedPropertyPathException
	 *             If any property on the path to this {@link ContextableProperty}
	 *             is null.
	 * @throws UncontextedPropertyPathException
	 *             If there is any uncontexted property in this
	 *             {@link ContextableProperty}'s path that does not have a
	 *             {@link PropertyIndex} included in the given {@link Context}.
	 * @throws OutboundPropertyPathException
	 *             If the key provided by the given {@link Context} is out of bounds
	 *             on the given model's elements this {@link ContextableProperty}
	 *             represents.
	 */
	public K keyAt(M model, O operator, Context context);

	/**
	 * Returns the element at the specified context.
	 * 
	 * @param model
	 *            The model to get the element from; might <b>NOT</b> be null.
	 * @return The element at the given context; might be null
	 * @throws InterruptedPropertyPathException
	 *             If any property on the path to this {@link ContextableProperty}
	 *             is null.
	 * @throws UncontextedPropertyPathException
	 *             If there is any uncontexted property in this
	 *             {@link ContextableProperty}'s path that does not have a
	 *             {@link PropertyIndex} included in the given {@link Context}.
	 * @throws OutboundPropertyPathException
	 *             If the key provided by the given {@link Context} is out of bounds
	 *             on the given model's elements this {@link ContextableProperty}
	 *             represents.
	 */
	public default E elementAt(M model) {
		return elementAt(model, null, null);
	}

	/**
	 * Returns the element at the specified context.
	 * 
	 * @param model
	 *            The model to get the element from; might <b>NOT</b> be null.
	 * @param operator
	 *            The operator; might be null
	 * @return The element at the given context; might be null
	 * @throws InterruptedPropertyPathException
	 *             If any property on the path to this {@link ContextableProperty}
	 *             is null.
	 * @throws UncontextedPropertyPathException
	 *             If there is any uncontexted property in this
	 *             {@link ContextableProperty}'s path that does not have a
	 *             {@link PropertyIndex} included in the given {@link Context}.
	 * @throws OutboundPropertyPathException
	 *             If the key provided by the given {@link Context} is out of bounds
	 *             on the given model's elements this {@link ContextableProperty}
	 *             represents.
	 */
	public default E elementAt(M model, O operator) {
		return elementAt(model, operator, null);
	}

	/**
	 * Returns the element at the specified context.
	 * 
	 * @param model
	 *            The model to get the element from; might <b>NOT</b> be null.
	 * @param operator
	 *            The operator; might be null
	 * @param context
	 *            The {@link Context} that should be used to satisfy the contexted
	 *            properties from the root property to this
	 *            {@link ContextableProperty}; might be null.
	 * @return The element at the given context; might be null
	 * @throws InterruptedPropertyPathException
	 *             If any property on the path to this {@link ContextableProperty}
	 *             is null.
	 * @throws UncontextedPropertyPathException
	 *             If there is any uncontexted property in this
	 *             {@link ContextableProperty}'s path that does not have a
	 *             {@link PropertyIndex} included in the given {@link Context}.
	 * @throws OutboundPropertyPathException
	 *             If the key provided by the given {@link Context} is out of bounds
	 *             on the given model's elements this {@link ContextableProperty}
	 *             represents.
	 */
	public E elementAt(M model, O operator, Context context);

	/**
	 * Adds the given element to the elements represented by this
	 * {@link ContextableProperty} in the given model.
	 * <p>
	 * Note that this is a writing operation, so the property has to
	 * {@link ReadableProperty#exists(Object)} in the model.
	 * 
	 * @param model
	 *            The model to add the element to; might <b>NOT</b> be null.
	 * @param element
	 *            The element to add; might be null.
	 * @throws InterruptedPropertyPathException
	 *             If any property on the path to this {@link ContextableProperty}
	 *             is null.
	 * @throws UncontextedPropertyPathException
	 *             If there is any uncontexted property in this
	 *             {@link ContextableProperty}'s path.
	 */
	public default void add(M model, E element) {
		add(model, element, null);
	}

	/**
	 * Adds the given element to the elements represented by this
	 * {@link ContextableProperty} in the given model.
	 * <p>
	 * Note that this is a writing operation, so the property has to
	 * {@link ReadableProperty#exists(Object, Context)} in the model.
	 * 
	 * @param model
	 *            The model to add the element to; might <b>NOT</b> be null.
	 * @param element
	 *            The element to add; might be null.
	 * @param context
	 *            The {@link Context} that should be used to satisfy the contexted
	 *            properties from the root property to this
	 *            {@link ContextableProperty}; might be null.
	 * @throws InterruptedPropertyPathException
	 *             If any property on the path to this {@link ContextableProperty}
	 *             is null.
	 * @throws UncontextedPropertyPathException
	 *             If there is any uncontexted property in this
	 *             {@link ContextableProperty}'s path that does not have a
	 *             {@link PropertyIndex} included in the given {@link Context}.
	 * @throws OutboundPropertyPathException
	 *             If the key provided by the given {@link Context} is out of bounds
	 *             on the given model's elements this {@link ContextableProperty}
	 *             represents.
	 */
	public void add(M model, E element, Context context);

	/**
	 * Adds the given element to the elements represented by this
	 * {@link ContextableProperty} in the given model.
	 * <p>
	 * Note that this is a writing operation, so the property has to
	 * {@link ReadableProperty#exists(Object, Context)} in the model.
	 * 
	 * @param model
	 *            The model to add the element to; might <b>NOT</b> be null.
	 * @param element
	 *            The element to add; might be null.
	 * @param operator
	 *            Specifies at which operator to add the element; might be null,
	 *            then the element is added at the end.
	 * @param context
	 *            The {@link Context} that should be used to satisfy the contexted
	 *            properties from the root property to this
	 *            {@link ContextableProperty}; might be null.
	 * @throws InterruptedPropertyPathException
	 *             If any property on the path to this {@link ContextableProperty}
	 *             is null.
	 * @throws UncontextedPropertyPathException
	 *             If there is any uncontexted property in this
	 *             {@link ContextableProperty}'s path that does not have a
	 *             {@link PropertyIndex} included in the given {@link Context}.
	 * @throws OutboundPropertyPathException
	 *             If the key provided by the given {@link Context} is out of bounds
	 *             on the given model's elements this {@link ContextableProperty}
	 *             represents.
	 */
	public void addAt(M model, E element, O operator, Context context);

	/**
	 * Removes the element from its elements that is represented by this
	 * {@link ContextableProperty} in the given model.
	 * <p>
	 * The element is removed by key; since this method does not retrieve one, the
	 * last element is removed from the elements.
	 * <p>
	 * Note that this is a writing operation, so the property has to
	 * {@link ReadableProperty#exists(Object)} in the model.
	 * 
	 * @param model
	 *            The model to remove the element from; might <b>NOT</b> be null.
	 * @return The element that has been removed; might be null if the removed
	 *         element was null
	 * @throws InterruptedPropertyPathException
	 *             If any property on the path to this {@link ContextableProperty}
	 *             is null.
	 * @throws UncontextedPropertyPathException
	 *             If there is any uncontexted property in this
	 *             {@link ContextableProperty}'s path.
	 */
	public default E remove(M model) {
		return remove(model, (Context) null);
	}

	/**
	 * Removes the element from its elements that is represented by this
	 * {@link ContextableProperty} in the given model.
	 * <p>
	 * The element is removed by key; the given one is used.
	 * <p>
	 * Note that this is a writing operation, so the property has to
	 * {@link ReadableProperty#exists(Object)} in the model.
	 * 
	 * @param model
	 *            The model to remove the element from; might <b>NOT</b> be null.
	 * @param key
	 *            Specifies at which key to remove the element; might be null, then
	 *            the element is removed from the end.
	 * @return The element that has been removed; might be null if the removed
	 *         element was null
	 * @throws InterruptedPropertyPathException
	 *             If any property on the path to this {@link ContextableProperty}
	 *             is null.
	 * @throws UncontextedPropertyPathException
	 *             If there is any uncontexted property in this
	 *             {@link ContextableProperty}'s path.
	 */
	public default E removeAt(M model, O key) {
		return removeAt(model, key, (Context) null);
	}

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
	 *            The model to remove the element from; might <b>NOT</b> be null.
	 * @param element
	 *            The element to remove. If it is contained by the elements multiple
	 *            times, the first occurrence is removed; might be null.
	 * @return The key of the element that has been removed; might be null if the
	 *         given element was not included in its element
	 * @throws InterruptedPropertyPathException
	 *             If any property on the path to this {@link ContextableProperty}
	 *             is null.
	 * @throws UncontextedPropertyPathException
	 *             If there is any uncontexted property in this
	 *             {@link ContextableProperty}'s path.
	 */
	public default O remove(M model, E element) {
		return remove(model, element, null);
	}

	/**
	 * Removes the element from its elements that is represented by this
	 * {@link ContextableProperty} in the given model.
	 * <p>
	 * The element is removed by key; since this method does not retrieve one, the
	 * last element is removed from the elements.
	 * <p>
	 * Note that this is a writing operation, so the property has to
	 * {@link ReadableProperty#exists(Object, Context)} in the model.
	 * 
	 * @param model
	 *            The model to remove the element from; might <b>NOT</b> be null.
	 * @param context
	 *            The {@link Context} that should be used to satisfy the contexted
	 *            properties from the root property to this
	 *            {@link ContextableProperty}; might be null.
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
	 *             If the key provided by the given {@link Context} is out of bounds
	 *             on the given model's elements this {@link ContextableProperty}
	 *             represents.
	 */
	public E remove(M model, Context context);

	/**
	 * Removes the element from its elements that is represented by this
	 * {@link ContextableProperty} in the given model.
	 * <p>
	 * The element is removed by key; the given one is used.
	 * <p>
	 * Note that this is a writing operation, so the property has to
	 * {@link ReadableProperty#exists(Object, Context)} in the model.
	 * 
	 * @param model
	 *            The model to remove the element from; might <b>NOT</b> be null.
	 * @param operator
	 *            Specifies at which operator to remove the element; might be null,
	 *            then the element is removed from the end.
	 * @param context
	 *            The {@link Context} that should be used to satisfy the contexted
	 *            properties from the root property to this
	 *            {@link ContextableProperty}; might be null.
	 * @return The element that has been removed; might be null if the removed
	 *         element was null
	 * @throws InterruptedPropertyPathException
	 *             If any property on the path to this {@link ContextableProperty}
	 *             is null.
	 * @throws UncontextedPropertyPathException
	 *             If there is any uncontexted property in this
	 *             {@link ContextableProperty}'s path that does not have a
	 *             {@link PropertyIndex} included in the given {@link Context}.
	 * @throws OutboundPropertyPathException
	 *             If the key provided by the given {@link Context} is out of bounds
	 *             on the given model's elements this {@link ContextableProperty}
	 *             represents.
	 */
	public E removeAt(M model, O operator, Context context);

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
	 *            The model to remove the element from; might <b>NOT</b> be null.
	 * @param element
	 *            The element to remove. If it is contained by the elements multiple
	 *            times, the first occurrence is removed; might be null.
	 * @param context
	 *            The {@link Context} that should be used to satisfy the contexted
	 *            properties from the root property to this
	 *            {@link ContextableProperty}; might be null.
	 * @return The key of the element that has been removed; might be null if the
	 *         given element was not included in its element
	 * @throws InterruptedPropertyPathException
	 *             If any property on the path to this {@link ContextableProperty}
	 *             is null.
	 * @throws UncontextedPropertyPathException
	 *             If there is any uncontexted property in this
	 *             {@link ContextableProperty}'s path that does not have a
	 *             {@link PropertyIndex} included in the given {@link Context}.
	 * @throws OutboundPropertyPathException
	 *             If the key provided by the given {@link Context} is out of bounds
	 *             on the given model's elements this {@link ContextableProperty}
	 *             represents.
	 */
	public O remove(M model, E element, Context context);
}
