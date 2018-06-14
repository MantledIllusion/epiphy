package com.mantledillusion.data.epiphy.context;

import com.mantledillusion.data.epiphy.interfaces.function.ContextableProperty;
import com.mantledillusion.data.epiphy.interfaces.type.ListedProperty;
import com.mantledillusion.data.epiphy.interfaces.type.NodedProperty;

/**
 * Base type for keys of {@link ContextableProperty} implementations.
 * <p>
 * Implementations are:<br>
 * - {@link PropertyIndex} for {@link ListedProperty}s<br>
 * - {@link PropertyRoute} for {@link NodedProperty}s<br>
 *
 * @param <P>
 *            The property type, which is the {@link ContextableProperty}
 *            implementation this key is meant for.
 * @param <K>
 *            The key type, which is the type the {@link ContextableProperty}
 *            implementation requires as key.
 */
public abstract class PropertyKey<P extends ContextableProperty<?, ?, K, ?>, K> {

	private final P property;
	private final K key;

	PropertyKey(P property, K key) {
		this.property = property;
		this.key = key;
	}

	/**
	 * Returns the property of this {@link PropertyKey}.
	 * 
	 * @return The property; never null
	 */
	public P getProperty() {
		return this.property;
	}

	/**
	 * Returns the key of this {@link PropertyKey}.
	 * 
	 * @return The key, never null
	 */
	public K getKey() {
		return this.key;
	}

	@Override
	public abstract int hashCode();

	@Override
	public abstract boolean equals(Object obj);

	@Override
	public abstract String toString();
}
