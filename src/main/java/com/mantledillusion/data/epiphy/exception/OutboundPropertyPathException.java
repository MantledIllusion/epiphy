package com.mantledillusion.data.epiphy.exception;

import com.mantledillusion.data.epiphy.context.PropertyReference;
import com.mantledillusion.data.epiphy.interfaces.ReadableProperty;
import com.mantledillusion.data.epiphy.interfaces.function.ContextableProperty;

/**
 * Exception that might be thrown if a {@link PropertyReference} is provided for
 * a {@link ReadableProperty}, but its reference is out of bound for the
 * property list value.
 */
public class OutboundPropertyPathException extends IndexOutOfBoundsException {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor.
	 * 
	 * @param <R>
	 *            The reference type.
	 * @param property
	 *            The {@link ContextableProperty} whose reference was out of bounds;
	 *            might <b>not</b> be null.
	 * @param reference
	 *            The reference that was out of bounds; might <b>not</b> be null.
	 */
	public <R> OutboundPropertyPathException(ContextableProperty<?, ?, R> property, R reference) {
		super("The property reference " + reference + " is out of bounds for the property " + property);
	}
}
