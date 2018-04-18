package com.mantledillusion.data.epiphy;

import java.util.List;

import com.mantledillusion.data.epiphy.interfaces.ListedProperty;
import com.mantledillusion.data.epiphy.interfaces.ReadableProperty;
import com.mantledillusion.data.epiphy.io.IndexedGetter;

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

	private final IndexedGetter<?, List<E>> getter;

	<P> ReadOnlyModelPropertyList(String id) {
		this(id, null, null);
	}

	<P> ReadOnlyModelPropertyList(String id, AbstractModelProperty<M, P> parent, IndexedGetter<P, List<E>> getter) {
		super(id, parent);
		this.getter = getter;
	}

	@Override
	public IndexedGetter<?, List<E>> getter() {
		return this.getter;
	}
}