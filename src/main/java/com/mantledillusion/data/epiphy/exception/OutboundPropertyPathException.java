package com.mantledillusion.data.epiphy.exception;

import com.mantledillusion.data.epiphy.context.PropertyIndex;
import com.mantledillusion.data.epiphy.interfaces.ReadableProperty;

/**
 * Exception that might be thrown if a {@link PropertyIndex} is provided for a
 * {@link ReadableProperty}, but that index is out of bound for the property
 * list value.
 */
public class OutboundPropertyPathException extends IndexOutOfBoundsException {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor.
	 * 
	 * @param indexedProperty
	 *            The {@link ReadableProperty} whose index was out of bounds for;
	 *            might be null
	 * @param index
	 *            The index that was out of bounds
	 * @param length
	 *            The length of the actual property index value
	 */
	public OutboundPropertyPathException(ReadableProperty<?, ?> indexedProperty, int index, int length) {
		super("Index " + index + " is out of bounds (0|" + (length - 1) + ") for indexed property " + indexedProperty);
	}
}
