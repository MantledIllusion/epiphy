package com.mantledillusion.data.epiphy.exception;

import com.mantledillusion.data.epiphy.context.reference.PropertyReference;
import com.mantledillusion.data.epiphy.Property;

/**
 * Exception that might be thrown if a {@link PropertyReference} is provided for a {@link Property}, but its reference
 * is out of bounds for that {@link Property}'s object.
 */
public class OutboundPropertyPathException extends IndexOutOfBoundsException {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor.
	 *
	 * @param property
	 *            The {@link Property} whose reference was out of bounds; might <b>not</b> be null.
	 * @param reference
	 *            The {@link PropertyReference} whose reference was out of bounds; might <b>not</b> be null.
	 */
	public OutboundPropertyPathException(Property<?, ?> property, PropertyReference<?, ?> reference) {
		super("The property reference " + reference + " is out of bounds for the property " + property);
	}
}
