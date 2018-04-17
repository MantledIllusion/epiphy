package com.mantledillusion.data.epiphy;

import java.util.List;

import com.mantledillusion.data.epiphy.exception.InterruptedPropertyPathException;
import com.mantledillusion.data.epiphy.index.IndexContext;
import com.mantledillusion.data.epiphy.io.Getter;
import com.mantledillusion.data.epiphy.io.IndexedGetter;
import com.mantledillusion.data.epiphy.io.IndexedSetter;
import com.mantledillusion.data.epiphy.io.Setter;

abstract class DefiniteModelProperty<M, T> extends AbstractModelProperty<M, T> {

	private final class IndexedDefiniteGetter<P, C> implements IndexedGetter<P, C> {

		private final Getter<P, C> setter;

		private IndexedDefiniteGetter(Getter<P, C> setter) {
			this.setter = setter;
		}

		@Override
		public C get(P source, IndexContext context, boolean allowNull) {
			if (checkParent(source, allowNull)) {
				return this.setter.get(source);
			} else {
				return null;
			}
		}
	}

	private final class IndexedDefiniteSetter<P, C> implements IndexedSetter<P, C> {

		private final Setter<P, C> setter;

		private IndexedDefiniteSetter(Setter<P, C> setter) {
			this.setter = setter;
		}

		@Override
		public void set(P target, C value, IndexContext context) {
			checkParent(target, false);
			this.setter.set(target, value);
		}
	}

	<P> DefiniteModelProperty(String id) {
		super(id, null, false);
	}

	<P> DefiniteModelProperty(String id, AbstractModelProperty<M, P> parent) {
		super(id, parent, false);
	}

	@Override
	public final boolean hasChildrenIn(M model, IndexContext context) {
		return !isNull(model, context) && hasChildren();
	}

	/**
	 * Registers a child to this {@link ModelProperty} that can be accessed
	 * by the given {@link Getter}/{@link Setter} combination.
	 * 
	 * @param <C>
	 *            The child properties' type.
	 * @param getter
	 *            The {@link Getter} that can be applied onto this
	 *            {@link ModelProperty} to retrieve the child property;
	 *            might <b>NOT</b> be null.
	 * @param setter
	 *            The {@link Setter} that can be applied onto this
	 *            {@link ModelProperty} to set the child property; might
	 *            <b>NOT</b> be null.
	 * @return The instantiated child property; never null
	 */
	public <C> ModelProperty<M, C> registerChild(Getter<T, C> getter, Setter<T, C> setter) {
		return registerChild(null, getter, setter);
	}

	/**
	 * Registers an id'ed child to this {@link ModelProperty} that can be
	 * accessed by the given {@link Getter}/{@link Setter} combination.
	 * 
	 * @param <C>
	 *            The child properties' type.
	 * @param id
	 *            The id the returned child {@link ModelProperty} will be
	 *            identified by; might be null. All parent id's (including this
	 *            {@link ModelProperty}'s id) will be prepended.
	 * @param getter
	 *            The {@link Getter} that can be applied onto this
	 *            {@link ModelProperty} to retrieve the child
	 *            {@link ModelProperty}; might <b>NOT</b> be null.
	 * @param setter
	 *            The {@link Setter} that can be applied onto this
	 *            {@link ModelProperty} to set the child
	 *            {@link ModelProperty}; might <b>NOT</b> be null.
	 * @return The instantiated child property; never null
	 */
	public <C> ModelProperty<M, C> registerChild(String id, Getter<T, C> getter, Setter<T, C> setter) {
		if (getter == null) {
			throw new IllegalArgumentException("Cannot build a child property with a null getter.");
		} else if (setter == null) {
			throw new IllegalArgumentException("Cannot build a child property with a null setter.");
		}
		return new ModelProperty<M, C>(id, this, new IndexedDefiniteGetter<T, C>(getter),
				new IndexedDefiniteSetter<T, C>(setter));
	}

	/**
	 * Registers a listed child to this {@link ModelProperty} that can be
	 * accessed by the given {@link Getter}/{@link Setter} combination.
	 * 
	 * @param <C>
	 *            The child properties' type.
	 * @param getter
	 *            The {@link Getter} that can be applied onto this
	 *            {@link ModelProperty} to retrieve the listed child
	 *            property; might <b>NOT</b> be null.
	 * @param setter
	 *            The {@link Setter} that can be applied onto this
	 *            {@link ModelProperty} to set the listed child property;
	 *            might <b>NOT</b> be null.
	 * @return The instantiated listed child property; never null
	 */
	public <C> ModelPropertyList<M, C> registerChildList(Getter<T, List<C>> getter, Setter<T, List<C>> setter) {
		return registerChildList(null, getter, setter);
	}

	/**
	 * Registers an id'ed listed child to this {@link ModelProperty} that
	 * can be accessed by the given {@link Getter}/{@link Setter} combination.
	 * 
	 * @param <C>
	 *            The child properties' type.
	 * @param id
	 *            The id the returned listed child {@link ModelPropertyList} will be
	 *            identified by; might be null. All parent id's (including this
	 *            {@link ModelProperty}'s id) will be prepended.
	 * @param getter
	 *            The {@link Getter} that can be applied onto this
	 *            {@link ModelProperty} to retrieve the listed child
	 *            property; might <b>NOT</b> be null.
	 * @param setter
	 *            The {@link Setter} that can be applied onto this
	 *            {@link ModelProperty} to set the listed child property;
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

	private <P> boolean checkParent(P parent, boolean allowNull) {
		if (parent == null) {
			if (allowNull) {
				return false;
			} else {
				throw new InterruptedPropertyPathException(DefiniteModelProperty.this);
			}
		}
		return true;
	}
}
