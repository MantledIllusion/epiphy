package com.mantledillusion.data.epiphy.exception;

import com.mantledillusion.data.epiphy.Property;

/**
 * Exception that might be thrown if a null object occurs when processing on a value.
 */
public class InterruptedPropertyPathException extends NullPointerException {

	/**
	 * Constructor.
	 * 
	 * @param property
	 *            The {@link Property} whose object was null so its value could not be processed; might <b>not</b> be null.
	 */
	public InterruptedPropertyPathException(Property<?, ?> property) {
		super("Unable to process a value of property '" + property + "'; the object to process the value on was null.");
	}
}
