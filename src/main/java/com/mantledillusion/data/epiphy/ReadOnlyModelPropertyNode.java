package com.mantledillusion.data.epiphy;

import java.util.List;

import com.mantledillusion.data.epiphy.context.Context;
import com.mantledillusion.data.epiphy.context.impl.DefaultContext;
import com.mantledillusion.data.epiphy.io.Getter;
import com.mantledillusion.data.epiphy.io.IndexedGetter;

public class ReadOnlyModelPropertyNode<M, T> extends NodedModelProperty<M, T> {

	private final IndexedGetter<?, T> getter;

	ReadOnlyModelPropertyNode(String id, Getter<T, List<T>> leafGetter) {
		this(id, null, null, leafGetter);
	}

	<P> ReadOnlyModelPropertyNode(String id, AbstractModelProperty<M, P> parent, IndexedGetter<P, T> getter, Getter<T, List<T>> leafGetter) {
		super(id, parent, leafGetter);
		this.getter = getter;
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
}
