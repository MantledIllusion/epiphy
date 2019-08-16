package com.mantledillusion.data.epiphy.exception;

import com.mantledillusion.data.epiphy.Property;

/**
 * Exception that might be thrown if a writing operation is executed on a property that has no
 * {@link com.mantledillusion.data.epiphy.io.Setter} provided for it, so it is read only.
 */
public class ReadonlyPropertyException extends NullPointerException {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor.
	 *
	 * @param property
	 *            The property that is read only.
	 */
	public ReadonlyPropertyException(Property<?, ?> property) {
		super("Unable to perform writing operation on property '" + property + "'; the property has no setter provided.");
	}
}
