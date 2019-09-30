package com.mantledillusion.data.epiphy.exception;

import com.mantledillusion.data.epiphy.Property;

/**
 * Exception that might be thrown if a writing operation is executed on a {@link Property} that returns
 * {@link Property#isWritable()} = false because it does not have a {@link com.mantledillusion.data.epiphy.io.Setter}.
 */
public class ReadonlyPropertyException extends NullPointerException {

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
