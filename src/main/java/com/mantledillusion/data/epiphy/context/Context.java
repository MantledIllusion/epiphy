package com.mantledillusion.data.epiphy.context;

import java.util.Map;

import com.mantledillusion.data.epiphy.Property;
import com.mantledillusion.data.epiphy.context.function.ExtractableProperties;
import com.mantledillusion.data.epiphy.context.reference.PropertyReference;

/**
 * Interface for {@link PropertyReference} contexts.
 * <p>
 * Contexts are basically a {@link Map} of {@link Property}-&gt;reference pairs, represented by implementations of
 * {@link PropertyReference}.
 * <p>
 * Note that two {@link Property}s are only equal when they are the exact same {@link Object} instance.
 * <p>
 * {@link DefaultContext} provides a default implementation for this interface.
 */
public interface Context {

	/**
	 * Returns whether this {@link Context} contains a reference for the given {@link Property}.
	 *
	 * @param <R> The {@link PropertyReference} implementation type.
	 * @param property The {@link Property}; might be null.
	 * @param referenceType The {@link PropertyReference} implementation {@link Class}; migth <b>not</b> be null.
	 * @return True if there is a reference, false otherwise
	 */
	<R extends PropertyReference<?, ?>> boolean containsReference(Property<?, ?> property, Class<R> referenceType);

	/**
	 * Returns The {@link PropertyReference} contained by this {@link Context} for the given {@link Property}.
	 *
	 * @param <R> The {@link PropertyReference} implementation type.
	 * @param property The {@link Property}; might be null.
	 * @param referenceType The {@link PropertyReference} implementation {@link Class}; migth <b>not</b> be null.
	 * @return The reference, might be null
	 */
	<R extends PropertyReference<?, ?>> R getReference(Property<?, ?> property, Class<R> referenceType);
}
