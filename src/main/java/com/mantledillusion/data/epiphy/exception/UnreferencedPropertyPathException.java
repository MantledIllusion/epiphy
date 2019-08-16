package com.mantledillusion.data.epiphy.exception;

import com.mantledillusion.data.epiphy.Property;
import com.mantledillusion.data.epiphy.context.reference.PropertyReference;
import com.mantledillusion.data.epiphy.context.function.ExtractableProperties;

/**
 * Exception that might be thrown if a referenced property occurs on a property path during an execution, but there is
 * no {@link PropertyReference} provided for it.
 */
public final class UnreferencedPropertyPathException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor.
	 * 
	 * @param property
	 *            The {@link ExtractableProperties} that no {@link PropertyReference} was provided for; might
	 *            <b>not</b> be null.
	 */
	public UnreferencedPropertyPathException(Property<?, ?> property) {
		super("No reference given for referenced property '" + property + "'");
	}
}
