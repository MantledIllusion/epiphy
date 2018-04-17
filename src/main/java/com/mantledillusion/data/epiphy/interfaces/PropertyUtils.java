package com.mantledillusion.data.epiphy.interfaces;

import com.mantledillusion.data.epiphy.index.IndexContext;
import com.mantledillusion.data.epiphy.io.IndexedGetter;
import com.mantledillusion.data.epiphy.io.IndexedSetter;

class PropertyUtils {

	@SuppressWarnings("unchecked")
	static <M, P, T> T castAndGet(ReadableProperty<M, T> property, M model, IndexContext context, boolean allowNull) {
		P parentValue = ((ReadableProperty<M, P>) property.getParent()).get(model, context, allowNull);
		return ((IndexedGetter<P, T>) property.getter()).get(parentValue, context, allowNull);
	}
	
	@SuppressWarnings("unchecked")
	static <M, P, T> void castAndSet(WriteableProperty<M, T> property, M model, T value, IndexContext context) {
		P parentValue = ((ReadableProperty<M, P>) property.getParent()).get(model, context);
		((IndexedSetter<P, T>) property.setter()).set(parentValue, value, context);
	}
}
