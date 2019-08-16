package com.mantledillusion.data.epiphy.context.function;

import com.mantledillusion.data.epiphy.context.Context;
import com.mantledillusion.data.epiphy.context.reference.PropertyReference;
import com.mantledillusion.data.epiphy.exception.InterruptedPropertyPathException;
import com.mantledillusion.data.epiphy.exception.OutboundPropertyPathException;
import com.mantledillusion.data.epiphy.exception.UnreferencedPropertyPathException;
import com.mantledillusion.data.epiphy.Property;

/**
 * Interface for properties that carry elements which can be reached by using a
 * {@link Context}, but with the extension to {@link ExtractableProperties} that
 * the context property reference has a natural order, so it can be
 * automatically increased and decreased for certain operations.
 *
 * @param <C>
 *            The root model type of this {@link IncludableProperties}'s property
 *            tree.
 * @param <E>
 *            The type of the property element this {@link IncludableProperties}
 *            represents.
 * @param <R>
 *            The type of the context property reference.
 */
public interface IncludableProperties<C, E, R> {

	/**
	 * Adds the given element to the elements represented by this
	 * {@link IncludableProperties} in the given model.
	 * <p>
	 * Instead of using a property reference in the context, this operation uses the
	 * property reference's natural order to add the given element at the end.
	 * <p>
	 * Note that this is a writing operation, so the property has to
	 * {@link Property#exists(Object)} in the model.
	 * 
	 * @param collection
	 *            The model to add the element to; might <b>not</b> be null.
	 * @param element
	 *            The element to add; might be null.
	 * @return The property reference where the element was appended, never null
	 * @throws InterruptedPropertyPathException
	 *             If any property on the path to this {@link IncludableProperties} is
	 *             null.
	 * @throws UnreferencedPropertyPathException
	 *             If there is any uncontexted property in this
	 *             {@link IncludableProperties}'s path.
	 */
	public default R include(C collection, E element) {
		return include(collection, element, null);
	}

	/**
	 * Adds the given element to the elements represented by this
	 * {@link IncludableProperties} in the given model.
	 * <p>
	 * Instead of using a property reference in the context, this operation uses the
	 * property reference's natural order to add the given element at the end.
	 * <p>
	 * Note that this is a writing operation, so the property has to
	 * {@link Property#exists(Object, Context)} in the model.
	 * 
	 * @param collection
	 *            The model to add the element to; might <b>not</b> be null.
	 * @param element
	 *            The element to add; might be null.
	 * @param context
	 *            The {@link Context} that should be used to satisfy the contexted
	 *            properties from the root property to this
	 *            {@link IncludableProperties}; might be null.
	 * @return The property reference where the element was appended, never null
	 * @throws InterruptedPropertyPathException
	 *             If any property on the path to this {@link IncludableProperties} is
	 *             null.
	 * @throws UnreferencedPropertyPathException
	 *             If there is any uncontexted property in this
	 *             {@link IncludableProperties}'s path that does not have a
	 *             {@link PropertyReference} included in the given {@link Context}.
	 * @throws OutboundPropertyPathException
	 *             If the property reference provided by the given {@link Context}
	 *             is out of bounds on the given model's elements this
	 *             {@link IncludableProperties} represents.
	 */
	public R include(C collection, E element, Context context);
}
