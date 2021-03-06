package com.mantledillusion.data.epiphy.context.io;

import com.mantledillusion.data.epiphy.ModelPropertyNode;
import com.mantledillusion.data.epiphy.context.Context;
import com.mantledillusion.data.epiphy.context.TraversingMode;
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

	Property<?, ?> getParent();

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
	 * 			The occurrence count; always &gt;=0
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
	 * @param context
	 *          The context to use as a base; might be null.
	 * @param traversingMode
	 *          Transcend recursively through all layers of {@link ModelPropertyNode}s that might be on the path from
	 *          the root {@link Property} to this {@link Property}; might <b>not</b> be null.
	 * @param includeNull
	 *          Include {@link Context}s for all values where {@link Property#exists(Object, Context)} would return
	 *          true, so they might be null.
	 * @return
	 * 			A {@link Collection} of {@link Context}s, never null, might be empty
	 */
	Collection<Context> contextualize(Property<O, V> property, O object, Context context, TraversingMode traversingMode, boolean includeNull);

	/**
	 * Returns a {@link Collection} of {@link Context}s for every occurrence where this {@link Property} is represented
	 * by the given value in the given object.
	 *
	 * @param property
	 * 			The property to check; might <b>not</b> be null.
	 * @param object
	 * 			The instance to check the value occurrences in; might be null.
	 * @param value
	 *          The value to look for; might be null, though to equal the {@link Property} has to
	 *          {@link Property#exists(Object, Context)} in the {@link Context}.
	 * @param context
	 *          The context in which to find the given value; might be null.
	 * @return
	 * 			A {@link Collection} of {@link Context}s, never null, might be empty
	 */
	Collection<Context> contextualize(Property<O, V> property, O object, V value, Context context);

	@SuppressWarnings({"unchecked", "rawtypes"})
	default <S> ReferencedGetter<S, V> obfuscate(Class<O> objectType) {
		if (objectType == null) {
			throw new IllegalArgumentException("Cannot obfuscate using a null object type class");
		}
		return new ReferencedGetter() {

			@Override
			public Object get(Property property, Object object, Context context, boolean allowNull)
					throws InterruptedPropertyPathException, UnreferencedPropertyPathException, OutboundPropertyPathException {
				return ReferencedGetter.this.get((Property<O, V>) property, objectType.isInstance(object) ? (O) object : null, context, allowNull);
			}

			@Override
			public Property<?, ?> getParent() {
				return ReferencedGetter.this.getParent();
			}

			@Override
			public Set<Property<?, ?>> getHierarchy(Property property) {
				return ReferencedGetter.this.getHierarchy(property);
			}

			@Override
			public int occurrences(Property property, Object object) {
				return ReferencedGetter.this.occurrences((Property<O, V>) property, objectType.isInstance(object) ? (O) object : null);
			}

			@Override
			public Collection<Context> contextualize(Property property, Object object, Context context, TraversingMode traversingMode, boolean includeNull) {
				return ReferencedGetter.this.contextualize((Property<O, V>) property, objectType.isInstance(object) ? (O) object : null, context, traversingMode, includeNull);
			}

			@Override
			public Collection<Context> contextualize(Property property, Object object, Object value, Context context) {
				return ReferencedGetter.this.contextualize((Property<O, V>) property, objectType.isInstance(object) ? (O) object : null, (V) value, context);
			}
		};
	}
}