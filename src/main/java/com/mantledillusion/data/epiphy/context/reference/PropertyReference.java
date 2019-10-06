package com.mantledillusion.data.epiphy.context.reference;

import com.mantledillusion.data.epiphy.Property;

/**
 * Base type for references to {@link Property}s whose values are elements to a batch.
 *
 * @param <P>
 * 			The type of {@link Property} being referenced
 * @param <R>
 * 			The reference type, which is the type the {@link Property} requires as reference.
 */
public abstract class PropertyReference<P extends Property<?, ?>, R> {

	private final P property;
	private final R reference;

	PropertyReference(P property, R reference) {
		this.property = property;
		this.reference = reference;
	}

	/**
	 * Returns the {@link Property} of this {@link PropertyReference}.
	 * 
	 * @return The {@link Property}, never null
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
