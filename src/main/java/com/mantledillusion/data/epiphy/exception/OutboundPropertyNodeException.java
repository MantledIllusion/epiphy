package com.mantledillusion.data.epiphy.exception;

import com.mantledillusion.data.epiphy.context.PropertyIndex;
import com.mantledillusion.data.epiphy.interfaces.type.NodedProperty;

/**
 * Exception that might be thrown if a {@link PropertyIndex} is provided for a
 * {@link NodedProperty}, but one index is out of bound for the respective
 * property leaf list.
 */
public class OutboundPropertyNodeException extends IndexOutOfBoundsException {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor.
	 * 
	 * @param nodedProperty
	 *            The {@link NodedProperty} whose index was out of bounds for; might
	 *            be null
	 * @param index
	 *            The index that was out of bounds
	 * @param length
	 *            The length of the actual property index value
	 */
	public OutboundPropertyNodeException(NodedProperty<?, ?> nodedProperty, int index, int length) {
		super("Index " + index + " is out of bounds (0|" + (length - 1) + ") for indexed property " + nodedProperty);
	}
}
