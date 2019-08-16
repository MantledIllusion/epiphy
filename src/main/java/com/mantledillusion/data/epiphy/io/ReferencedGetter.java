package com.mantledillusion.data.epiphy.io;

import com.mantledillusion.data.epiphy.context.Context;
import com.mantledillusion.data.epiphy.exception.InterruptedPropertyPathException;
import com.mantledillusion.data.epiphy.Property;
import com.mantledillusion.data.epiphy.exception.OutboundPropertyPathException;
import com.mantledillusion.data.epiphy.exception.UnreferencedPropertyPathException;

/**
 * Functional interface for retrieving a value from an object using a {@link Context}.
 *
 * @param <O>
 *            The object type.
 * @param <V>
 *            The value type.
 */
@FunctionalInterface
public interface ReferencedGetter<O, V> {

	/**
	 * Retrieves the value from the object.
	 *
	 * @param object
	 *            The instance to lookup the value from; might be null.
	 * @param context
	 *            The {@link Context} to use; might be null.
	 * @param allowNull
	 *            Whether or not the given object is allowed to be null. If set to true, instead of throwing an
	 *            {@link InterruptedPropertyPathException}, the method will just return null.
	 * @return The retrieved value; might return null if the value is null or if the given object is null and allowNull
	 * is set to true
	 * @throws InterruptedPropertyPathException
	 *             If the given object is null and allowNull is set to false.
	 * @throws UnreferencedPropertyPathException
	 *             If the given {@link Property} has to be referenced but there is no
	 *             {@link com.mantledillusion.data.epiphy.context.reference.PropertyReference} included in the given
	 *             {@link Context}.
	 * @throws OutboundPropertyPathException
	 *             If the given {@link Property} has to be referenced and there is a
	 *             {@link com.mantledillusion.data.epiphy.context.reference.PropertyReference} included in the  given
	 *             {@link Context} that does not match the given {@link Property} value's bounds.
	 */
	V get(Property<O, V> property, O object, Context context, boolean allowNull)
			throws InterruptedPropertyPathException, UnreferencedPropertyPathException, OutboundPropertyPathException;
}