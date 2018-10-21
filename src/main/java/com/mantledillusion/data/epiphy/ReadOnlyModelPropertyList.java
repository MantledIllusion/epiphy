package com.mantledillusion.data.epiphy;

import java.util.List;

import com.mantledillusion.data.epiphy.context.Context;
import com.mantledillusion.data.epiphy.interfaces.ReadableProperty;
import com.mantledillusion.data.epiphy.interfaces.type.ListedProperty;
import com.mantledillusion.data.epiphy.io.ContextedGetter;

/**
 * Implementation of {@link ListedProperty} and {@link ReadableProperty} that
 * represents a listed, read-only model property.
 *
 * @param <M>
 *            The root model type of this {@link ReadOnlyModelPropertyList}'s property
 *            tree.
 * @param <E>
 *            The list element type of the property list this
 *            {@link ReadOnlyModelPropertyList} represents.
 */
public final class ReadOnlyModelPropertyList<M, E> extends ListedModelProperty<M, E> implements ReadableProperty<M, List<E>> {

	private final ContextedGetter<?, List<E>> getter;

	ReadOnlyModelPropertyList(String id) {
		this(id, null, null);
	}

	<P> ReadOnlyModelPropertyList(String id, AbstractModelProperty<M, P> parent, ContextedGetter<P, List<E>> getter) {
		super(id, parent);
		this.getter = getter;
	}

	// ###########################################################################################################
	// ############################################## OPERATIONS #################################################
	// ###########################################################################################################

	@Override
	public List<E> get(M model, Context context, boolean allowNull) {
		return PropertyUtils.castAndGet(model, context, allowNull, this.parent, this.getter);
	}
}