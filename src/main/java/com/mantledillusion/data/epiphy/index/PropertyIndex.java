package com.mantledillusion.data.epiphy.index;

import com.mantledillusion.data.epiphy.interfaces.ListedProperty;

/**
 * Represents a single property index, which is basically an
 * {@link ListedProperty}-&gt;{@link Integer} pair.
 * <p>
 * {@link PropertyIndex}es can be created using the
 * {@link #of(ListedProperty, int)} method.
 */
public final class PropertyIndex {

	private final ListedProperty<?, ?> key;
	private final Integer value;

	private PropertyIndex(ListedProperty<?, ?> key, Integer value) {
		this.key = key;
		this.value = value;
	}

	/**
	 * Returns the property of this {@link PropertyIndex}.
	 * 
	 * @return The property; never null
	 */
	public ListedProperty<?, ?> getProperty() {
		return key;
	}

	/**
	 * Returns the index of this {@link PropertyIndex}.
	 * 
	 * @return The index; never null
	 */
	public Integer getIndex() {
		return value;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((key == null) ? 0 : key.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
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
		if (key == null) {
			if (other.key != null)
				return false;
		} else if (!key.equals(other.key))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PropertyIndex [key=" + key + ", value=" + value + "]";
	}

	/**
	 * Creates a new {@link PropertyIndex}.
	 * 
	 * @param listedProperty
	 *            The listed property this {@link PropertyIndex} indexes; might
	 *            <b>NOT</b> be null.
	 * @param index
	 *            The index the given property has to have.
	 * @return A new {@link PropertyIndex}; never null
	 */
	public static PropertyIndex of(ListedProperty<?, ?> listedProperty, int index) {
		if (listedProperty == null) {
			throw new IllegalArgumentException("Cannot create an index for a null listed property.");
		}
		return new PropertyIndex(listedProperty, index);
	}
}