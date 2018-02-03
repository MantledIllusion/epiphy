package com.mantledillusion.data.epiphy.exception;

import com.mantledillusion.data.epiphy.ModelPropertyList;
import com.mantledillusion.data.epiphy.index.PropertyIndex;

/**
 * Exception that might be thrown if a {@link PropertyIndex} is provided for a
 * {@link ModelPropertyList}, but that index is out of bound for the property
 * list value.
 */
public class OutboundPropertyPathException extends IndexOutOfBoundsException {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor.
	 * 
	 * @param listedProperty
	 *            The {@link ModelPropertyList} for whose property list value the
	 *            index was out of bounds for; might be null
	 * @param index
	 *            The index that was out of bounds
	 * @param length
	 *            The length of the actual property list value
	 */
	public OutboundPropertyPathException(ModelPropertyList<?, ?> listedProperty, int index, int length) {
		super("Index " + index + " is out of bounds (0|" + (length - 1) + ") for listed property " + listedProperty);
	}
}
