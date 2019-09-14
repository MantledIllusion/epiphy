package com.mantledillusion.data.epiphy.context;

import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.Map;

import com.mantledillusion.data.epiphy.Property;
import com.mantledillusion.data.epiphy.context.reference.PropertyReference;

/**
 * A pool {@link PropertyReference}s that describe a {@link Context} in which {@link Property}s should be evaluated.
 * <p>
 * {@link Context} are basically a {@link Map} of {@link Property}-&gt;{@link PropertyReference} pairs, represented by
 * implementations of {@link PropertyReference}.
 * <p>
 * Note that two {@link Property}s are only considered equal when they are the exact same {@link Object} instance.
 */
public class Context {

	/**
	 * An empty {@link Context} instance without any references.
	 */
	public static final Context EMPTY = new Context();

	private final Map<Property<?, ?>, PropertyReference<?, ?>> keys;

	private Context() {
		this.keys = new IdentityHashMap<>();
	}

	/**
	 * Returns whether this {@link Context} contains a reference for the given {@link Property}.
	 *
	 * @param property The {@link Property}; might be null.
	 * @return True if there is a reference, false otherwise
	 */
	public boolean containsReference(Property<?, ?> property) {
		return this.keys.containsKey(property);
	}

	/**
	 * Returns whether this {@link Context} contains a reference for the given {@link Property}.
	 *
	 * @param <R> The {@link PropertyReference} implementation type.
	 * @param property The {@link Property}; might be null.
	 * @param referenceType The {@link PropertyReference} implementation {@link Class}; might <b>not</b> be null.
	 * @return True if there is a reference, false otherwise
	 */
	public <R extends PropertyReference<?, ?>> boolean containsReference(Property<?, ?> property, Class<R> referenceType) {
		if (referenceType == null) {
			throw new IllegalArgumentException("Cannot check the reference's type using a null type");
		}
		return this.keys.containsKey(property) && referenceType.isAssignableFrom(this.keys.get(property).getClass());
	}

	/**
	 * Returns The {@link PropertyReference} contained by this {@link Context} for the given {@link Property}.
	 *
	 * @param <R> The {@link PropertyReference} implementation type.
	 * @param property The {@link Property}; might be null.
	 * @return The reference, might be null
	 */
	public <R extends PropertyReference<?, ?>> R getReference(Property<?, ?> property) {
		return (R) this.keys.get(property);
	}

	/**
	 * Returns The {@link PropertyReference} contained by this {@link Context} for the given {@link Property}.
	 *
	 * @param <R> The {@link PropertyReference} implementation type.
	 * @param property The {@link Property}; might be null.
	 * @param referenceType The type of the {@link PropertyReference} to get; might <b>not</b> be null.
	 * @return The reference, might be null
	 */
	public <R extends PropertyReference<?, ?>> R getReference(Property<?, ?> property, Class<R> referenceType) {
		if (referenceType == null) {
			throw new IllegalArgumentException("Cannot check the reference's type using a null type");
		}
		return referenceType.cast(this.keys.get(property));
	}

	public Iterator<? extends PropertyReference<?, ?>> iterator() {
		return this.keys.values().iterator();
	}

	public Context union(PropertyReference<?, ?>... references) {
		return union(Context.of(references));
	}

	public Context union(Context other) {
		Context context = new Context();
		context.keys.putAll(this.keys);
		context.keys.putAll(other.keys);
		return context;
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
		Context other = (Context) obj;
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
	 * Creates a new {@link Context} using the given indices.
	 *
	 * @param references
	 *            The {@link PropertyReference}s to create a new context from; might be null or contain null values.
	 * @return A new {@link Context} of the given references, never null
	 */
	@SafeVarargs
	public static Context of(PropertyReference<?, ?>... references) {
		if (references == null || references.length == 0) {
			return EMPTY;
		} else {
			Context context = new Context();
			for (PropertyReference<?, ?> key : references) {
				if (key != null) {
					context.keys.put(key.getProperty(), key);
				}
			}
			return context;
		}
	}
}
