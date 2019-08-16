package com.mantledillusion.data.epiphy.context;

import java.util.HashMap;
import java.util.Map;

import com.mantledillusion.data.epiphy.Property;
import com.mantledillusion.data.epiphy.context.reference.PropertyReference;

/**
 * Default implementation of {@link Context}.
 * <p>
 * {@link DefaultContext}s can be created using the
 * {@link #of(PropertyReference...)} method.
 */
public class DefaultContext implements Context {

	/**
	 * An empty {@link DefaultContext} instance without any indices.
	 */
	public static final DefaultContext EMPTY = new DefaultContext();

	private final Map<Property<?, ?>, Map<Class<? extends PropertyReference>, PropertyReference<?, ?>>> keys;

	private DefaultContext() {
		this.keys = new HashMap<>();
	}

	@Override
	public <R extends PropertyReference<?, K>, K> boolean containsReference(Property<?, ?> property, Class<R> referenceType) {
		return this.keys.containsKey(property) && this.keys.get(property).containsKey(referenceType);
	}

	@Override
	public <R extends PropertyReference<?, K>, K> R getReference(Property<?, ?> property, Class<R> referenceType) {
		return containsReference(property, referenceType) ? (R) this.keys.get(property).get(referenceType) : null;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((keys == null) ? 0 : keys.hashCode());
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
		DefaultContext other = (DefaultContext) obj;
		if (keys == null) {
			if (other.keys != null)
				return false;
		} else if (!keys.equals(other.keys))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "DefaultContext [propertyKeys=" + keys + "]";
	}

	/**
	 * Creates a new {@link DefaultContext} using the given indices.
	 * 
	 * @param keys
	 *            The property keys to create a new context from; might be null or
	 *            contain null values.
	 * @return A new {@link DefaultContext} of the given keys; never null
	 */
	@SafeVarargs
	public static DefaultContext of(PropertyReference<?, ?>... keys) {
		if (keys == null || keys.length == 0) {
			return EMPTY;
		} else {
			DefaultContext context = new DefaultContext();
			for (PropertyReference<?, ?> key : keys) {
				if (key != null) {
					context.keys.computeIfAbsent(key.getProperty(), p -> new HashMap<>()).put(key.getClass(), key);
				}
			}
			return context;
		}
	}
}