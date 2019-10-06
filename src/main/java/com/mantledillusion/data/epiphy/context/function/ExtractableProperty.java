package com.mantledillusion.data.epiphy.context.function;

import com.mantledillusion.data.epiphy.context.Context;
import com.mantledillusion.data.epiphy.context.reference.PropertyReference;
import com.mantledillusion.data.epiphy.exception.InterruptedPropertyPathException;
import com.mantledillusion.data.epiphy.exception.OutboundPropertyPathException;
import com.mantledillusion.data.epiphy.exception.UnreferencedPropertyPathException;
import com.mantledillusion.data.epiphy.Property;

/**
 * Interface for properties that carry elements which can be removed using the element's reference.
 *
 * @param <O>
 *            The root parent object type of this {@link Property}.
 * @param <E>
 *            The type of the property element this {@link ExtractableProperty} represents.
 * @param <R>
 *            The type of the context reference.
 */
public interface ExtractableProperty<O, E, R> {

	/**
	 * Removes an element from the batch that is represented by this {@link ExtractableProperty}.
	 * <p>
	 * The element is removed by its reference.
	 * <p>
	 * Note that this is a writing operation, so the property has to {@link Property#exists(Object)} in the object.
	 *
	 * @param object
	 * 			The object to remove the element from; might <b>not</b> be null.
	 * @param reference
	 * 			The reference to extract the element with; might <b>not</b> be null.
	 * @return
	 * 			The element that has been removed; might be null if the element at the reference was null
	 * @throws InterruptedPropertyPathException
	 * 			If any property on the path to this {@link ExtractableProperty} is null.
	 * @throws UnreferencedPropertyPathException
	 *          If there is any uncontexted property in this {@link ExtractableProperty}'s path.
	 */
	default E extract(O object, R reference) throws
			InterruptedPropertyPathException, UnreferencedPropertyPathException {
		return extract(object, reference, null);
	}

	/**
	 * Removes an element from the batch that is represented by this {@link ExtractableProperty}.
	 * <p>
	 * The element is removed by its reference.
	 * <p>
	 * Note that this is a writing operation, so the property has to {@link Property#exists(Object)} in the object.
	 *
	 * @param object
	 * 			The object to remove the element from; might <b>not</b> be null.
	 * @param reference
	 * 			The reference to extract the element with; might <b>not</b> be null.
	 * @param context
	 * 			The {@link Context} that should be used to satisfy the contexted properties from the root property to
	 * 			this {@link ExtractableProperty}; might be null.
	 * @return
	 * 			The element that has been removed; might be null if the element at the reference was null
	 * @throws InterruptedPropertyPathException
	 * 			If any property on the path to this {@link ExtractableProperty} is null.
	 * @throws UnreferencedPropertyPathException
	 * 			If there is any uncontexted property in this {@link ExtractableProperty}'s path that does not have a
	 * 			{@link PropertyReference} included in the given {@link Context}.
	 * @throws OutboundPropertyPathException
	 * 			If the property reference provided by the given {@link Context} is out of bounds on the given object's
	 * 			elements this {@link ExtractableProperty} represents.
	 */
	E extract(O object, R reference, Context context) throws
			InterruptedPropertyPathException, UnreferencedPropertyPathException, OutboundPropertyPathException;
}
