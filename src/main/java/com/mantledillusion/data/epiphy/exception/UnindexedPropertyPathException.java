package com.mantledillusion.data.epiphy.exception;

import com.mantledillusion.data.epiphy.index.PropertyIndex;
import com.mantledillusion.data.epiphy.interfaces.Property;

/**
 * Exception that might be thrown if a listed property occurs on a property path
 * during an execution, but there is no {@link PropertyIndex} provided for it.
 */
public final class UnindexedPropertyPathException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor.
	 * 
	 * @param listedProperty
	 *            The listed property that no {@link PropertyIndex} was provided
	 *            for; might be null.
	 */
	public UnindexedPropertyPathException(Property<?, ?> listedProperty) {
		super("No index given for listed property '" + listedProperty + "'");
	}
}
