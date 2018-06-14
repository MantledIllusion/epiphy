package com.mantledillusion.data.epiphy.interfaces.type;

import java.lang.reflect.Method;
import java.util.List;

import com.mantledillusion.data.epiphy.ModelProperty;
import com.mantledillusion.data.epiphy.ModelPropertyList;
import com.mantledillusion.data.epiphy.ModelPropertyNode;
import com.mantledillusion.data.epiphy.interfaces.ReadableProperty;
import com.mantledillusion.data.epiphy.interfaces.function.ContextableProperty;
import com.mantledillusion.data.epiphy.io.Getter;

/**
 * Interface for types that represent a listed, multi instance
 * {@link ReadableProperty}.
 * <p>
 * On a {@link ListedProperty} instance, the {@link Method}s...<br>
 * - {@link #defineElementAsChild()}<br>
 * - {@link #defineElementAsChild(String)}<br>
 * ... can be used to specify the listed element as a definite child property to
 * that instance. Also, the {@link Method}s...<br>
 * - {@link #defineElementAsChildList()}<br>
 * - {@link #defineElementAsChildList(String)}<br>
 * ... can be used to specify the listed element as a listed child property to
 * that instance.
 * 
 * @param <M>
 *            The root model type of this {@link ListedProperty}'s property
 *            tree.
 * @param <E>
 *            The type of the property element this {@link ListedProperty}
 *            represents.
 */
public interface ListedProperty<M, E> extends ReadableProperty<M, List<E>>, ContextableProperty<M, E, Integer, Integer> {

	/**
	 * Defines the element of this {@link ListedProperty} to be a
	 * {@link ModelProperty}.
	 * <p>
	 * NOTE: The element of a {@link ListedProperty} can only be defined once!
	 * 
	 * @return The {@link ModelProperty} that represents an element of this
	 *         {@link ListedProperty}; never null
	 */
	public default ModelProperty<M, E> defineElementAsChild() {
		return defineElementAsChild(null);
	}

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
	public default <C> ModelPropertyList<M, C> defineElementAsChildList() {
		return defineElementAsChildList(null);
	}

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
	 * Defines the element of this {@link ListedProperty} to be a
	 * {@link ModelPropertyNode}.
	 * <p>
	 * NOTE: The element of a {@link ListedProperty} can only be defined once!
	 * 
	 * @param leafGetter
	 *            The {@link Getter} that is able to retrieve the leaf nodes of the
	 *            {@link ModelPropertyNode}; might <b>not</b> be null.
	 * @return The {@link ModelPropertyNode} that represents an element of this
	 *         {@link ListedProperty}; never null
	 */
	public default ModelPropertyNode<M, E> defineElementAsChildNode(Getter<E, List<E>> leafGetter) {
		return defineElementAsChildNode(null, leafGetter);
	}

	/**
	 * Defines the element of this {@link ListedProperty} to be a
	 * {@link ModelPropertyNode}.
	 * <p>
	 * NOTE: The element of a {@link ListedProperty} can only be defined once!
	 * 
	 * @param elementId
	 *            The id the element {@link ModelProperty} of the returned listed
	 *            child {@link ModelPropertyNode} will be identified by; might be
	 *            null. All parent id's (including this {@link ListedProperty}'s id)
	 *            will be prepended.
	 * @param leafGetter
	 *            The {@link Getter} that is able to retrieve the leaf nodes of the
	 *            {@link ModelPropertyNode}; might <b>not</b> be null.
	 * @return The {@link ModelPropertyNode} that represents an element of this
	 *         {@link ListedProperty}; never null
	 */
	public ModelPropertyNode<M, E> defineElementAsChildNode(String elementId, Getter<E, List<E>> leafGetter);
}
