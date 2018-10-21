package com.mantledillusion.data.epiphy;

import com.mantledillusion.data.epiphy.context.Context;
import com.mantledillusion.data.epiphy.interfaces.ReadableProperty;
import com.mantledillusion.data.epiphy.interfaces.type.DefiniteProperty;
import com.mantledillusion.data.epiphy.io.ContextedGetter;

/**
 * Implementation of {@link DefiniteProperty} and {@link ReadableProperty} that
 * represents a definite, read-only model property.
 * 
 * @param <M>
 *            The root model type of this {@link ReadOnlyModelProperty}'s
 *            property tree.
 * @param <T>
 *            The type of the property this {@link ReadOnlyModelProperty}
 *            represents.
 */
public final class ReadOnlyModelProperty<M, T> extends DefiniteModelProperty<M, T> implements ReadableProperty<M, T> {

	private final ContextedGetter<?, T> getter;

	ReadOnlyModelProperty(String id) {
		this(id, null, null);
	}

	<P> ReadOnlyModelProperty(String id, AbstractModelProperty<M, P> parent, ContextedGetter<P, T> getter) {
		super(id, parent);
		this.getter = getter;
	}

	// ###########################################################################################################
	// ############################################## OPERATIONS #################################################
	// ###########################################################################################################

	@Override
	public T get(M model, Context context, boolean allowNull) {
		return PropertyUtils.castAndGet(model, context, allowNull, this.parent, this.getter);
	}
}