package com.mantledillusion.data.epiphy.context;

import com.mantledillusion.data.epiphy.interfaces.type.ListedProperty;

/**
 * Represents a single property index, which is basically an
 * {@link ListedProperty}-&gt;int pair.
 * <p>
 * {@link PropertyIndex}es can be created using the
 * {@link #of(ListedProperty, int)} method.
 */
public final class PropertyIndex extends PropertyReference<ListedProperty<?, ?>, Integer> {

	private PropertyIndex(ListedProperty<?, ?> property, int key) {
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
	 * @param listedProperty
	 *            The listed property this {@link PropertyIndex} indexes; might
	 *            <b>not</b> be null.
	 * @param index
	 *            The index the given property has to have.
	 * @return A new {@link PropertyIndex}, never null
	 */
	public static PropertyIndex of(ListedProperty<?, ?> listedProperty, int index) {
		if (listedProperty == null) {
			throw new IllegalArgumentException("Cannot create an index for a null listed property.");
		}
		return new PropertyIndex(listedProperty, index);
	}
}