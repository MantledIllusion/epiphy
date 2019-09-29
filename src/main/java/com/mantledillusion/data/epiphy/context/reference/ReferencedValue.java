package com.mantledillusion.data.epiphy.context.reference;

/**
 * Defines a tuple of a {@link PropertyReference} and the value it contexts.
 * 
 * @param <R>
 * 			The {@link PropertyReference} type of the {@link ReferencedValue}.
 * @param <V>
 * 			The value type of the {@link ReferencedValue}.
 */
public final class ReferencedValue<R, V> {

	private final R reference;
	private final V value;

	private ReferencedValue(R reference, V value) {
		this.reference = reference;
		this.value = value;
	}

	/**
	 * Returns the {@link PropertyReference} that defines the context of the value.
	 * 
	 * @return The {@link PropertyReference}, never null
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
	 * @param <R>
	 * 			The {@link PropertyReference} type of the {@link ReferencedValue}.
	 * @param <V>
	 * 			The value type of the {@link ReferencedValue}.
	 * @param reference
	 * 			The {@link PropertyReference} to create the value for; might <b>not</b> be null.
	 * @param value
	 * 			The referenced value; might be null.
	 * @return A new {@link ReferencedValue}, never null
	 */
	public static <R, V> ReferencedValue<R, V> of(R reference, V value) {
		if (reference == null) {
			throw new IllegalArgumentException("Cannot create a referenced value from a null reference");
		}
		return new ReferencedValue<>(reference, value);
	}
}
