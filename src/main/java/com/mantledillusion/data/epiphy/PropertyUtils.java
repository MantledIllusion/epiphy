package com.mantledillusion.data.epiphy;

import com.mantledillusion.data.epiphy.context.Context;
import com.mantledillusion.data.epiphy.context.impl.DefaultContext;
import com.mantledillusion.data.epiphy.interfaces.ReadableProperty;
import com.mantledillusion.data.epiphy.io.ContextedGetter;
import com.mantledillusion.data.epiphy.io.ContextedSetter;

class PropertyUtils {

	@SuppressWarnings("unchecked")
	static <M, P, T> T castAndGet(M model, Context context, boolean allowNull, ReadableProperty<M, ?> parent, ContextedGetter<?, T> getter) {
		if (parent == null) {
			return (T) model;
		} else {
			context = context == null ? DefaultContext.EMPTY : context;
			P parentValue = ((ReadableProperty<M, P>) parent).get(model, context, allowNull);
			return ((ContextedGetter<P, T>) getter).get(parentValue, context, allowNull);
		}
	}

	@SuppressWarnings("unchecked")
	static <M, P, T> void castAndSet(M model, T value, Context context, ReadableProperty<M, ?> parent, ContextedSetter<?, T> setter) {
		if (parent != null) {
			context = context == null ? DefaultContext.EMPTY : context;
			P parentValue = ((ReadableProperty<M, P>) parent).get(model, context);
			((ContextedSetter<P, T>) setter).set(parentValue, value, context);
		}
	}
}
