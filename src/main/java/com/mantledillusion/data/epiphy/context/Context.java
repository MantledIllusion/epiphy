package com.mantledillusion.data.epiphy.context;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.Stream;

import com.mantledillusion.data.epiphy.Property;
import com.mantledillusion.data.epiphy.context.reference.PropertyReference;

/**
 * An immutable pool of {@link PropertyReference}s that describe the {@link Context} in which {@link Property}s should
 * be evaluated.
 * <p>
 * {@link Context} are basically a {@link Map} of {@link Property}-&gt;{@link PropertyReference} pairs, represented by
 * implementations of {@link PropertyReference}.
 * <p>
 * Note that two {@link Property}s are only considered equal when they are the exact same {@link Object} instance; as a
 * result, the {@link Property}s in {@link Context}s are used by their {@link Object} identity.
 */
public class Context {

	/**
	 * An empty {@link Context} instance without any references.
	 */
	public static final Context EMPTY = new Context();

	private final Map<Property<?, ?>, PropertyReference<?, ?>> keys;

	private Context() {
		this.keys = new HashMap<>();
	}

	/**
	 * Returns whether this {@link Context} contains a reference for the given {@link Property}.
	 *
	 * @param property
	 * 			The {@link Property}; might be null.
	 * @return True if there is a reference, false otherwise
	 */
	public boolean containsReference(Property<?, ?> property) {
		return this.keys.containsKey(property);
	}

	/**
	 * Returns whether this {@link Context} contains a reference for the given {@link Property} that is an instance of
	 * the given {@link PropertyReference} implementation {@link Class} type.
	 *
	 * @param <R>
	 * 			The {@link PropertyReference} implementation type.
	 * @param property
	 * 			The {@link Property}; might be null.
	 * @param referenceType
	 * 			The {@link PropertyReference} implementation {@link Class}; might <b>not</b> be null.
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
	 * @param <R>
	 * 			The {@link PropertyReference} implementation type.
	 * @param property
	 * 			The {@link Property}; might be null.
	 * @return The reference, might be null
	 */
	public <R extends PropertyReference<?, ?>> R getReference(Property<?, ?> property) {
		return (R) this.keys.get(property);
	}

	/**
	 * Returns The {@link PropertyReference} contained by this {@link Context} for the given {@link Property}.
	 *
	 * @param <R>
	 * 			The {@link PropertyReference} implementation type.
	 * @param property
	 * 			The {@link Property}; might be null.
	 * @param referenceType
	 * 			The type of the {@link PropertyReference} to get; might <b>not</b> be null.
	 * @return The reference, might be null
	 */
	public <R extends PropertyReference<?, ?>> R getReference(Property<?, ?> property, Class<R> referenceType) {
		if (referenceType == null) {
			throw new IllegalArgumentException("Cannot check the reference's type using a null type");
		}
		return referenceType.cast(this.keys.get(property));
	}

	/**
	 * Returns a new {@link Stream} for all {@link PropertyReference}s stored by this {@link Context}.
	 *
	 * @return A new {@link Stream} instance, never null
	 */
	public Stream<? extends PropertyReference<?, ?>> stream() {
		return this.keys.values().stream();
	}

	/**
	 * Returns a new {@link Iterator} for all {@link PropertyReference}s stored by this {@link Context}.
	 *
	 * @return A new {@link Iterator} instance, never null
	 */
	public Iterator<? extends PropertyReference<?, ?>> iterator() {
		return this.keys.values().iterator();
	}

	public Context union(PropertyReference<?, ?>... references) {
		return union(Context.of(references));
	}

	/**
	 * Returns a new {@link Context} containing the {@link PropertyReference}s of both this {@link Context} as well as
	 * the ones of the given {@link Context}.
	 * <p>
	 * If there is a {@link PropertyReference} in both {@link Context}s for the same {@link Property}, the one of the
	 * given {@link Context} is taken.
	 *
	 * @param other
	 * 			The other {@link Context} to unionize with; might be null
	 * @return A new {@link Context} instance, never null
	 */
	public Context union(Context other) {
		Context context = new Context();
		context.keys.putAll(this.keys);
		if (other != null) {
			context.keys.putAll(other.keys);
		}
		return context;
	}

	/**
	 * Returns the amount of references contained by this {@link Context}.
	 *
	 * @return The amount of references, never &lt;0
	 */
	public int size() {
		return this.keys.size();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + this.keys.hashCode();
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
		return this.keys.equals(((Context) obj).keys);
	}

	@Override
	public String toString() {
		return "Context [propertyKeys=" + keys + "]";
	}

	/**
	 * Creates a new {@link Context} using the given {@link PropertyReference}s.
	 *
	 * @param references
	 * 			The {@link PropertyReference}s to create a new context from; might be null, empty or contain null values.
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

	/**
	 * Defaults to returning an {@link #EMPTY} {@link Context} if the given one is null.
	 *
	 * @param context
	 * 			The {@link Context} to check; might be null.
	 * @return
	 * 			Either the given {@link Context} if it is not null, {@link #EMPTY} otherwise
	 */
	public static Context defaultIfNull(Context context) {
		return context == null ? EMPTY : context;
	}
}
