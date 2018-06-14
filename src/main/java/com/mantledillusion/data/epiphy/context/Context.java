package com.mantledillusion.data.epiphy.context;

import java.util.Map;

import com.mantledillusion.data.epiphy.context.impl.DefaultContext;
import com.mantledillusion.data.epiphy.interfaces.function.ContextableProperty;

/**
 * Interface for {@link PropertyKey} contexts.
 * <p>
 * Contexts of {@link PropertyKey}s would basically contain a {@link Map} of
 * {@link ContextableProperty}-&gt;{@link PropertyKey} pairs, where the key's
 * type is the one of the respective {@link ContextableProperty}.
 * <p>
 * Note that {@link ContextableProperty}s (like all properties) only equal when
 * they are the same instance.
 * <p>
 * The default implementation of this interface is {@link DefaultContext}.
 */
public interface Context {

	/**
	 * Returns whether there is a key for the given property.
	 * 
	 * @param <K>
	 *            The key type of the given {@link ContextableProperty}.
	 * @param property
	 *            The property whose key is searched for; might be null, although
	 *            this will cause the method always to return false.
	 * @return True if there is a key existing, false otherwise.
	 */
	public <K> boolean containsKey(ContextableProperty<?, ?, K, ?> property);

	/**
	 * Returns the key of the given property.
	 * 
	 * @param <K>
	 *            The key type of the given {@link ContextableProperty}.
	 * @param property
	 *            The property whose key is searched for; might be null, although
	 *            this will cause the method always to return null.
	 * @return The key if this {@link Context} contains one for the given property,
	 *         null otherwise
	 */
	public <K> K getKey(ContextableProperty<?, ?, K, ?> property);
}
