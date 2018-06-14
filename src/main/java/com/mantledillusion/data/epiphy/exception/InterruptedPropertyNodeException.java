package com.mantledillusion.data.epiphy.exception;

import com.mantledillusion.data.epiphy.interfaces.type.NodedProperty;

/**
 * Exception that might be thrown if a null property value occurs on a property
 * node path during an execution.
 */
public class InterruptedPropertyNodeException extends NullPointerException {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor.
	 * 
	 * @param nodedProperty
	 *            The property whose intermediate property value was null so no leaf
	 *            property value could be determined; might be null
	 */
	public InterruptedPropertyNodeException(NodedProperty<?, ?> nodedProperty) {
		super("Unable to retrieve a leaf of property '" + nodedProperty
				+ "'; the value representing one of the intermediate branches was null.");
	}
}
