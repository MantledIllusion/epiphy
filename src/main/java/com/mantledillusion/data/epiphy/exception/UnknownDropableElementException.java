package com.mantledillusion.data.epiphy.exception;

import com.mantledillusion.data.epiphy.Property;
import com.mantledillusion.data.epiphy.context.function.DropableProperty;

/**
 * Exception that might be thrown when an element cannot be dropped from a {@link DropableProperty} because it is
 * unknown to the batch.
 */
public class UnknownDropableElementException extends IllegalStateException {

	/**
	 * Constructor.
	 *
	 * @param <E>
	 * 			The element type of the batch.
	 * @param property
	 * 			The {@link Property} the element to drop is unknown to; might <b>not</b> be null.
	 * @param element
	 * 			The element value that was unknown; might <b>not</b> be null.
	 */
	public <E> UnknownDropableElementException(DropableProperty<?, ?, E, ?> property, E element) {
		super("Unable to drop the element value '" + element + "' from the property '" + property +
				"'; the element to drop is unknown to the batch.");
	}
}
