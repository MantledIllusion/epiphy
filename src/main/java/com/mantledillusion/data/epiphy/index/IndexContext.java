package com.mantledillusion.data.epiphy.index;

import java.util.Map;

import com.mantledillusion.data.epiphy.index.impl.DefaultIndexContext;
import com.mantledillusion.data.epiphy.interfaces.ListedProperty;
import com.mantledillusion.data.epiphy.interfaces.ReadableProperty;

/**
 * Interface for index contexts, which are basically {@link Map}s of
 * {@link ListedProperty}-&gt;{@link Integer} pairs determining the indices of
 * {@link ListedProperty}s.
 * <p>
 * The default implementation of this interface is {@link DefaultIndexContext}.
 */
public interface IndexContext {

	/**
	 * Retrieves the index for the given listed property.
	 * <p>
	 * Note that the parameter for retrieving is {@link ReadableProperty}, not
	 * {@link ListedProperty} for convenience reasons; an implementation of
	 * {@link IndexContext} should never return anything but null when
	 * listedProperty.getClass() instanceof {@link ListedProperty} = false.
	 * 
	 * @param listedProperty
	 *            The listed property to retrieve the index for; might be null.
	 * @return The index of the listed property; might be null, if the given
	 *         property is not indexed
	 */
	public Integer indexOf(ReadableProperty<?, ?> listedProperty);

	/**
	 * Checks if this {@link DefaultIndexContext} contains an index for the given
	 * {@link ReadableProperty}.
	 * <p>
	 * Note that the parameter for checking is {@link ReadableProperty}, not
	 * {@link ListedProperty} for convenience reasons; an implementation of
	 * {@link IndexContext} should never return anything but false when
	 * listedProperty.getClass() instanceof {@link ListedProperty} = false.
	 * 
	 * @param listedProperty
	 *            The listed property to check index existence for; might be null.
	 * @return True if there is an index for the given listed property; false
	 *         otherwise
	 */
	public boolean contains(ReadableProperty<?, ?> listedProperty);
}
