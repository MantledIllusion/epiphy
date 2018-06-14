package com.mantledillusion.data.epiphy.exception;

import com.mantledillusion.data.epiphy.context.PropertyIndex;
import com.mantledillusion.data.epiphy.interfaces.ReadableProperty;

/**
 * Exception that might be thrown if an indexed property occurs on a property path
 * during an execution, but there is no {@link PropertyIndex} provided for it.
 */
public final class UncontextedPropertyPathException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor.
	 * 
	 * @param indexedProperty
	 *            The index property that no {@link PropertyIndex} was provided
	 *            for; might be null.
	 */
	public UncontextedPropertyPathException(ReadableProperty<?, ?> indexedProperty) {
		super("No index given for indexed property '" + indexedProperty + "'");
	}
}
