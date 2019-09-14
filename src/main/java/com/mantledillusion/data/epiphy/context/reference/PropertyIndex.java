package com.mantledillusion.data.epiphy.context.reference;

import com.mantledillusion.data.epiphy.Property;

import java.util.List;

/**
 * Represents a single property index.
 */
public final class PropertyIndex extends PropertyReference<Property<? extends List<?>, ?>, Integer> {

	private PropertyIndex(Property<? extends List<?>, ?> property, int key) {
		super(property, key);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + getReference();
		result = prime * result + ((getProperty() == null) ? 0 : getProperty().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PropertyIndex other = (PropertyIndex) obj;
		if (getReference() != other.getReference())
			return false;
		if (getProperty() == null) {
			if (other.getProperty() != null)
				return false;
		} else if (!getProperty().equals(other.getProperty()))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PropertyIndex [key=" + getProperty() + ", index=" + this.getReference() + "]";
	}

	/**
	 * Creates a new {@link PropertyIndex}.
	 *
	 * @param <E> The type of the listed element.
	 * @param listedProperty
	 *            The listed property this {@link PropertyIndex} indexes; might
	 *            <b>not</b> be null.
	 * @param index
	 *            The index the given property has to have.
	 * @return A new {@link PropertyIndex}, never null
	 */
	public static <E> PropertyIndex of(Property<? extends List<E>, E> listedProperty, Integer index) {
		if (listedProperty == null) {
			throw new IllegalArgumentException("Cannot create an index for a null listed property.");
		} else if (index == null) {
			throw new IllegalArgumentException("Cannot create an index for a null index.");
		}
		return new PropertyIndex(listedProperty, index);
	}
}