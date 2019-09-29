package com.mantledillusion.data.epiphy.context.function;

import com.mantledillusion.data.epiphy.context.Context;
import com.mantledillusion.data.epiphy.context.reference.PropertyReference;
import com.mantledillusion.data.epiphy.exception.InterruptedPropertyPathException;
import com.mantledillusion.data.epiphy.exception.OutboundPropertyPathException;
import com.mantledillusion.data.epiphy.exception.UnreferencedPropertyPathException;
import com.mantledillusion.data.epiphy.Property;

/**
 * Interface for properties that carry elements whose references have a natural order, so new elements can be added
 * without explicitly providing a reference.
 *
 * @param <O>
 *            The root parent object type of this {@link Property}.
 * @param <E>
 *            The type of the property element this {@link IncludableProperties} represents.
 * @param <R>
 *            The type of the context reference.
 */
public interface IncludableProperties<O, E, R> {

	/**
	 * Adds an element to the batch that is represented by this {@link IncludableProperties}.
	 * <p>
	 * The element is added at the next natural reference.
	 * <p>
	 * Note that this is a writing operation, so the property has to {@link Property#exists(Object)} in the object.
	 *
	 * @param object
	 * 			The object to add the element to; might <b>not</b> be null.
	 * @param element
	 * 			The element to add; might be null.
	 * @return
	 * 			The property reference of the element that has been removed; never null
	 * @throws InterruptedPropertyPathException
	 * 			If any property on the path to this {@link IncludableProperties} is null.
	 * @throws UnreferencedPropertyPathException
	 *          If there is any uncontexted property in this {@link IncludableProperties}'s path.
	 */
	default R include(O object, E element) throws
			InterruptedPropertyPathException, UnreferencedPropertyPathException {
		return include(object, element, null);
	}

	/**
	 * Adds an element to the batch that is represented by this {@link IncludableProperties}.
	 * <p>
	 * The element is added at the next natural reference.
	 * <p>
	 * Note that this is a writing operation, so the property has to {@link Property#exists(Object)} in the object.
	 *
	 * @param object
	 * 			The object to add the element to; might <b>not</b> be null.
	 * @param element
	 * 			The element to add; might be null.
	 * @param context
	 * 			The {@link Context} that should be used to satisfy the contexted properties from the root property to
	 * 			this {@link IncludableProperties}; might be null.
	 * @return
	 * 			The property reference of the element that has been removed; never null
	 * @throws InterruptedPropertyPathException
	 * 			If any property on the path to this {@link IncludableProperties} is null.
	 * @throws UnreferencedPropertyPathException
	 * 			If there is any uncontexted property in this {@link IncludableProperties}'s path that does not have a
	 * 			{@link PropertyReference} included in the given {@link Context}.
	 * @throws OutboundPropertyPathException
	 * 			If the property reference provided by the given {@link Context} is out of bounds on the given object's
	 * 			elements this {@link IncludableProperties} represents.
	 */
	R include(O object, E element, Context context) throws
			InterruptedPropertyPathException, UnreferencedPropertyPathException, OutboundPropertyPathException;
}
