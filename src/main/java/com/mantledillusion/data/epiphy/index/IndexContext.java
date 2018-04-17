package com.mantledillusion.data.epiphy.index;

import java.util.Map;

import com.mantledillusion.data.epiphy.ModelPropertyList;
import com.mantledillusion.data.epiphy.index.impl.DefaultIndexContext;
import com.mantledillusion.data.epiphy.interfaces.Property;

/**
 * Interface for index contexts, which are basically {@link Map}s of
 * {@link ModelPropertyList}-&gt;{@link Integer} pairs determining the indices
 * of {@link ModelPropertyList}s.
 * <p>
 * The default implementation of this interface is {@link DefaultIndexContext}.
 */
public interface IndexContext {

	/**
	 * Retrieves the index for the given listed property.
	 * <p>
	 * Note that the parameter for retrieving is {@link Property}, not
	 * {@link ModelPropertyList} for convenience reasons; an implementation of
	 * {@link IndexContext} should never return anything but null when
	 * listedProperty.getClass() instanceof {@link ModelPropertyList} = false.
	 * 
	 * @param listedProperty
	 *            The listed property to retrieve the index for; might be null.
	 * @return The index of the listed property; might be null, if the given
	 *         property is not indexed
	 */
	public Integer indexOf(Property<?, ?> listedProperty);

	/**
	 * Checks if this {@link DefaultIndexContext} contains an index for the given
	 * {@link Property}.
	 * <p>
	 * Note that the parameter for checking is {@link Property}, not
	 * {@link ModelPropertyList} for convenience reasons; an implementation of
	 * {@link IndexContext} should never return anything but false when
	 * listedProperty.getClass() instanceof {@link ModelPropertyList} = false.
	 * 
	 * @param listedProperty
	 *            The listed property to check index existence for; might be null.
	 * @return True if there is an index for the given listed property; false
	 *         otherwise
	 */
	public boolean contains(Property<?, ?> listedProperty);
}
