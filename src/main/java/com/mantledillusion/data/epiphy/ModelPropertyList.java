package com.mantledillusion.data.epiphy;

import java.lang.reflect.Method;
import java.util.List;

import com.mantledillusion.data.epiphy.exception.InterruptedPropertyPathException;
import com.mantledillusion.data.epiphy.exception.OutboundPropertyPathException;
import com.mantledillusion.data.epiphy.exception.UnindexedPropertyPathException;
import com.mantledillusion.data.epiphy.index.IndexContext;
import com.mantledillusion.data.epiphy.index.PropertyIndex;
import com.mantledillusion.data.epiphy.index.impl.DefaultIndexContext;

/**
 * Implementation of {@link ModelProperty} that represents a {@link List} of
 * model properties.
 *
 * @param <M>
 *            The root model type of this {@link ModelPropertyList}'s property
 *            tree.
 * @param <E>
 *            The list element type of the property list this
 *            {@link ModelPropertyList} represents.
 */
public final class ModelPropertyList<M, E> extends ModelProperty<M, List<E>> {

	private static final class IndexedListedGetter<E> implements IndexedGetter<List<E>, E> {

		private final ModelPropertyList<?, E> list;

		public IndexedListedGetter(ModelPropertyList<?, E> list) {
			this.list = list;
		}

		@Override
		public E get(List<E> source, IndexContext context, boolean allowNull) {
			if (this.list.checkListIndexing(source, context, true, allowNull)) {
				return source.get(context.indexOf(this.list));
			} else {
				return null;
			}
		}
	}

	private static final class IndexedListedSetter<E> implements IndexedSetter<List<E>, E> {

		private final ModelPropertyList<?, E> list;

		public IndexedListedSetter(ModelPropertyList<?, E> list) {
			super();
			this.list = list;
		}

		@Override
		public void set(List<E> target, E value, IndexContext context) {
			this.list.checkListIndexing(target, context, true, false);
			target.set(context.indexOf(this.list), value);
		}
	}

	private ModelProperty<M, E> element;

	<P> ModelPropertyList(String id) {
		super(id);
	}

	<P> ModelPropertyList(String id, ModelProperty<M, P> parent, IndexedGetter<P, List<E>> getter,
			IndexedSetter<P, List<E>> setter) {
		super(id, parent, getter, setter, true);
	}

	/**
	 * Defines the element of this {@link ModelPropertyList} to be a
	 * {@link DefiniteModelProperty}, no {@link ModelPropertyList}.
	 * <p>
	 * NOTE: The element of a {@link ModelPropertyList} can only be defined once!
	 * 
	 * @return The {@link DefiniteModelProperty} that represents an element of this
	 *         {@link ModelPropertyList}; never null
	 */
	public DefiniteModelProperty<M, E> defineElementAsChild() {
		return defineElementAsChild(null);
	}

	/**
	 * Defines the element of this {@link ModelPropertyList} to be a
	 * {@link DefiniteModelProperty}, no {@link ModelPropertyList}.
	 * <p>
	 * NOTE: The element of a {@link ModelPropertyList} can only be defined once!
	 * 
	 * @param elementId
	 *            The id the element {@link DefiniteModelProperty} of the returned
	 *            listed child {@link ModelPropertyList} will be identified by;
	 *            might be null. All parent id's (including this
	 *            {@link DefiniteModelProperty}'s id) will be prepended.
	 * @return The {@link DefiniteModelProperty} that represents an element of this
	 *         {@link ModelPropertyList}; never null
	 */
	public DefiniteModelProperty<M, E> defineElementAsChild(String elementId) {
		if (this.element != null) {
			throw new IllegalStateException("The element of this model property list has already been defined.");
		}
		DefiniteModelProperty<M, E> child = new DefiniteModelProperty<M, E>(elementId, this,
				new IndexedListedGetter<E>(this), new IndexedListedSetter<E>(this));
		this.element = child;
		return child;
	}

	/**
	 * Defines the element of this {@link ModelPropertyList} to be a
	 * {@link ModelPropertyList}, no {@link DefiniteModelProperty}.
	 * <p>
	 * NOTE: The element of a {@link ModelPropertyList} can only be defined once!
	 * <p>
	 * Make sure to only do this on {@link ModelPropertyList}s whose element type
	 * actually implements {@link List}; if not, this {@link Method} will return a
	 * property anyway, but {@link ClassCastException}s will occur during the use of
	 * the property.
	 * 
	 * @param <C>
	 *            The element type of the {@link ModelPropertyList} being defined as
	 *            element for this {@link ModelPropertyList}.
	 * @return The {@link ModelPropertyList} that represents an element of this
	 *         {@link ModelPropertyList}; never null
	 */
	public <C> ModelPropertyList<M, C> defineElementAsChildList() {
		return defineElementAsChildList(null);
	}

	/**
	 * Defines the element of this {@link ModelPropertyList} to be a
	 * {@link ModelPropertyList}, no {@link DefiniteModelProperty}.
	 * <p>
	 * NOTE: The element of a {@link ModelPropertyList} can only be defined once!
	 * <p>
	 * Make sure to only do this on {@link ModelPropertyList}s whose element type
	 * actually implements {@link List}; if not, this {@link Method} will return a
	 * property anyway, but {@link ClassCastException}s will occur during the use of
	 * the property.
	 * 
	 * @param <C>
	 *            The element type of the {@link ModelPropertyList} being defined as
	 *            element for this {@link ModelPropertyList}.
	 * @param elementId
	 *            The id the element {@link DefiniteModelProperty} of the returned
	 *            listed child {@link ModelPropertyList} will be identified by;
	 *            might be null. All parent id's (including this
	 *            {@link DefiniteModelProperty}'s id) will be prepended.
	 * @return The {@link ModelPropertyList} that represents an element of this
	 *         {@link ModelPropertyList}; never null
	 */
	@SuppressWarnings("unchecked")
	public <C> ModelPropertyList<M, C> defineElementAsChildList(String elementId) {
		if (this.element != null) {
			throw new IllegalStateException("The element of this model property list has already been defined.");
		}
		ModelPropertyList<M, List<C>> parentList = (ModelPropertyList<M, List<C>>) this;
		ModelPropertyList<M, C> childList = new ModelPropertyList<M, C>(elementId, parentList,
				new IndexedListedGetter<>(parentList), new IndexedListedSetter<>(parentList));
		this.element = (ModelProperty<M, E>) childList;
		return childList;
	}

