package com.mantledillusion.data.epiphy.io;

import com.mantledillusion.data.epiphy.Property;
import com.mantledillusion.data.epiphy.context.Context;
import com.mantledillusion.data.epiphy.exception.InterruptedPropertyPathException;
import com.mantledillusion.data.epiphy.exception.OutboundPropertyPathException;
import com.mantledillusion.data.epiphy.exception.ReadonlyPropertyException;
import com.mantledillusion.data.epiphy.exception.UnreferencedPropertyPathException;

/**
 * Functional interface for writing a value to an object using a {@link Context}.
 *
 * @param <O>
 *            The object type.
 * @param <V>
 *            The value type.
 */
@FunctionalInterface
public interface ReferencedSetter<O, V> {

	/**
	 * Writes the value to the object.
	 *
	 * @param object
	 *            The instance to write the value to; might be null.
	 * @param value
	 *            The value to set; might be null.
	 * @param context
	 *            The {@link Context} to use; might be null.
	 * @throws InterruptedPropertyPathException
	 *             If the given object is null.
	 * @throws UnreferencedPropertyPathException
	 *             If the given {@link Property} has to be referenced but there is no
	 *             {@link com.mantledillusion.data.epiphy.context.reference.PropertyReference} included in the given
	 *             {@link Context}.
	 * @throws OutboundPropertyPathException
	 *             If the given {@link Property} has to be referenced and there is a
	 *             {@link com.mantledillusion.data.epiphy.context.reference.PropertyReference} included in the  given
	 *             {@link Context} that does not match the given {@link Property} value's bounds.
	 * @throws ReadonlyPropertyException
	 *             If this {@link ReferencedSetter} is a bulk implementation.
	 */
	void set(Property<O, V> property, O object, V value, Context context)
			throws InterruptedPropertyPathException, UnreferencedPropertyPathException, OutboundPropertyPathException,
			ReadonlyPropertyException;
}