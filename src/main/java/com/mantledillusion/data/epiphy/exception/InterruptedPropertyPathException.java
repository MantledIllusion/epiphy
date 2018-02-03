package com.mantledillusion.data.epiphy.exception;

import com.mantledillusion.data.epiphy.ModelProperty;

/**
 * Exception that might be thrown if a null property value occurs on a property
 * path during an execution.
 */
public class InterruptedPropertyPathException extends NullPointerException {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor.
	 * 
	 * @param parentProperty
	 *            The parent property whose property value was null so no child
	 *            property value could be determined; might be null
	 */
	public InterruptedPropertyPathException(ModelProperty<?, ?> parentProperty) {
		super("Unable to retrieve a child from parent property '" + parentProperty
				+ "'; the value representing the parent was null.");
	}
}
