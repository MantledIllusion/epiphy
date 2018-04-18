package com.mantledillusion.data.epiphy.index.impl;

import java.lang.reflect.Method;
import java.util.IdentityHashMap;
import java.util.Map;

import com.mantledillusion.data.epiphy.index.IndexContext;
import com.mantledillusion.data.epiphy.index.PropertyIndex;
import com.mantledillusion.data.epiphy.interfaces.ListedProperty;
import com.mantledillusion.data.epiphy.interfaces.ReadableProperty;

/**
 * Default implementation of {@link IndexContext}.
 * <p>
 * Context of {@link PropertyIndex}es that basically contains a {@link Map} of
 * {@link ListedProperty}-&gt;{@link Integer} pairs determining the indices.
 * <p>
 * {@link DefaultIndexContext}s can be created using the {@link #of(PropertyIndex...)}
 * {@link Method}.
 */
public class DefaultIndexContext implements IndexContext {

	/**
	 * An empty {@link DefaultIndexContext} instance without any indices.
	 */
	public static final DefaultIndexContext EMPTY = new DefaultIndexContext();

	private final Map<ListedProperty<?, ?>, Integer> indices;

	private DefaultIndexContext() {
		this(new IdentityHashMap<>());
	}

	private DefaultIndexContext(IdentityHashMap<ListedProperty<?, ?>, Integer> indices) {
		this.indices = indices;
	}

	@Override
	public Integer indexOf(ReadableProperty<?, ?> listedProperty) {
		return this.indices.get(listedProperty);
	}

	@Override
	public boolean contains(ReadableProperty<?, ?> listedProperty) {
		return this.indices.containsKey(listedProperty);
	}

	@Override
	public final int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + this.indices.hashCode();
		return result;
	}

	@Override
	public final boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DefaultIndexContext other = (DefaultIndexContext) obj;
		if (this.indices == null) {
			if (other.indices != null)
				return false;
		} else if (!this.indices.equals(other.indices))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "DefaultIndexContext [indices=" + indices + "]";
	}

	/**
	 * Creates a new {@link DefaultIndexContext} using the given indices.
	 * 
	 * @param indices
	 *            The indices to create a new context from; might be null or contain
	 *            null values.
	 * @return A new {@link DefaultIndexContext} of the given indices; never null
	 */
	public static DefaultIndexContext of(PropertyIndex... indices) {
		if (indices == null || indices.length == 0) {
			return EMPTY;
		} else {
			DefaultIndexContext context = new DefaultIndexContext();
			for (PropertyIndex index : indices) {
				if (index != null) {
					context.indices.put(index.getProperty(), index.getIndex());
				}
			}
			return context;
		}
	}
}