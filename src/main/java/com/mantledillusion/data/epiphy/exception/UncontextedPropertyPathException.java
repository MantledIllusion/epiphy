package com.mantledillusion.data.epiphy.exception;

import com.mantledillusion.data.epiphy.context.PropertyReference;
import com.mantledillusion.data.epiphy.interfaces.function.ContextableProperty;

/**
 * Exception that might be thrown if a contexted property occurs on a property
 * path during an execution, but there is no {@link PropertyReference} provided
 * for it.
 */
public final class UncontextedPropertyPathException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor.
	 * 
	 * @param property
	 *            The {@link ContextableProperty} that no {@link PropertyReference}
	 *            was provided for; might <b>not</b> be null.
	 */
	public UncontextedPropertyPathException(ContextableProperty<?, ?, ?> property) {
		super("No reference given for contexted property '" + property + "'");
	}
}
