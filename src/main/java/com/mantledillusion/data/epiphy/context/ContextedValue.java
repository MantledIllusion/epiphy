package com.mantledillusion.data.epiphy.context;

/**
 * Defines a value that is contexted by a property key.
 * 
 * @param <K>
 *            The reference type of the {@link ContextedValue}.
 * @param <V>
 *            The value type of the {@link ContextedValue}.
 */
public final class ContextedValue<K, V> {

	private final K propertyKey;
	private final V value;

	private ContextedValue(K propertyKey, V value) {
		this.propertyKey = propertyKey;
		this.value = value;
	}

	/**
	 * Returns the property key that defines the context of the value.
	 * 
	 * @return The property key, never null
	 */
	public K getPropertyKey() {
		return propertyKey;
	}

	/**
	 * Returns the contexted value.
	 * 
	 * @return The contexted value, never null
	 */
	public V getValue() {
		return value;
	}

	/**
	 * Creates a new {@link ContextedValue}.
	 * 
	 * @param <K>
	 *            The reference type of the {@link ContextedValue}.
	 * @param <V>
	 *            The value type of the {@link ContextedValue}.
	 * @param propertyKey
	 *            The property key to create the value for; might <b>not</b> be
	 *            null.
	 * @param value
	 *            The contexted value; might be null.
	 * @return A new {@link ContextedValue}, never null
	 */
	public static <K, V> ContextedValue<K, V> of(K propertyKey, V value) {
		if (propertyKey == null) {
			throw new IllegalArgumentException("Cannot create a contexted value with a null property key");
		}
		return new ContextedValue<K, V>(propertyKey, value);
	}
}
