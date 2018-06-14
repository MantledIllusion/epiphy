package com.mantledillusion.data.epiphy;

import com.mantledillusion.data.epiphy.context.Context;
import com.mantledillusion.data.epiphy.interfaces.WriteableProperty;
import com.mantledillusion.data.epiphy.interfaces.type.DefiniteProperty;
import com.mantledillusion.data.epiphy.io.IndexedGetter;
import com.mantledillusion.data.epiphy.io.IndexedSetter;

/**
 * Implementation of {@link DefiniteProperty} and {@link WriteableProperty} that
 * represents a definite, writable model property.
 * 
 * @param <M>
 *            The root model type of this {@link ModelProperty}'s property tree.
 * @param <T>
 *            The type of the property this {@link ModelProperty} represents.
 */
public final class ModelProperty<M, T> extends DefiniteModelProperty<M, T> implements WriteableProperty<M, T> {

	private final IndexedGetter<?, T> getter;
	private final IndexedSetter<?, T> setter;

	ModelProperty(String id) {
		this(id, null, null, null);
	}

	<P> ModelProperty(String id, AbstractModelProperty<M, P> parent, IndexedGetter<P, T> getter,
			IndexedSetter<P, T> setter) {
		super(id, parent);
		this.getter = getter;
		this.setter = setter;
	}

	// ###########################################################################################################
	// ############################################## OPERATIONS #################################################
	// ###########################################################################################################

	@Override
	public T get(M model, Context context, boolean allowNull) {
		return PropertyUtils.castAndGet(model, context, allowNull, this.parent, this.getter);
	}

	@Override
	public void set(M model, T value, Context context) {
		PropertyUtils.castAndSet(model, value, context, this.parent, this.setter);
	}
}