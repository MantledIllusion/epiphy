package com.mantledillusion.data.epiphy.exception;

import com.mantledillusion.data.epiphy.Property;
import com.mantledillusion.data.epiphy.context.function.ExtractableProperty;

/**
 * Exception that might be thrown when an element cannot be extracted from an {@link ExtractableProperty} because its
 * reference is out of bounds for the batch.
 */
public class OutboundExtractableReferenceException extends IllegalStateException {

	/**
	 * Constructor.
	 *
	 * @param property
	 * 			The {@link Property} the reference to extract is out of bounds to; might <b>not</b> be null.
	 * @param reference
	 * 			The reference that is out of bounds; might <b>not</b> be null.
	 */
	public <R> OutboundExtractableReferenceException(ExtractableProperty<?, ?, R> property, R reference) {
		super("Unable to extract the element at reference '" + reference + "' from the property '" + property +
				"'; the reference to extract at is out of bounds for the batch.");
	}
}
