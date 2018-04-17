package com.mantledillusion.data.epiphy;

import java.lang.reflect.Method;

import com.mantledillusion.data.epiphy.interfaces.WriteableProperty;
import com.mantledillusion.data.epiphy.io.Getter;
import com.mantledillusion.data.epiphy.io.IndexedGetter;
import com.mantledillusion.data.epiphy.io.IndexedSetter;
import com.mantledillusion.data.epiphy.io.Setter;

/**
 * Implementation of {@link WriteableProperty} that represents a definite, single
 * model property.
 * <p>
 * On a {@link ModelProperty} instance, the {@link Method}s...<br>
 * - {@link #registerChild(Getter, Setter)}<br>
 * - {@link #registerChild(String, Getter, Setter)}<br>
 * ... can be used to add a definite child property to that instance. Also, the
 * {@link Method}s...<br>
 * - {@link #registerChildList(Getter, Setter)}<br>
 * - {@link #registerChildList(String, Getter, Setter)}<br>
 * ... can be used to add child property lists to that instance.
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