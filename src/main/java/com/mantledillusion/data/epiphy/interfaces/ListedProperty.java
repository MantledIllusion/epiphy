package com.mantledillusion.data.epiphy.interfaces;

import java.lang.reflect.Method;
import java.util.List;

import com.mantledillusion.data.epiphy.ModelProperty;
import com.mantledillusion.data.epiphy.ModelPropertyList;
import com.mantledillusion.data.epiphy.exception.InterruptedPropertyPathException;
import com.mantledillusion.data.epiphy.exception.OutboundPropertyPathException;
import com.mantledillusion.data.epiphy.exception.UnindexedPropertyPathException;
import com.mantledillusion.data.epiphy.index.IndexContext;
import com.mantledillusion.data.epiphy.index.PropertyIndex;

public interface ListedProperty<M, E> extends Property<M, List<E>> {

	/**
	 * Defines the element of this {@link ListedProperty} to be a
	 * {@link ModelProperty}.
	 * <p>
	 * NOTE: The element of a {@link ListedProperty} can only be defined once!
	 * 
	 * @return The {@link ModelProperty} that represents an element of this
	 *         {@link ListedProperty}; never null
	 */
	public ModelProperty<M, E> defineElementAsChild();

	/**
	 * Defines the element of this {@link ListedProperty} to be a
	 * {@link ModelProperty}.
	 * <p>
	 * NOTE: The element of a {@link ListedProperty} can only be defined once!
	 * 
	 * @param elementId
	 *            The id the element {@link ModelProperty} of the returned listed
	 *            child {@link ListedProperty} will be identified by; might be null.
	 *            All parent id's (including this {@link ModelProperty}'s id) will
	 *            be prepended.
	 * @return The {@link ModelProperty} that represents an element of this
	 *         {@link ModelPropertyList}; never null
	 */
	public ModelProperty<M, E> defineElementAsChild(String elementId);

	/**
	 * Defines the element of this {@link ListedProperty} to be a
	 * {@link ModelPropertyList}.
	 * <p>
	 * NOTE: The element of a {@link ListedProperty} can only be defined once!
	 * <p>
	 * Make sure to only do this on {@link ListedProperty}s whose element type
	 * actually implements {@link List}; if not, this {@link Method} will return a
	 * property anyway, but {@link ClassCastException}s will occur during the use of
	 * the property.
	 * 
	 * @param <C>
	 *            The element type of the {@link ModelPropertyList} being defined as
	 *            element for this {@link ListedProperty}.
	 * @return The {@link ModelPropertyList} that represents an element of this
	 *         {@link ListedProperty}; never null
	 */
	public <C> ModelPropertyList<M, C> defineElementAsChildList();

	/**
	 * Defines the element of this {@link ListedProperty} to be a
	 * {@link ModelPropertyList}.
	 * <p>
	 * NOTE: The element of a {@link ListedProperty} can only be defined once!
	 * <p>
	 * Make sure to only do this on {@link ListedProperty}s whose element type
	 * actually implements {@link List}; if not, this {@link Method} will return a
	 * property anyway, but {@link ClassCastException}s will occur during the use of
	 * the property.
	 * 
	 * @param <C>
	 *            The element type of the {@link ModelPropertyList} being defined as
	 *            element for this {@link ListedProperty}.
	 * @param elementId
	 *            The id the element {@link ModelProperty} of the returned listed
	 *            child {@link ModelPropertyList} will be identified by; might be
	 *            null. All parent id's (including this {@link ListedProperty}'s id)
	 *            will be prepended.
	 * @return The {@link ModelPropertyList} that represents an element of this
	 *         {@link ListedProperty}; never null
	 */
	public <C> ModelPropertyList<M, C> defineElementAsChildList(String elementId);

