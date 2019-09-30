package com.mantledillusion.data.epiphy.exception;

import com.mantledillusion.data.epiphy.Property;

/**
 * Exception that might be thrown if a referenced {@link Property} occurs during an processing, but there is no
 * {@link com.mantledillusion.data.epiphy.context.reference.PropertyReference} provided for it.
 */
public final class UnreferencedPropertyPathException extends RuntimeException {

	/**
	 * Constructor.
	 * 
	 * @param property
	 *            The {@link Property} no reference was provided for; might <b>not</b> be null.
	 */
	public UnreferencedPropertyPathException(Property<?, ?> property) {
		super("No reference provided for referenced property '" + property + "'");
	}
}
