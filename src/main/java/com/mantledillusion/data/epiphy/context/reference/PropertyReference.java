package com.mantledillusion.data.epiphy.context.reference;

import com.mantledillusion.data.epiphy.Property;
import com.mantledillusion.data.epiphy.context.function.ExtractableProperties;

/**
 * Base type for references to {@link ExtractableProperties} implementations.
 *
 * @param <P>
 *            The property type, which is the {@link ExtractableProperties}
 *            implementation this reference is meant for.
 * @param <R>
 *            The reference type, which is the type the
 *            {@link ExtractableProperties} implementation requires as reference.
 */
public abstract class PropertyReference<P extends Property<?, ?>, R> {

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
