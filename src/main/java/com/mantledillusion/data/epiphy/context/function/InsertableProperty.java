package com.mantledillusion.data.epiphy.context.function;

import com.mantledillusion.data.epiphy.Property;
import com.mantledillusion.data.epiphy.context.Context;
import com.mantledillusion.data.epiphy.context.ReferencableProperty;
import com.mantledillusion.data.epiphy.context.reference.PropertyReference;
import com.mantledillusion.data.epiphy.exception.InterruptedPropertyPathException;
import com.mantledillusion.data.epiphy.exception.OutboundPropertyPathException;
import com.mantledillusion.data.epiphy.exception.UnreferencedPropertyPathException;

/**
 * Interface for properties that carry elements which can be inserted using a specific reference.
 *
 * @param <O>
 *            The root parent object type of this {@link Property}.
 * @param <V>
 *     		  The type of the child value this {@link Property} represents.
 * @param <E>
 *            The type of the property element this {@link InsertableProperty} represents.
 * @param <R>
 *            The type of the context reference.
 */
public interface InsertableProperty<O, V, E, R> extends ReferencableProperty<O, V, E, R> {

	/**
	 * Adds an element to the batch that is represented by this {@link InsertableProperty}.
	 * <p>
	 * The element is added at the next natural reference.
	 * <p>
	 * Note that this is a writing operation, so the property has to {@link Property#exists(Object)} in the object.
	 *
	 * @param object
	 * 			The object to add the element to; might <b>not</b> be null.
	 * @param element
	 * 			The element to add; might be null.
	 * @param reference
	 * 			The reference to insert the element with; might <b>not</b> be null.
	 * @throws InterruptedPropertyPathException
	 * 			If any property on the path to this {@link InsertableProperty} is null.
	 * @throws UnreferencedPropertyPathException
	 *          If there is any uncontexted property in this {@link InsertableProperty}'s path.
	 */
	default void insert(O object, E element, R reference) throws
			InterruptedPropertyPathException, UnreferencedPropertyPathException {
		insert(object, element, reference, null);
	}

	/**
	 * Adds an element to the batch that is represented by this {@link InsertableProperty}.
	 * <p>
	 * The element is added at the next natural reference.
	 * <p>
	 * Note that this is a writing operation, so the property has to {@link Property#exists(Object)} in the object.
	 *
	 * @param object
	 * 			The object to add the element to; might <b>not</b> be null.
	 * @param element
	 * 			The element to add; might be null.
	 * @param reference
	 * 			The reference to insert the element with; might <b>not</b> be null.
	 * @param context
	 * 			The {@link Context} that should be used to satisfy the contexted properties from the root property to
	 * 			this {@link InsertableProperty}; might be null.
	 * @throws InterruptedPropertyPathException
	 * 			If any property on the path to this {@link InsertableProperty} is null.
	 * @throws UnreferencedPropertyPathException
	 * 			If there is any uncontexted property in this {@link InsertableProperty}'s path that does not have a
	 * 			{@link PropertyReference} included in the given {@link Context}.
	 * @throws OutboundPropertyPathException
	 * 			If the property reference provided by the given {@link Context} is out of bounds on the given object's
	 * 			elements this {@link InsertableProperty} represents.
	 */
	void insert(O object, E element, R reference, Context context) throws
			InterruptedPropertyPathException, UnreferencedPropertyPathException, OutboundPropertyPathException;
}
