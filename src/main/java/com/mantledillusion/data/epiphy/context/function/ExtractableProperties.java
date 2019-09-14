package com.mantledillusion.data.epiphy.context.function;

import com.mantledillusion.data.epiphy.context.Context;
import com.mantledillusion.data.epiphy.context.reference.PropertyReference;
import com.mantledillusion.data.epiphy.exception.InterruptedPropertyPathException;
import com.mantledillusion.data.epiphy.exception.OutboundPropertyPathException;
import com.mantledillusion.data.epiphy.exception.UnreferencedPropertyPathException;
import com.mantledillusion.data.epiphy.Property;

/**
 * Interface for properties that carry elements which can be reached by using a
 * {@link Context}.
 *
 * @param <C>
 *            The root model type of this {@link ExtractableProperties}'s property
 *            tree.
 * @param <E>
 *            The type of the property element this {@link ExtractableProperties}
 *            represents.
 * @param <R>
 *            The type of the context reference.
 */
public interface ExtractableProperties<C, E, R> {

	/**
	 * Removes the element from its elements that is represented by this
	 * {@link ExtractableProperties} in the given model.
	 * <p>
	 * The {@link PropertyReference} of this {@link ExtractableProperties} in the
	 * given context is used to determine where to remove the given element.
	 * <p>
	 * Note that this is a writing operation, so the property has to
	 * {@link Property#exists(Object, Context)} in the model.
	 * 
	 * @param collection
	 *            The model to remove the element from; might <b>not</b> be null.
	 * @param reference The reference to extract the element with; might <b>not</b> be null.
	 * @param context
	 *            The {@link Context} that should be used to satisfy the contexted
	 *            properties from the root property to this
	 *            {@link ExtractableProperties}; might <b>not</b> be null.
	 * @return The element that has been removed; might be null if the removed
	 *         element was null or the elements were empty, so nothing could be
	 *         removed
	 * @throws InterruptedPropertyPathException
	 *             If any property on the path to this {@link ExtractableProperties}
	 *             is null.
	 * @throws UnreferencedPropertyPathException
	 *             If there is any uncontexted property in this
	 *             {@link ExtractableProperties}'s path that does not have a
	 *             {@link PropertyReference} included in the given {@link Context}.
	 * @throws OutboundPropertyPathException
	 *             If the property reference provided by the given {@link Context}
	 *             is out of bounds on the given model's elements this
	 *             {@link ExtractableProperties} represents.
	 */
	E extract(C collection, R reference, Context context);
}