	/**
	 * Adds the given element to the list represented by this
	 * {@link ModelPropertyList} in the given model.
	 * <p>
	 * Note that this is a writing operation, so the property has to
	 * {@link #exists(Object)} in the model.
	 * 
	 * @param model
	 *            The model to add the element to; might <b>NOT</b> be null.
	 * @param element
	 *            The element to add; might be null.
	 * @throws InterruptedPropertyPathException
	 *             If any property on the path to this {@link ModelProperty} is
	 *             null.
	 * @throws UnindexedPropertyPathException
	 *             If there is any listed property in this {@link ModelProperty}'s
	 *             path.
	 */
	public void add(M model, E element) {
		add(model, element, null);
	}

	/**
	 * Adds the given element to the list represented by this
	 * {@link ModelPropertyList} in the given model.
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
	 *            properties from the root property to his {@link ModelProperty};
	 *            might be null. If a {@link PropertyIndex} is provided for this
	 *            {@link ModelProperty}, it is used to specify at which index to add
	 *            the element. If there is none, the element is added at the end.
	 * @throws InterruptedPropertyPathException
	 *             If any property on the path to this {@link ModelProperty} is
	 *             null.
	 * @throws UnindexedPropertyPathException
	 *             If there is any listed property in this {@link ModelProperty}'s
	 *             path that does not have a {@link PropertyIndex} included in the
	 *             given {@link IndexContext}.
	 * @throws OutboundPropertyPathException
	 *             If the index provided by the given {@link IndexContext} is out of
	 *             bounds on the given model's list this {@link ModelPropertyList}
	 *             represents.
	 */
	public void add(M model, E element, IndexContext context) {
		context = context == null ? DefaultIndexContext.EMPTY : context;
		List<E> list = get(model, context);
		checkListIndexing(list, context, false, false);
		if (context.contains(this)) {
			list.add(context.indexOf(this), element);
		} else {
			list.add(element);
		}
	}

	/**
	 * Removes the given element from the list represented by this
	 * {@link ModelPropertyList} in the given model.
	 * <p>
	 * Note that this is a writing operation, so the property has to
	 * {@link #exists(Object)} in the model.
	 * 
	 * @param model
	 *            The model to remove the element from; might <b>NOT</b> be null.
	 * @return The element that has been removed; might be null if the removed
	 *         element was null
	 * @throws InterruptedPropertyPathException
	 *             If any property on the path to this {@link ModelProperty} is
	 *             null.
	 * @throws UnindexedPropertyPathException
	 *             If there is any listed property in this {@link ModelProperty}'s
	 *             path.
	 */
	public E remove(M model) {
		return remove(model, null);
	}

	/**
	 * Removes the given element from the list represented by this
	 * {@link ModelPropertyList} in the given model.
	 * <p>
	 * Note that this is a writing operation, so the property has to
	 * {@link #exists(Object, IndexContext)} in the model.
	 * 
	 * @param model
	 *            The model to remove the element from; might <b>NOT</b> be null.
	 * @param context
	 *            The {@link IndexContext} that should be used to satisfy the listed
	 *            properties from the root property to his {@link ModelProperty};
	 *            might be null. If a {@link PropertyIndex} is provided for this
	 *            {@link ModelProperty}, it is used to specify at which index to
	 *            remove the element. If there is none, the element is removed from
	 *            the end.
	 * @return The element that has been removed; might be null if the removed
	 *         element was null
	 * @throws InterruptedPropertyPathException
	 *             If any property on the path to this {@link ModelProperty} is
	 *             null.
	 * @throws UnindexedPropertyPathException
	 *             If there is any listed property in this {@link ModelProperty}'s
	 *             path that does not have a {@link PropertyIndex} included in the
	 *             given {@link IndexContext}.
	 * @throws OutboundPropertyPathException
	 *             If the index provided by the given {@link IndexContext} is out of
	 *             bounds on the given model's list this {@link ModelPropertyList}
	 *             represents.
	 */
	public E remove(M model, IndexContext context) {
		context = context == null ? DefaultIndexContext.EMPTY : context;
		List<E> list = get(model, context);
		checkListIndexing(list, context, false, false);
		if (context.contains(this)) {
			return list.remove((int) context.indexOf(this));
		} else {
			return list.remove((int) list.size() - 1);
		}
	}

	private boolean checkListIndexing(List<E> list, IndexContext context, boolean indexRequired, boolean allowNull) {
		if (list == null) {
			if (allowNull) {
				return false;
			}
			throw new InterruptedPropertyPathException(this);
		} else if (indexRequired) {
			if (!context.contains(this)) {
				throw new UnindexedPropertyPathException(this);
			} else if (context.indexOf(this) < 0 || context.indexOf(this) >= list.size()) {
				throw new OutboundPropertyPathException(this, context.indexOf(this), list.size());
			}
		}
		return true;
	}
}