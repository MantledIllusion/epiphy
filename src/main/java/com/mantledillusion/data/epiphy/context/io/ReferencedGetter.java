package com.mantledillusion.data.epiphy.context.io;

import com.mantledillusion.data.epiphy.context.Context;
import com.mantledillusion.data.epiphy.exception.InterruptedPropertyPathException;
import com.mantledillusion.data.epiphy.Property;
import com.mantledillusion.data.epiphy.exception.OutboundPropertyPathException;
import com.mantledillusion.data.epiphy.exception.UnreferencedPropertyPathException;

import java.util.*;

/**
 * Interface for retrieving a value from an object using a {@link Context}.
 *
 * @param <O>
 *            The object type.
 * @param <V>
 *            The value type.
 */
public interface ReferencedGetter<O, V> {

	/**
	 * Retrieves the value from the object.
	 *
	 * @param property
	 * 			The property to get; might <b>not</b> be null.
	 * @param object
	 * 			The instance to lookup the value from; might be null.
	 * @param context
	 * 			The {@link Context} to use; might be null.
	 * @param allowNull
	 * 			Whether or not the given object is allowed to be null. If set to true, instead of throwing an
	 * 			{@link InterruptedPropertyPathException}, the method will just return null.
	 * @return
	 * 			The retrieved value; might return null if the value is null or if the given object is null and
	 * 			allowNull is set to true
	 * @throws InterruptedPropertyPathException
	 * 			If the given object is null and allowNull is set to false.
	 * @throws UnreferencedPropertyPathException
	 * 			If the given {@link Property} has to be referenced but there is no
	 * 			{@link com.mantledillusion.data.epiphy.context.reference.PropertyReference} included in the given
	 * 			{@link Context}.
	 * @throws OutboundPropertyPathException
	 * 			If the given {@link Property} has to be referenced and there is a
	 * 			{@link com.mantledillusion.data.epiphy.context.reference.PropertyReference} included in the  given
	 * 			{@link Context} that does not match the given {@link Property} value's bounds.
	 */
	V get(Property<O, V> property, O object, Context context, boolean allowNull)
			throws InterruptedPropertyPathException, UnreferencedPropertyPathException, OutboundPropertyPathException;

	/**
	 * Returns a {@link Set} of all {@link Property}s in the parent hierarchy of the given {@link Property}, including
	 * the given one.
	 *
	 * @param property
	 * 			The property to check; might <b>not</b> be null.
	 * @return
	 * 			A {@link Set} of {@link Property}s, never null, might be empty
	 */
	Set<Property<?, ?>> getHierarchy(Property<O, V> property);

	/**
	 * Returns the count of occurrences ({@link Property#exists(Object)} != null) there are of the given
	 * {@link Property} in the given object.
	 *
	 * @param property
	 * 			The property to check; might <b>not</b> be null.
	 * @param object
	 * 			The instance to check the value occurrences in; might be null.
	 * @return
	 * 			The occurrence count; always >=0
	 */
	int occurrences(Property<O, V> property, O object);

	/**
	 * Returns a {@link Collection} of {@link Context}s for every occurrence of the given {@link Property} in the given
	 * object.
	 * <p>
	 * The amount of returned {@link Context}s equals the result of {@link #occurrences(Property, Object)} on the same
	 * {@link Property}/object.
	 *
	 * @param property
	 * 			The property to check; might <b>not</b> be null.
	 * @param object
	 * 			The instance to check the value occurrences in; might be null.
	 * @return
	 * 			A {@link Collection} of {@link Context}s, never null, might be empty
	 */
	Collection<Context> contextualize(Property<O, V> property, O object);
}