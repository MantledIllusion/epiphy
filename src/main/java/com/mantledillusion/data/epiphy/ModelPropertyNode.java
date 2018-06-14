package com.mantledillusion.data.epiphy;

import java.util.Arrays;
import java.util.List;

import com.mantledillusion.data.epiphy.context.Context;
import com.mantledillusion.data.epiphy.context.impl.DefaultContext;
import com.mantledillusion.data.epiphy.exception.OutboundPropertyNodeException;
import com.mantledillusion.data.epiphy.interfaces.WriteableProperty;
import com.mantledillusion.data.epiphy.io.Getter;
import com.mantledillusion.data.epiphy.io.IndexedGetter;
import com.mantledillusion.data.epiphy.io.IndexedSetter;

public class ModelPropertyNode<M, T> extends NodedModelProperty<M, T> implements WriteableProperty<M, T> {

	private final IndexedGetter<?, T> getter;
	private final IndexedSetter<?, T> setter;

	ModelPropertyNode(String id, Getter<T, List<T>> leafGetter) {
		this(id, null, null, null, leafGetter);
	}

	<P> ModelPropertyNode(String id, AbstractModelProperty<M, P> parent, IndexedGetter<P, T> getter,
			IndexedSetter<P, T> setter, Getter<T, List<T>> leafGetter) {
		super(id, parent, leafGetter);
		this.getter = getter;
		this.setter = setter;
	}

	// ###########################################################################################################
	// ############################################## OPERATIONS #################################################
	// ###########################################################################################################

	@Override
	public T get(M model, Context context, boolean allowNull) {
		context = context == null ? DefaultContext.EMPTY : context;
		T value = PropertyUtils.castAndGet(model, context, allowNull, this.parent, this.getter);
		value = locate(value, context.getKey(this), 0, allowNull);
		return value;
	}

	@Override
	public void set(M model, T value, Context context) {
		context = context == null ? DefaultContext.EMPTY : context;
		int[] nodeIndices = context.getKey(this);
		if (nodeIndices == null || nodeIndices.length == 0) {
			PropertyUtils.castAndSet(model, value, context, this.parent, this.setter);
		} else {
			T target = PropertyUtils.castAndGet(model, context, false, this.parent, this.getter);
			int setIdx = nodeIndices[nodeIndices.length-1];
			nodeIndices = Arrays.copyOf(nodeIndices, nodeIndices.length-1);
			target = locate(target, nodeIndices, 0, false);
			checkParent(target, false);
			List<T> leaves = this.leafGetter.get(target);
			if (leaves == null || leaves.size() <= setIdx) {
				throw new OutboundPropertyNodeException(this, setIdx, leaves.size());
			}
			leaves.set(setIdx, value);
		}
	}
}
