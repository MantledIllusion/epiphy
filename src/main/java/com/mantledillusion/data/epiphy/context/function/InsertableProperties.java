package com.mantledillusion.data.epiphy.context.function;

import com.mantledillusion.data.epiphy.Property;
import com.mantledillusion.data.epiphy.context.Context;
import com.mantledillusion.data.epiphy.context.reference.PropertyReference;
import com.mantledillusion.data.epiphy.exception.InterruptedPropertyPathException;
import com.mantledillusion.data.epiphy.exception.OutboundPropertyPathException;
import com.mantledillusion.data.epiphy.exception.UnreferencedPropertyPathException;

/**
 * Interface for properties that carry elements which can be reached by using a
 * {@link Context}.
 *
 * @param <C>
 *            The root model type of this {@link InsertableProperties}'s property
 *            tree.
 * @param <E>
 *            The type of the property element this {@link InsertableProperties}
 *            represents.
 * @param <R>
 *            The type of the context reference.
 */
public interface InsertableProperties<C, E, R> {

	/**
	 * Adds the given element to the elements represented by this
	 * {@link InsertableProperties} in the given model.
	 * <p>
	 * The {@link PropertyReference} of this {@link InsertableProperties} in the
	 * given context is used to determine where to add the given element.
	 * <p>
	 * Note that this is a writing operation, so the property has to
	 * {@link Property#exists(Object, Context)} in the model.
	 * 
	 * @param collection
	 *            The model to add the element to; might <b>not</b> be null.
	 * @param element
	 *            The element to add; might be null.
	 * @param reference
	 * @param context
	 *            The {@link Context} that should be used to satisfy the contexted
	 *            properties from the root property to this
	 *            {@link InsertableProperties}; might <b>not</b> be null.
	 * @throws InterruptedPropertyPathException
	 *             If any property on the path to this {@link InsertableProperties}
	 *             is null.
	 * @throws UnreferencedPropertyPathException
	 *             If there is any uncontexted property in this
	 *             {@link InsertableProperties}'s path that does not have a
	 *             {@link PropertyReference} included in the given {@link Context}.
	 * @throws OutboundPropertyPathException
	 *             If the property reference provided by the given {@link Context}
	 *             is out of bounds on the given model's elements this
	 *             {@link InsertableProperties} represents.
	 */
	void insert(C collection, E element, R reference, Context context);
}
