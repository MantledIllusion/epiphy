package com.mantledillusion.data.epiphy;

import java.lang.reflect.Method;
import java.util.List;

import com.mantledillusion.data.epiphy.exception.InterruptedPropertyPathException;
import com.mantledillusion.data.epiphy.index.IndexContext;
import com.mantledillusion.data.epiphy.io.Getter;
import com.mantledillusion.data.epiphy.io.Setter;

/**
 * Implementation of {@link ModelProperty} that represents a definite, single
 * model property.
 * <p>
 * On a {@link DefiniteModelProperty} instance, the {@link Method}s...<br>
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
public final class DefiniteModelProperty<M, T> extends ModelProperty<M, T> {

	private final class IndexedDefiniteGetter<P, C> implements IndexedGetter<P, C> {

		private final Getter<P, C> setter;

		private IndexedDefiniteGetter(Getter<P, C> setter) {
			this.setter = setter;
		}

		@Override
		public C get(P source, IndexContext context) {
			checkParent(source);
			return this.setter.get(source);
		}
	}

	private final class IndexedDefiniteSetter<P, C> implements IndexedSetter<P, C> {

		private final Setter<P, C> setter;

		private IndexedDefiniteSetter(Setter<P, C> setter) {
			this.setter = setter;
		}

		@Override
		public void set(P target, C value, IndexContext context) {
			checkParent(target);
			this.setter.set(target, value);
		}
	}

	DefiniteModelProperty(String id) {
		super(id);
	}

	<P> DefiniteModelProperty(String id, ModelProperty<M, P> parent, IndexedGetter<P, T> getter,
			IndexedSetter<P, T> setter) {
		super(id, parent, getter, setter, false);
	}

	/**
	 * Registers a child to this {@link DefiniteModelProperty} that can be accessed
	 * by the given {@link Getter}/{@link Setter} combination.
	 * 
	 * @param <C>
	 *            The child properties' type.
	 * @param getter
	 *            The {@link Getter} that can be applied onto this
	 *            {@link DefiniteModelProperty} to retrieve the child property;
	 *            might <b>NOT</b> be null.
	 * @param setter
	 *            The {@link Setter} that can be applied onto this
	 *            {@link DefiniteModelProperty} to set the child property; might
	 *            <b>NOT</b> be null.
	 * @return The instantiated child property; never null
	 */
	public <C> DefiniteModelProperty<M, C> registerChild(Getter<T, C> getter, Setter<T, C> setter) {
		return registerChild(null, getter, setter);
	}

	/**
	 * Registers an id'ed child to this {@link DefiniteModelProperty} that can be
	 * accessed by the given {@link Getter}/{@link Setter} combination.
	 * 
	 * @param <C>
	 *            The child properties' type.
	 * @param id
	 *            The id the returned child {@link DefiniteModelProperty} will be
	 *            identified by; might be null. All parent id's (including this
	 *            {@link DefiniteModelProperty}'s id) will be prepended.
	 * @param getter
	 *            The {@link Getter} that can be applied onto this
	 *            {@link DefiniteModelProperty} to retrieve the child
	 *            {@link DefiniteModelProperty}; might <b>NOT</b> be null.
	 * @param setter
	 *            The {@link Setter} that can be applied onto this
	 *            {@link DefiniteModelProperty} to set the child
	 *            {@link DefiniteModelProperty}; might <b>NOT</b> be null.
	 * @return The instantiated child property; never null
	 */
	public <C> DefiniteModelProperty<M, C> registerChild(String id, Getter<T, C> getter, Setter<T, C> setter) {
		if (getter == null) {
			throw new IllegalArgumentException("Cannot build a child property with a null getter.");
		} else if (setter == null) {
			throw new IllegalArgumentException("Cannot build a child property with a null setter.");
		}
		return new DefiniteModelProperty<M, C>(id, this, new IndexedDefiniteGetter<T, C>(getter),
				new IndexedDefiniteSetter<T, C>(setter));
	}

	/**
	 * Registers a listed child to this {@link DefiniteModelProperty} that can be
	 * accessed by the given {@link Getter}/{@link Setter} combination.
	 * 
	 * @param <C>
	 *            The child properties' type.
	 * @param getter
	 *            The {@link Getter} that can be applied onto this
	 *            {@link DefiniteModelProperty} to retrieve the listed child
	 *            property; might <b>NOT</b> be null.
	 * @param setter
	 *            The {@link Setter} that can be applied onto this
	 *            {@link DefiniteModelProperty} to set the listed child property;
	 *            might <b>NOT</b> be null.
	 * @return The instantiated listed child property; never null
	 */
	public <C> ModelPropertyList<M, C> registerChildList(Getter<T, List<C>> getter, Setter<T, List<C>> setter) {
		return registerChildList(null, getter, setter);
	}

	/**
	 * Registers an id'ed listed child to this {@link DefiniteModelProperty} that
	 * can be accessed by the given {@link Getter}/{@link Setter} combination.
	 * 
	 * @param <C>
	 *            The child properties' type.
	 * @param id
	 *            The id the returned listed child {@link ModelPropertyList} will be
	 *            identified by; might be null. All parent id's (including this
	 *            {@link DefiniteModelProperty}'s id) will be prepended.
	 * @param getter
	 *            The {@link Getter} that can be applied onto this
	 *            {@link DefiniteModelProperty} to retrieve the listed child
	 *            property; might <b>NOT</b> be null.
	 * @param setter
	 *            The {@link Setter} that can be applied onto this
	 *            {@link DefiniteModelProperty} to set the listed child property;
	 *            might <b>NOT</b> be null.
	 * @return The instantiated listed child property; never null
	 */
	public <C> ModelPropertyList<M, C> registerChildList(String id, Getter<T, List<C>> getter,
			Setter<T, List<C>> setter) {
		if (getter == null) {
			throw new IllegalArgumentException("Cannot build a listed child property with a null getter.");
		} else if (setter == null) {
			throw new IllegalArgumentException("Cannot build a listed child property with a null setter.");
		}
		return new ModelPropertyList<M, C>(id, this, new IndexedDefiniteGetter<T, List<C>>(getter),
				new IndexedDefiniteSetter<T, List<C>>(setter));
	}

	private <P> void checkParent(P parent) {
		if (parent == null) {
			throw new InterruptedPropertyPathException(DefiniteModelProperty.this);
		}
	}
}