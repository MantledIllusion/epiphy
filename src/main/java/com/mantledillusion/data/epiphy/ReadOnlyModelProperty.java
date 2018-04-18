package com.mantledillusion.data.epiphy;

import com.mantledillusion.data.epiphy.interfaces.DefiniteProperty;
import com.mantledillusion.data.epiphy.interfaces.ReadableProperty;
import com.mantledillusion.data.epiphy.io.IndexedGetter;

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

	private final IndexedGetter<?, T> getter;

	ReadOnlyModelProperty(String id) {
		this(id, null, null);
	}

	<P> ReadOnlyModelProperty(String id, AbstractModelProperty<M, P> parent, IndexedGetter<P, T> getter) {
		super(id, parent);
		this.getter = getter;
	}

	@Override
	public IndexedGetter<?, T> getter() {
		return this.getter;
	}
}