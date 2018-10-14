package com.mantledillusion.data.epiphy;

import java.util.Arrays;
import java.util.List;

import com.mantledillusion.data.epiphy.context.Context;
import com.mantledillusion.data.epiphy.context.impl.DefaultContext;
import com.mantledillusion.data.epiphy.exception.InterruptedPropertyNodeException;
import com.mantledillusion.data.epiphy.exception.OutboundPropertyNodeException;
import com.mantledillusion.data.epiphy.interfaces.type.NodedProperty;
import com.mantledillusion.data.epiphy.io.Getter;
import com.mantledillusion.data.epiphy.io.IndexedGetter;
import com.mantledillusion.data.epiphy.io.IndexedSetter;
import com.mantledillusion.data.epiphy.io.Setter;

public abstract class NodedModelProperty<M, T> extends AbstractModelProperty<M, T> implements NodedProperty<M, T> {

	private final class IndexedNodedGetter<P, C> implements IndexedGetter<P, C> {

		private final Getter<P, C> getter;

		private IndexedNodedGetter(Getter<P, C> setter) {
			this.getter = setter;
		}

		@Override
		public C get(P source, Context context, boolean allowNull) {
			context = context == null ? DefaultContext.EMPTY : context;
			if (checkParent(source, allowNull)) {
				return this.getter.get(source);
			} else {
				return null;
			}
		}
	}

	private final class IndexedNodedSetter<P, C> implements IndexedSetter<P, C> {
		
		private final Setter<P, C> setter;

		private IndexedNodedSetter(Setter<P, C> setter) {
			this.setter = setter;
		}

		@Override
		public void set(P target, C value, Context context) {
			context = context == null ? DefaultContext.EMPTY : context;
			checkParent(target, false);
			this.setter.set(target, value);
		}
	}

	protected final Getter<T, List<T>> leafGetter;

	NodedModelProperty(String id, Getter<T, List<T>> leafGetter) {
		super(id, null, false);
		this.leafGetter = leafGetter;
	}

	<P> NodedModelProperty(String id, AbstractModelProperty<M, P> parent, Getter<T, List<T>> leafGetter) {
		super(id, parent, false);
		this.leafGetter = leafGetter;
	}

	// ###########################################################################################################
	// ############################################### INTERNAL ##################################################
	// ###########################################################################################################

	protected T locate(T rootNode, int[] nodeIndeces, int currentIndex, boolean allowNull) {
		if (nodeIndeces == null || nodeIndeces.length <= currentIndex) {
			return rootNode;
		} else {
			if (rootNode == null) {
				if (allowNull) {
					return null;
				} else {
					throw new InterruptedPropertyNodeException(this);
				}
			} else {
				List<T> leaves = this.leafGetter.get(rootNode);
				checkIndex(leaves.size(), nodeIndeces[currentIndex], true);
				return locate(leaves.get(nodeIndeces[currentIndex]), nodeIndeces, currentIndex + 1, allowNull);
			}
		}
	}
	
	private void checkIndex(int leafCount, int index, boolean harshBounds) {
		if (index < 0 || (harshBounds ? index >= leafCount : index > leafCount)) {
			throw new OutboundPropertyNodeException(this, index, leafCount);
		}
	}

	protected <P> boolean checkParent(P parent, boolean allowNull) {
		if (parent == null) {
			if (allowNull) {
				return false;
			} else {
				throw new InterruptedPropertyNodeException(this);
			}
		}
		return true;
	}

	// ###########################################################################################################
	// ############################################ FUNCTIONALITY ################################################
	// ###########################################################################################################

	@Override
	public final boolean hasChildrenIn(M model, Context context) {
		return !isNull(model, context) && hasChildren();
	}

	// ###########################################################################################################
	// ############################################### CHILDREN ##################################################
	// ###########################################################################################################

	@Override
	public <C> ReadOnlyModelProperty<M, C> registerChild(String id, Getter<T, C> getter) {
		if (getter == null) {
			throw new IllegalArgumentException("Cannot build a child property with a null getter.");
		}
		return new ReadOnlyModelProperty<M, C>(id, this, new IndexedNodedGetter<T, C>(getter));
	}

	@Override
	public <C> ModelProperty<M, C> registerChild(String id, Getter<T, C> getter, Setter<T, C> setter) {
		if (getter == null) {
			throw new IllegalArgumentException("Cannot build a child property with a null getter.");
		} else if (setter == null) {
			throw new IllegalArgumentException("Cannot build a child property with a null setter.");
		}
		return new ModelProperty<M, C>(id, this, new IndexedNodedGetter<T, C>(getter),
				new IndexedNodedSetter<T, C>(setter));
	}

	@Override
	public <C> ReadOnlyModelPropertyList<M, C> registerChildList(String id, Getter<T, List<C>> getter) {
		if (getter == null) {
			throw new IllegalArgumentException("Cannot build a listed child property with a null getter.");
		}
		return new ReadOnlyModelPropertyList<M, C>(id, this, new IndexedNodedGetter<T, List<C>>(getter));
	}

