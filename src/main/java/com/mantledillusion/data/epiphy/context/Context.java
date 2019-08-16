package com.mantledillusion.data.epiphy.context;

import java.util.Map;

import com.mantledillusion.data.epiphy.Property;
import com.mantledillusion.data.epiphy.context.function.ExtractableProperties;
import com.mantledillusion.data.epiphy.context.reference.PropertyReference;

/**
 * Interface for {@link PropertyReference} contexts.
 * <p>
 * Contexts of {@link PropertyReference}s would basically contain a {@link Map} of
 * {@link ExtractableProperties}-&gt;{@link PropertyReference} pairs, where the key's
 * type is the one of the respective {@link ExtractableProperties}.
 * <p>
 * Note that {@link ExtractableProperties}s (like all properties) only equal when
 * they are the same instance.
 * <p>
 * The default implementation of this interface is {@link DefaultContext}.
 */
public interface Context {

	<R extends PropertyReference<?, K>, K> boolean containsReference(Property<?, ?> property, Class<R> referenceType);

	<R extends PropertyReference<?, K>, K> R getReference(Property<?, ?> property, Class<R> referenceType);
}
