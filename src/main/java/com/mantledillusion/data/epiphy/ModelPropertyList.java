package com.mantledillusion.data.epiphy;

import java.util.List;

import com.mantledillusion.data.epiphy.context.Context;
import com.mantledillusion.data.epiphy.interfaces.WriteableProperty;
import com.mantledillusion.data.epiphy.interfaces.type.ListedProperty;
import com.mantledillusion.data.epiphy.io.IndexedGetter;
import com.mantledillusion.data.epiphy.io.IndexedSetter;

/**
 * Implementation of {@link ListedProperty} and {@link WriteableProperty} that
 * represents a listed, writable model property.
 *
 * @param <M>
 *            The root model type of this {@link ModelPropertyList}'s property
 *            tree.
 * @param <E>
 *            The list element type of the property list this
 *            {@link ModelPropertyList} represents.
 */
public final class ModelPropertyList<M, E> extends ListedModelProperty<M, E> implements WriteableProperty<M, List<E>> {

	private final IndexedGetter<?, List<E>> getter;
	private final IndexedSetter<?, List<E>> setter;

	<P> ModelPropertyList(String id) {
		this(id, null, null, null);
	}

	<P> ModelPropertyList(String id, AbstractModelProperty<M, P> parent, IndexedGetter<P, List<E>> getter,
			IndexedSetter<P, List<E>> setter) {
		super(id, parent);
		this.getter = getter;
		this.setter = setter;
	}

	// ###########################################################################################################
	// ############################################## OPERATIONS #################################################
	// ###########################################################################################################

	@Override
	public List<E> get(M model, Context context, boolean allowNull) {
		return PropertyUtils.castAndGet(model, context, allowNull, this.parent, this.getter);
	}
	
	@Override
	public void set(M model, List<E> value, Context context) {
		PropertyUtils.castAndSet(model, value, context, this.parent, this.setter);
	}
}