package com.mantledillusion.data.epiphy;

import com.mantledillusion.data.epiphy.interfaces.DefiniteProperty;
import com.mantledillusion.data.epiphy.interfaces.WriteableProperty;
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

	@Override
	public IndexedGetter<?, T> getter() {
		return this.getter;
	}

	@Override
	public IndexedSetter<?, T> setter() {
		return this.setter;
	}
}