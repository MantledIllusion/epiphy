package com.mantledillusion.data.epiphy;

import java.util.List;

import com.mantledillusion.data.epiphy.exception.InterruptedPropertyPathException;
import com.mantledillusion.data.epiphy.exception.OutboundPropertyPathException;
import com.mantledillusion.data.epiphy.exception.UnindexedPropertyPathException;
import com.mantledillusion.data.epiphy.index.IndexContext;
import com.mantledillusion.data.epiphy.index.impl.DefaultIndexContext;
import com.mantledillusion.data.epiphy.interfaces.ListedProperty;
import com.mantledillusion.data.epiphy.io.IndexedGetter;
import com.mantledillusion.data.epiphy.io.IndexedSetter;

abstract class ListedModelProperty<M, E> extends AbstractModelProperty<M, List<E>> implements ListedProperty<M, E> {

	private static final class IndexedListedGetter<E> implements IndexedGetter<List<E>, E> {

		private final ListedModelProperty<?, E> list;

		public IndexedListedGetter(ListedModelProperty<?, E> list) {
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

		private final ListedModelProperty<?, E> list;

		public IndexedListedSetter(ListedModelProperty<?, E> list) {
			super();
			this.list = list;
		}

		@Override
		public void set(List<E> target, E value, IndexContext context) {
			this.list.checkListIndexing(target, context, true, false);
			target.set(context.indexOf(this.list), value);
		}
	}

	private AbstractModelProperty<M, E> element;

	<P> ListedModelProperty(String id) {
		super(id, null, true);
	}

	<P> ListedModelProperty(String id, AbstractModelProperty<M, P> parent) {
		super(id, parent, true);
	}
	
	@Override
	public boolean hasChildrenIn(M model, IndexContext context) {
		context = context == null ? DefaultIndexContext.EMPTY : context;
		if (!context.contains(this)) {
			throw new UnindexedPropertyPathException(this);
		}
		return !isNull(model, context) && hasChildren() && context.indexOf(this) >= 0
				&& context.indexOf(this) < get(model, context).size();
	}

	@Override
	public ModelProperty<M, E> defineElementAsChild(String elementId) {
		if (this.element != null) {
			throw new IllegalStateException("The element of this model property list has already been defined.");
		}
		ModelProperty<M, E> child = new ModelProperty<M, E>(elementId, this,
				new IndexedListedGetter<E>(this), new IndexedListedSetter<E>(this));
		this.element = child;
		return child;
	}

	@Override
	@SuppressWarnings("unchecked")
	public <C> ModelPropertyList<M, C> defineElementAsChildList(String elementId) {
		if (this.element != null) {
			throw new IllegalStateException("The element of this model property list has already been defined.");
		}
		ListedModelProperty<M, List<C>> parentList = (ListedModelProperty<M, List<C>>) this;
		ModelPropertyList<M, C> childList = new ModelPropertyList<M, C>(elementId, parentList,
				new IndexedListedGetter<>(parentList), new IndexedListedSetter<>(parentList));
		this.element = (AbstractModelProperty<M, E>) childList;
		return childList;
	}

	@Override
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

	@Override
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
				if (allowNull) {
					return false;
				} else {
					throw new OutboundPropertyPathException(this, context.indexOf(this), list.size());
				}
			}
		}
		return true;
	}
}
