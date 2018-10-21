package com.mantledillusion.data.epiphy.interfaces.function;

import com.mantledillusion.data.epiphy.context.Context;
import com.mantledillusion.data.epiphy.context.ContextedValue;
import com.mantledillusion.data.epiphy.context.PropertyReference;
import com.mantledillusion.data.epiphy.exception.InterruptedPropertyPathException;
import com.mantledillusion.data.epiphy.exception.OutboundPropertyPathException;
import com.mantledillusion.data.epiphy.exception.UncontextedPropertyPathException;
import com.mantledillusion.data.epiphy.interfaces.ReadableProperty;

/**
 * Interface for properties that carry elements which can be reached by using a
 * {@link Context}, but with the extension to {@link ContextableProperty} that
 * the context property reference has a natural order, so it can be
 * automatically increased and decreased for certain operations.
 *
 * @param <M>
 *            The root model type of this {@link EnumerableProperty}'s property
 *            tree.
 * @param <E>
 *            The type of the property element this {@link EnumerableProperty}
 *            represents.
 * @param <R>
 *            The type of the context property reference.
 */
public interface EnumerableProperty<M, E, R> extends ContextableProperty<M, E, R> {

	/**
	 * Adds the given element to the elements represented by this
	 * {@link EnumerableProperty} in the given model.
	 * <p>
	 * Instead of using a property reference in the context, this operation uses the
	 * property reference's natural order to add the given element at the end.
	 * <p>
	 * Note that this is a writing operation, so the property has to
	 * {@link ReadableProperty#exists(Object)} in the model.
	 * 
	 * @param model
	 *            The model to add the element to; might <b>not</b> be null.
	 * @param element
	 *            The element to add; might be null.
	 * @return The property reference where the element was appended, never null
	 * @throws InterruptedPropertyPathException
	 *             If any property on the path to this {@link EnumerableProperty} is
	 *             null.
	 * @throws UncontextedPropertyPathException
	 *             If there is any uncontexted property in this
	 *             {@link EnumerableProperty}'s path.
	 */
	public default R append(M model, E element) {
		return append(model, element, null);
	}

	/**
	 * Adds the given element to the elements represented by this
	 * {@link EnumerableProperty} in the given model.
	 * <p>
	 * Instead of using a property reference in the context, this operation uses the
	 * property reference's natural order to add the given element at the end.
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
	 *            {@link EnumerableProperty}; might be null.
	 * @return The property reference where the element was appended, never null
	 * @throws InterruptedPropertyPathException
	 *             If any property on the path to this {@link EnumerableProperty} is
	 *             null.
	 * @throws UncontextedPropertyPathException
	 *             If there is any uncontexted property in this
	 *             {@link EnumerableProperty}'s path that does not have a
	 *             {@link PropertyReference} included in the given {@link Context}.
	 * @throws OutboundPropertyPathException
	 *             If the property reference provided by the given {@link Context}
	 *             is out of bounds on the given model's elements this
	 *             {@link EnumerableProperty} represents.
	 */
	public R append(M model, E element, Context context);

	/**
	 * Removes the element from its elements that is represented by this
	 * {@link ContextableProperty} in the given model.
	 * <p>
	 * Instead of using a property reference in the context, this operation uses the
	 * property reference's natural order to remove the given element from the end.
	 * <p>
	 * Note that this is a writing operation, so the property has to
	 * {@link ReadableProperty#exists(Object)} in the model.
	 * 
	 * @param model
	 *            The model to remove the element from; might <b>not</b> be null.
	 * @return A {@link ContextedValue} that contains the property reference and
	 *         value that has been removed, might be null if nothing was removed
	 * @throws InterruptedPropertyPathException
	 *             If any property on the path to this {@link ContextableProperty}
	 *             is null.
	 * @throws UncontextedPropertyPathException
	 *             If there is any uncontexted property in this
	 *             {@link ContextableProperty}'s path.
	 */
	public default ContextedValue<R, E> strip(M model) {
		return strip(model, null);
	}

	/**
	 * Removes the element from its elements that is represented by this
	 * {@link ContextableProperty} in the given model.
	 * <p>
	 * Instead of using a property reference in the context, this operation uses the
	 * property reference's natural order to remove the given element from the end.
	 * <p>
	 * Note that this is a writing operation, so the property has to
	 * {@link ReadableProperty#exists(Object)} in the model.
	 * 
	 * @param model
	 *            The model to remove the element from; might <b>not</b> be null.
	 * @param context
	 *            The {@link Context} that should be used to satisfy the contexted
	 *            properties from the root property to this
	 *            {@link EnumerableProperty}; might be null.
	 * @return A {@link ContextedValue} that contains the property reference and
	 *         value that has been removed, might be null if nothing was removed
	 * @throws InterruptedPropertyPathException
	 *             If any property on the path to this {@link ContextableProperty}
	 *             is null.
	 * @throws UncontextedPropertyPathException
	 *             If there is any uncontexted property in this
	 *             {@link ContextableProperty}'s path.
	 */
	public ContextedValue<R, E> strip(M model, Context context);
}
