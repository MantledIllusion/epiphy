package com.mantledillusion.data.epiphy.exception;

import com.mantledillusion.data.epiphy.Property;

/**
 * Exception that might be thrown if a null property value occurs on a property path during an execution.
 */
public class InterruptedPropertyPathException extends NullPointerException {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor.
	 * 
	 * @param property
	 *            The property whose object was null so the properties' value could not be determined; might be null
	 */
	public InterruptedPropertyPathException(Property<?, ?> property) {
		super("Unable to retrieve a value of property '" + property
				+ "'; the object to retrieve the value from was null.");
	}
}
