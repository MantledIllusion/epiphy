package com.mantledillusion.data.epiphy.context.reference;

/**
 * Defines a value that is referenced.
 * 
 * @param <R>
 *            The reference type of the {@link ReferencedValue}.
 * @param <V>
 *            The value type of the {@link ReferencedValue}.
 */
public final class ReferencedValue<R, V> {

	private final R reference;
	private final V value;

	private ReferencedValue(R reference, V value) {
		this.reference = reference;
		this.value = value;
	}

	/**
	 * Returns the reference that defines the context of the value.
	 * 
	 * @return The reference, never null
	 */
	public R getReference() {
		return reference;
	}

	/**
	 * Returns the referenced value.
	 * 
	 * @return The referenced value, never null
	 */
	public V getValue() {
		return value;
	}

	/**
	 * Creates a new {@link ReferencedValue}.
	 * 
	 * @param <K>
	 *            The reference type of the {@link ReferencedValue}.
	 * @param <V>
	 *            The value type of the {@link ReferencedValue}.
	 * @param propertyKey
	 *            The reference to create the value for; might <b>not</b> be null.
	 * @param value
	 *            The referenced value; might be null.
	 * @return A new {@link ReferencedValue}, never null
	 */
	public static <K, V> ReferencedValue<K, V> of(K propertyKey, V value) {
		if (propertyKey == null) {
			throw new IllegalArgumentException("Cannot create a referenced value with a null property key");
		}
		return new ReferencedValue<K, V>(propertyKey, value);
	}
}
