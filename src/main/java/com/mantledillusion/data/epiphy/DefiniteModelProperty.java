package com.mantledillusion.data.epiphy;

import java.util.List;

import com.mantledillusion.data.epiphy.context.Context;
import com.mantledillusion.data.epiphy.exception.InterruptedPropertyPathException;
import com.mantledillusion.data.epiphy.interfaces.type.DefiniteProperty;
import com.mantledillusion.data.epiphy.io.Getter;
import com.mantledillusion.data.epiphy.io.ContextedGetter;
import com.mantledillusion.data.epiphy.io.ContextedSetter;
import com.mantledillusion.data.epiphy.io.Setter;

abstract class DefiniteModelProperty<M, T> extends AbstractModelProperty<M, T> implements DefiniteProperty<M, T> {

	private final class IndexedDefiniteGetter<P, C> implements ContextedGetter<P, C> {

		private final Getter<P, C> getter;

		private IndexedDefiniteGetter(Getter<P, C> setter) {
			this.getter = setter;
		}

		@Override
		public C get(P source, Context context, boolean allowNull) {
			if (checkParent(source, allowNull)) {
				return this.getter.get(source);
			} else {
				return null;
			}
		}
	}

	private final class IndexedDefiniteSetter<P, C> implements ContextedSetter<P, C> {

		private final Setter<P, C> setter;

		private IndexedDefiniteSetter(Setter<P, C> setter) {
			this.setter = setter;
		}

		@Override
		public void set(P target, C value, Context context) {
			checkParent(target, false);
			this.setter.set(target, value);
		}
	}

	DefiniteModelProperty(String id) {
		super(id, null, false);
	}

	<P> DefiniteModelProperty(String id, AbstractModelProperty<M, P> parent) {
		super(id, parent, false);
	}

	// ###########################################################################################################
	// ############################################### INTERNAL ##################################################
	// ###########################################################################################################

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

	// ###########################################################################################################
	// ############################################ FUNCTIONALITY ################################################
	// ###########################################################################################################

	@Override
	public final boolean hasChildrenIn(M model, Context context) {
		return !isNull(model, context) && hasChildren();
	}

	// ###########################################################################################################
	// ############################################### CHILDREN ##################################################
	// ###########################################################################################################

	@Override
	public <C> ReadOnlyModelProperty<M, C> registerChild(String id, Getter<T, C> getter) {
		if (getter == null) {
			throw new IllegalArgumentException("Cannot build a child property with a null getter.");
		}
		return new ReadOnlyModelProperty<M, C>(id, this, new IndexedDefiniteGetter<T, C>(getter));
	}

	@Override
	public <C> ModelProperty<M, C> registerChild(String id, Getter<T, C> getter, Setter<T, C> setter) {
		if (getter == null) {
			throw new IllegalArgumentException("Cannot build a child property with a null getter.");
		} else if (setter == null) {
			throw new IllegalArgumentException("Cannot build a child property with a null setter.");
		}
		return new ModelProperty<M, C>(id, this, new IndexedDefiniteGetter<T, C>(getter),
				new IndexedDefiniteSetter<T, C>(setter));
	}

	@Override
	public <C> ReadOnlyModelPropertyList<M, C> registerChildList(String id, Getter<T, List<C>> getter) {
		if (getter == null) {
			throw new IllegalArgumentException("Cannot build a listed child property with a null getter.");
		}
		return new ReadOnlyModelPropertyList<M, C>(id, this, new IndexedDefiniteGetter<T, List<C>>(getter));
	}

	@Override
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

	@Override
	public <C> ReadOnlyModelPropertyNode<M, C> registerChildNode(String id, Getter<T, C> getter,
			Getter<C, List<C>> leafGetter) {
		if (getter == null) {
			throw new IllegalArgumentException("Cannot build a noded child property with a null getter.");
		} else if (leafGetter == null) {
			throw new IllegalArgumentException("Cannot build a noded child property with a null leaf getter.");
		}
		return new ReadOnlyModelPropertyNode<M, C>(id, this, new IndexedDefiniteGetter<>(getter), leafGetter);
	}

	@Override
	public <C> ModelPropertyNode<M, C> registerChildNode(String id, Getter<T, C> getter, Setter<T, C> setter,
			Getter<C, List<C>> leafGetter) {
		if (getter == null) {
			throw new IllegalArgumentException("Cannot build a noded child property with a null getter.");
		} else if (setter == null) {
			throw new IllegalArgumentException("Cannot build a noded child property with a null setter.");
		} else if (leafGetter == null) {
			throw new IllegalArgumentException("Cannot build a noded child property with a null leaf getter.");
		}
		return new ModelPropertyNode<M, C>(id, this, new IndexedDefiniteGetter<>(getter),
				new IndexedDefiniteSetter<>(setter), leafGetter);
	}
}
