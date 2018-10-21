package com.mantledillusion.data.epiphy.context;

import com.mantledillusion.data.epiphy.interfaces.function.ContextableProperty;
import com.mantledillusion.data.epiphy.interfaces.type.ListedProperty;
import com.mantledillusion.data.epiphy.interfaces.type.NodedProperty;

/**
 * Base type for references to {@link ContextableProperty} implementations.
 * <p>
 * Implementations are:<br>
 * - {@link PropertyIndex} for {@link ListedProperty}s<br>
 * - {@link PropertyRoute} for {@link NodedProperty}s<br>
 *
 * @param <P>
 *            The property type, which is the {@link ContextableProperty}
 *            implementation this reference is meant for.
 * @param <R>
 *            The reference type, which is the type the
 *            {@link ContextableProperty} implementation requires as reference.
 */
public abstract class PropertyReference<P extends ContextableProperty<?, ?, R>, R> {

	private final P property;
	private final R reference;

	PropertyReference(P property, R reference) {
		this.property = property;
		this.reference = reference;
	}

	/**
	 * Returns the property of this {@link PropertyReference}.
	 * 
	 * @return The property, never null
	 */
	public P getProperty() {
		return this.property;
	}

	/**
	 * Returns the reference of this {@link PropertyReference}.
	 * 
	 * @return The reference, never null
	 */
	public R getReference() {
		return reference;
	}

	@Override
	public abstract int hashCode();

	@Override
	public abstract boolean equals(Object obj);

	@Override
	public abstract String toString();
}
