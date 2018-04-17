package com.mantledillusion.data.epiphy;

import java.util.List;

import com.mantledillusion.data.epiphy.interfaces.ListedProperty;
import com.mantledillusion.data.epiphy.interfaces.WriteableProperty;
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

	@Override
	public IndexedGetter<?, List<E>> getter() {
		return this.getter;
	}

	@Override
	public IndexedSetter<?, List<E>> setter() {
		return this.setter;
	}
}