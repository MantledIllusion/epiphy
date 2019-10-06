package com.mantledillusion.data.epiphy.exception;

import com.mantledillusion.data.epiphy.Property;
import com.mantledillusion.data.epiphy.context.function.InsertableProperty;

/**
 * Exception that might be thrown when an element cannot be inserted into an {@link InsertableProperty} because its
 * reference is out of bounds for the batch.
 */
public class OutboundInsertableReferenceException extends IllegalStateException {

	/**
	 * Constructor.
	 *
	 * @param property
	 * 			The {@link Property} the reference to insert is out of bounds to; might <b>not</b> be null.
	 * @param reference
	 * 			The reference that is out of bounds; might <b>not</b> be null.
	 */
	public <R> OutboundInsertableReferenceException(InsertableProperty<?, ?, R> property, R reference) {
		super("Unable to insert the element at reference '" + reference + "' into the property '" + property +
				"'; the reference to insert at is out of bounds for the batch.");
	}
}