	/**
	 * Adds the given element to the list represented by this {@link ListedProperty}
	 * in the given model.
	 * <p>
	 * Note that this is a writing operation, so the property has to
	 * {@link #exists(Object)} in the model.
	 * 
	 * @param model
	 *            The model to add the element to; might <b>NOT</b> be null.
	 * @param element
	 *            The element to add; might be null.
	 * @throws InterruptedPropertyPathException
	 *             If any property on the path to this {@link ListedModelProperty}
	 *             is null.
	 * @throws UnindexedPropertyPathException
	 *             If there is any listed property in this {@link ListedProperty}'s
	 *             path.
	 */
	public void add(M model, E element);

	/**
	 * Adds the given element to the list represented by this {@link ListedProperty}
	 * in the given model.
	 * <p>
	 * Note that this is a writing operation, so the property has to
	 * {@link #exists(Object, IndexContext)} in the model.
	 * 
	 * @param model
	 *            The model to add the element to; might <b>NOT</b> be null.
	 * @param element
	 *            The element to add; might be null.
	 * @param context
	 *            The {@link IndexContext} that should be used to satisfy the listed
	 *            properties from the root property to this {@link ListedProperty};
	 *            might be null. If a {@link PropertyIndex} is provided for this
	 *            {@link ListedProperty}, it is used to specify at which index to
	 *            add the element. If there is none, the element is added at the
	 *            end.
	 * @throws InterruptedPropertyPathException
	 *             If any property on the path to this {@link ListedProperty} is
	 *             null.
	 * @throws UnindexedPropertyPathException
	 *             If there is any listed property in this {@link ListedProperty}'s
	 *             path that does not have a {@link PropertyIndex} included in the
	 *             given {@link IndexContext}.
	 * @throws OutboundPropertyPathException
	 *             If the index provided by the given {@link IndexContext} is out of
	 *             bounds on the given model's list this {@link ListedProperty}
	 *             represents.
	 */
	public void add(M model, E element, IndexContext context);

	/**
	 * Removes the given element from the list represented by this
	 * {@link ListedProperty} in the given model.
	 * <p>
	 * Note that this is a writing operation, so the property has to
	 * {@link #exists(Object)} in the model.
	 * 
	 * @param model
	 *            The model to remove the element from; might <b>NOT</b> be null.
	 * @return The element that has been removed; might be null if the removed
	 *         element was null
	 * @throws InterruptedPropertyPathException
	 *             If any property on the path to this {@link ListedProperty} is
	 *             null.
	 * @throws UnindexedPropertyPathException
	 *             If there is any listed property in this {@link ListedProperty}'s
	 *             path.
	 */
	public E remove(M model);

	/**
	 * Removes the given element from the list represented by this
	 * {@link ListedProperty} in the given model.
	 * <p>
	 * Note that this is a writing operation, so the property has to
	 * {@link #exists(Object, IndexContext)} in the model.
	 * 
	 * @param model
	 *            The model to remove the element from; might <b>NOT</b> be null.
	 * @param context
	 *            The {@link IndexContext} that should be used to satisfy the listed
	 *            properties from the root property to this {@link ListedProperty};
	 *            might be null. If a {@link PropertyIndex} is provided for this
	 *            {@link ListedProperty}, it is used to specify at which index to
	 *            remove the element. If there is none, the element is removed from
	 *            the end.
	 * @return The element that has been removed; might be null if the removed
	 *         element was null
	 * @throws InterruptedPropertyPathException
	 *             If any property on the path to this {@link ListedProperty} is
	 *             null.
	 * @throws UnindexedPropertyPathException
	 *             If there is any listed property in this {@link ListedProperty}'s
	 *             path that does not have a {@link PropertyIndex} included in the
	 *             given {@link IndexContext}.
	 * @throws OutboundPropertyPathException
	 *             If the index provided by the given {@link IndexContext} is out of
	 *             bounds on the given model's list this {@link ListedProperty}
	 *             represents.
	 */
	public E remove(M model, IndexContext context);
}
