package com.mantledillusion.data.epiphy;

import java.util.List;

import com.mantledillusion.data.epiphy.context.Context;
import com.mantledillusion.data.epiphy.context.ContextedValue;
import com.mantledillusion.data.epiphy.context.PropertyIndex;
import com.mantledillusion.data.epiphy.context.impl.DefaultContext;
import com.mantledillusion.data.epiphy.exception.InterruptedPropertyPathException;
import com.mantledillusion.data.epiphy.exception.OutboundPropertyPathException;
import com.mantledillusion.data.epiphy.exception.UncontextedPropertyPathException;
import com.mantledillusion.data.epiphy.interfaces.type.ListedProperty;
import com.mantledillusion.data.epiphy.io.Getter;
import com.mantledillusion.data.epiphy.io.ContextedGetter;
import com.mantledillusion.data.epiphy.io.ContextedSetter;

abstract class ListedModelProperty<M, E> extends AbstractModelProperty<M, List<E>> implements ListedProperty<M, E> {

	private static final class IndexedListedGetter<E> implements ContextedGetter<List<E>, E> {

		private final ListedModelProperty<?, E> list;

		public IndexedListedGetter(ListedModelProperty<?, E> list) {
			this.list = list;
		}

		@Override
		public E get(List<E> source, Context context, boolean allowNull) {
			if (this.list.checkListIndexing(source, context, true, true, allowNull)) {
				return source.get(context.getKey(this.list));
			} else {
				return null;
			}
		}
	}

	private static final class IndexedListedSetter<E> implements ContextedSetter<List<E>, E> {

		private final ListedModelProperty<?, E> list;

		public IndexedListedSetter(ListedModelProperty<?, E> list) {
			super();
			this.list = list;
		}

		@Override
		public void set(List<E> target, E value, Context context) {
			this.list.checkListIndexing(target, context, true, true, false);
			target.set(context.getKey(this.list), value);
		}
	}

	private AbstractModelProperty<M, E> element;

	ListedModelProperty(String id) {
		super(id, null, true);
	}

	<P> ListedModelProperty(String id, AbstractModelProperty<M, P> parent) {
		super(id, parent, true);
	}

	// ###########################################################################################################
	// ############################################### INTERNAL ##################################################
	// ###########################################################################################################

	private boolean checkListIndexing(List<E> list, Context context, boolean indexRequired, boolean harshBounds, boolean allowNull) {
		if (list == null) {
			if (allowNull) {
				return false;
			}
			throw new InterruptedPropertyPathException(this);
		} else if (indexRequired) {
			if (context.containsKey(this)) {
				Integer idx = context.getKey(this);
				return checkIndex(list.size(), idx, allowNull, harshBounds);
			} else {
				throw new UncontextedPropertyPathException(this);
			}
		}
		return true;
	}

	private boolean checkIndex(int listSize, int idx, boolean allowNull, boolean harshBounds) {
		if (idx < 0 || (harshBounds ? idx >= listSize : idx > listSize)) {
			if (allowNull) {
				return false;
			} else {
				throw new OutboundPropertyPathException(this, idx);
			}
		}
		return true;
	}

	// ###########################################################################################################
	// ############################################ FUNCTIONALITY ################################################
	// ###########################################################################################################

	@Override
	public boolean hasChildrenIn(M model, Context context) {
		context = context == null ? DefaultContext.EMPTY : context;
		if (!context.containsKey(this)) {
			throw new UncontextedPropertyPathException(this);
		}
		return !isNull(model, context) && hasChildren() && context.getKey(this) >= 0
				&& context.getKey(this) < get(model, context).size();
	}

	// ###########################################################################################################
	// ############################################### CHILDREN ##################################################
	// ###########################################################################################################

	@Override
	public ModelProperty<M, E> defineElementAsChild(String elementId) {
		if (this.element != null) {
			throw new IllegalStateException("The element of this model property list has already been defined.");
		}
		ModelProperty<M, E> child = new ModelProperty<M, E>(elementId, this, new IndexedListedGetter<E>(this),
				new IndexedListedSetter<E>(this));
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
	public ModelPropertyNode<M, E> defineElementAsChildNode(String elementId, Getter<E, List<E>> leafGetter) {
		if (this.element != null) {
			throw new IllegalStateException("The element of this model property list has already been defined.");
		}
		ModelPropertyNode<M, E> child = new ModelPropertyNode<M, E>(elementId, this, new IndexedListedGetter<E>(this),
				new IndexedListedSetter<E>(this), leafGetter);
		this.element = child;
		return child;
	}

	// ###########################################################################################################
	// ############################################## OPERATIONS #################################################
	// ###########################################################################################################
	
	@Override
	public PropertyIndex referenceOf(Integer reference) {
		return PropertyIndex.of(this, reference);
	}

	@Override
	public int size(M model, Context context) {
		context = context == null ? DefaultContext.EMPTY : context;
		List<E> list = get(model, context);
		checkListIndexing(list, context, false, false, false);
		return list.size();
	}
	
	@Override
	public Integer append(M model, E element, Context context) {
		context = context == null ? DefaultContext.EMPTY : context;
		List<E> list = get(model, context);
		checkListIndexing(list, context, false, false, false);
		list.add(element);
		return list.size()-1;
	}

	@Override
	public Integer addAt(M model, E element, Context context) {
		context = context == null ? DefaultContext.EMPTY : context;
		List<E> list = get(model, context);
		checkListIndexing(list, context, true, false, false);
		Integer idx = context.getKey(this);
		list.add(idx, element);
		return idx;
	}
	
	@Override
	public ContextedValue<Integer, E> strip(M model, Context context) {
		context = context == null ? DefaultContext.EMPTY : context;
		List<E> list = get(model, context);
		checkListIndexing(list, context, false, false, false);
		if (list.isEmpty()) {
			return null;
		} else {
			return ContextedValue.of(list.size()-1, list.remove(list.size()-1));
		}
	}

	@Override
	public E removeAt(M model, Context context) {
		context = context == null ? DefaultContext.EMPTY : context;
		List<E> list = get(model, context);
		checkListIndexing(list, context, true, true, false);
		return list.remove((int) context.getKey(this));
	}
	
	@Override
	public Integer remove(M model, E element, Context context) {
		context = context == null ? DefaultContext.EMPTY : context;
		List<E> list = get(model, context);
		checkListIndexing(list, context, false, false, false);
		int index = list.indexOf(element);
		if (index == -1) {
			return null;
		} else {
			list.remove(index);
			return index;
		}
	}
}