	@Override
	public <C> ModelPropertyList<M, C> registerChildList(String id, Getter<T, List<C>> getter,
			Setter<T, List<C>> setter) {
		if (getter == null) {
			throw new IllegalArgumentException("Cannot build a listed child property with a null getter.");
		} else if (setter == null) {
			throw new IllegalArgumentException("Cannot build a listed child property with a null setter.");
		}
		return new ModelPropertyList<M, C>(id, this, new IndexedNodedGetter<T, List<C>>(getter),
				new IndexedNodedSetter<T, List<C>>(setter));
	}

	@Override
	public <C> ReadOnlyModelPropertyNode<M, C> registerChildNode(String id, Getter<T, C> getter,
			Getter<C, List<C>> leafGetter) {
		if (getter == null) {
			throw new IllegalArgumentException("Cannot build a noded child property with a null getter.");
		} else if (leafGetter == null) {
			throw new IllegalArgumentException("Cannot build a noded child property with a null leaf getter.");
		}
		return new ReadOnlyModelPropertyNode<M, C>(id, this, new IndexedNodedGetter<>(getter), leafGetter);
	}

	@Override
	public <C> ModelPropertyNode<M, C> registerChildNode(String id, Getter<T, C> getter, Setter<T, C> setter,
			Getter<C, List<C>> leafGetter) {
		if (getter == null) {
			throw new IllegalArgumentException("Cannot build a noded child property with a null getter.");
		} else if (setter == null) {
			throw new IllegalArgumentException("Cannot build a noded child property with a null setter.");
		} else if (leafGetter == null) {
			throw new IllegalArgumentException("Cannot build a noded child property with a null leaf getter.");
		}
		return new ModelPropertyNode<M, C>(id, this, new IndexedNodedGetter<>(getter),
				new IndexedNodedSetter<>(setter), leafGetter);
	}

	// ###########################################################################################################
	// ############################################## OPERATIONS #################################################
	// ###########################################################################################################

	@Override
	public int[] keyAt(M model, Integer operator, Context context) {
		context = context == null ? DefaultContext.EMPTY : context;
		T value = get(model, context);
		checkParent(value, false);
		List<T> leaves = this.leafGetter.get(value);
		int[] path = context.containsKey(this) ? context.getKey(this) : new int[0];
		path = Arrays.copyOf(path, path.length+1);
		if (operator == null) {
			path[path.length-1] = leaves.size();
			return leaves.isEmpty() ? null : path;
		} else {
			checkIndex(leaves.size(), operator, false);
			path[path.length-1] = operator;
			return path;
		}
	}
	
	@Override
	public T elementAt(M model, Integer operator, Context context) {
		context = context == null ? DefaultContext.EMPTY : context;
		T value = get(model, context);
		checkParent(value, false);
		List<T> leaves = this.leafGetter.get(value);
		if (operator == null) {
			return leaves.isEmpty() ? null : leaves.get(leaves.size()-1);
		} else {
			checkIndex(leaves.size(), operator, false);
			return leaves.get(operator);
		}
	}
	
	@Override
	public void add(M model, T element, Context context) {
		context = context == null ? DefaultContext.EMPTY : context;
		T value = get(model, context);
		checkParent(value, false);
		this.leafGetter.get(value).add(element);
	}
	
	@Override
	public void addAt(M model, T element, Integer operator, Context context) {
		if (operator == null) {
			add(model, element, context);
		} else {
			context = context == null ? DefaultContext.EMPTY : context;
			T value = get(model, context);
			checkParent(value, false);
			List<T> leaves = this.leafGetter.get(value);
			checkIndex(leaves.size(), operator, false);
			leaves.add(operator, element);
		}
	}

	@Override
	public T remove(M model, Context context) {
		context = context == null ? DefaultContext.EMPTY : context;
		T value = get(model, context);
		checkParent(value, false);
		List<T> leaves = this.leafGetter.get(value);
		if (leaves.isEmpty()) {
			return null;
		} else {
			return leaves.remove(leaves.size()-1);
		}
	}
	
	@Override
	public T removeAt(M model, Integer operator, Context context) {
		if (operator == null) {
			return remove(model, context);
		} else {
			context = context == null ? DefaultContext.EMPTY : context;
			T value = get(model, context);
			checkParent(value, false);
			List<T> leaves = this.leafGetter.get(value);
			checkIndex(leaves.size(), operator, true);
			return leaves.remove((int) operator);
		}
	}

	@Override
	public Integer remove(M model, T element, Context context) {
		context = context == null ? DefaultContext.EMPTY : context;
		T value = get(model, context);
		checkParent(value, false);
		List<T> leaves = this.leafGetter.get(value);
		int index = leaves.indexOf(element);
		if (index == -1) {
			return null;
		} else {
			leaves.remove(index);
			return index;
		}
	}
}
