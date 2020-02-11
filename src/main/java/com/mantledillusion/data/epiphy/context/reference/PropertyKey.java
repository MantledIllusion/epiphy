package com.mantledillusion.data.epiphy.context.reference;

import com.mantledillusion.data.epiphy.Property;

import java.util.Map;

/**
 * {@link PropertyReference} for {@link Map} element {@link Property}s.
 */
public final class PropertyKey<K> extends PropertyReference<Property<? extends Map<K, ?>, ?>, K> {

	private PropertyKey(Property<? extends Map<K, ?>, ?> property, K key) {
		super(property, key);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (getReference() == null ? 0 : getReference().hashCode());
		result = prime * result + getProperty().hashCode();
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
		PropertyKey other = (PropertyKey) obj;
		if (getReference() != other.getReference())
			return false;
		if (!getProperty().equals(other.getProperty()))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PropertyKey [property=" + getProperty() + ", key=" + this.getReference() + "]";
	}

	/**
	 * Creates a new {@link PropertyKey}.
	 *
	 * @param <K>
	 *          The key type of the map this {@link Property} represents.
	 * @param <V>
	 *          The value type of the map this {@link Property} represents.
	 * @param mappedProperty
	 * 			The listed property this {@link PropertyKey} contexts; might <b>not</b> be null.
	 * @param key
	 * 			The index of the given {@link Property}'s element; might <b>not</b> be null.
	 * @return A new {@link PropertyKey}, never null
	 */
	public static <K, V> PropertyKey of(Property<? extends Map<K, V>, V> mappedProperty, K key) {
		if (mappedProperty == null) {
			throw new IllegalArgumentException("Cannot create an index for a null mapped property.");
		}
		return new PropertyKey(mappedProperty, key);
	}
}