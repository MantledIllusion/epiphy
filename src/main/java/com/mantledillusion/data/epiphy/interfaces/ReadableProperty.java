package com.mantledillusion.data.epiphy.interfaces;

import java.util.List;
import java.util.Set;

import com.mantledillusion.data.epiphy.context.Context;
import com.mantledillusion.data.epiphy.context.PropertyIndex;
import com.mantledillusion.data.epiphy.exception.InterruptedPropertyPathException;
import com.mantledillusion.data.epiphy.exception.UncontextedPropertyPathException;
import com.mantledillusion.data.epiphy.interfaces.function.IdentifyableProperty;

/**
 * Interface for properties representing a readable property of a model.
 * 
 * @param <M>
 *            The root model type of this {@link ReadableProperty}'s property
 *            tree.
 * @param <T>
 *            The type of the property this {@link ReadableProperty} represents.
 */
public interface ReadableProperty<M, T> extends IdentifyableProperty<M> {

	/**
	 * Returns whether this {@link ReadableProperty} is reachable, so the property
	 * itself might be null, but all of its parents aren't.
	 * 
	 * @param model
	 *            The model to lookup the property from; might be null.
	 * @return True if all of this {@link ReadableProperty}'s parents
	 */
	public default boolean exists(M model) {
		return exists(model, null);
	}

	/**
	 * Returns whether this {@link ReadableProperty} is reachable, so the property
	 * itself might be null, but all of its parents aren't.
	 * 
	 * @param model
	 *            The model to lookup the property from; might be null.
	 * @param context
	 *            The {@link Context} that should be used to satisfy the listed
	 *            properties from the root property to his {@link ReadableProperty};
	 * @return True if all of this {@link ReadableProperty}'s parents
	 */
	public boolean exists(M model, Context context);

	/**
	 * Returns whether this {@link ReadableProperty} has any registered children.
	 * 
	 * @return True if least one child has been registered, false otherwise
	 */
	public boolean hasChildren();

	/**
	 * Returns whether this {@link ReadableProperty} has any children in the given
	 * model.
	 * 
	 * @param model
	 *            The model to retrieve the property from; might <b>not</b> be null.
	 * @return True if this property is not null and has any children in the given
	 *         model.
	 */
	public default boolean hasChildrenIn(M model) {
		return hasChildrenIn(model, null);
	}

	/**
	 * Returns whether this {@link ReadableProperty} has any children in the given
	 * model.
	 * 
	 * @param model
	 *            The model to retrieve the property from; might <b>not</b> be null.
	 * @param context
	 *            The {@link Context} that should be used to satisfy the listed
	 *            properties from the root property to this
	 *            {@link ReadableProperty}; might be null.
	 * @return True if this property is not null and has any children in the given
	 *         model.
	 */
	public boolean hasChildrenIn(M model, Context context);

	/**
	 * Returns the child of this {@link ReadableProperty} with the given path.
	 * <p>
	 * A property path is a properties' id, appended to the id's of 0-n of it's
	 * parents, separated by the '.' character.
	 * <p>
	 * A property path is always relative to the {@link ReadableProperty} it is used
	 * on. For example, in a property tree with a hierarchy of A-&gt;B-&gt;C-&gt;D;,
	 * the property path from B to D is B.C.D, as B is the property to begin at.
	 * 
	 * @param propertyPath
	 *            The property path to return the child for; might be null, although
	 *            there will never be a child for a null child path.
	 * @return The child at the end of the given path, as seen from this
	 *         {@link ReadableProperty}; might be null if there is none
	 */
	public ReadableProperty<M, ?> getChild(String propertyPath);

	/**
	 * Returns the path of this {@link ReadableProperty}'s child.
	 * <p>
	 * Note that the child is recognized by objectId; the method will return null
	 * for given a child instance of another property model, even if that model is
	 * equal.
	 * 
	 * @param child
	 *            The child to returns the path for; might be null, although there
	 *            will never be a path for a null child.
	 * @return The path at whose end the given child is located, as seen from this
	 *         {@link ReadableProperty}; might be null if the child is unknown
	 */
	public String getChildPath(ReadableProperty<M, ?> child);

	/**
	 * Returns a collapsed {@link Set} of all child properties in the complete
	 * subtree this {@link ReadableProperty} is the parent of.
	 * 
	 * @return Collapsed {@link Set} of the property subtree beneath this
	 *         {@link ReadableProperty}; never null
	 */
	public Set<ReadableProperty<M, ?>> getAllChildren();

	/**
	 * Returns the property paths of all of this {@link ReadableProperty}'s
	 * children.
	 * 
	 * @return A {@link Set} of all paths leading to this properties's children;
	 *         never null, might be empty
	 */
	public Set<String> getAllChildPaths();

	/**
	 * Returns an ordered {@link List} of this {@link ReadableProperty}'s parent
	 * properties, from the property tree's root property to this
	 * {@link ReadableProperty}.
	 * 
	 * @return An unmodifiable, ordered {@link List} of all properties from the
	 *         property tree's root to this {@link ReadableProperty}; never null
	 */
	public List<ReadableProperty<M, ?>> getPath();

	/**
	 * Returns an unordered {@link Set} of this {@link ReadableProperty}'s parent
	 * properties, including this {@link ReadableProperty}.
	 * 
	 * @return An unmodifiable, unordered {@link Set} of all properties from the
	 *         property tree's root to this {@link ReadableProperty}; never null
	 */
	public Set<ReadableProperty<M, ?>> getParents();

	/**
	 * Returns whether this {@link ReadableProperty}'s property is null, including
	 * the case when a parent property is null.
	 * 
	 * @param model
	 *            The model to lookup the property from; might be null.
	 * @return True if this {@link ReadableProperty} or any of its parents is null,
	 *         false if it has a non-null value
	 */
	public default boolean isNull(M model) {
		return get(model, true) == null;
	}

	/**
	 * Returns whether this {@link ReadableProperty}'s property is null, including
	 * the case when a parent property is null.
	 * 
	 * @param model
	 *            The model to lookup the property from; might be null.
	 * @param context
	 *            The {@link Context} that should be used to satisfy the listed
	 *            properties from the root property to his {@link ReadableProperty};
	 *            might be null.
	 * @return True if this {@link ReadableProperty} or any of its parents is null,
	 *         false if it has a non-null value
	 */
	public default boolean isNull(M model, Context context) {
		return get(model, context, true) == null;
	}

	/**
	 * Retrieves this {@link ReadableProperty}'s property out of the given model.
	 * 
	 * @param model
	 *            The model to retrieve the property from; might <b>not</b> be null.
	 * @return The retrieved property; might return null if the property is null
	 * @throws InterruptedPropertyPathException
	 *             If any property on the path to this {@link ReadableProperty} is
	 *             null.
	 * @throws UncontextedPropertyPathException
	 *             If there is any listed property in this
	 *             {@link ReadableProperty}'s path.
	 */
	public default T get(M model) {
		return get(model, null, false);
	}

	/**
	 * Retrieves this {@link ReadableProperty}'s property out of the given model.
	 * 
	 * @param model
	 *            The model to retrieve the property from; might be null if
	 *            allowNull is set to true.
	 * @param allowNull
	 *            Allows any parent property of this property to be null. If set to
	 *            true, instead of throwing an
	 *            {@link InterruptedPropertyPathException}, the method will just
	 *            return null.
	 * @return The retrieved property; might return null if the property is null or
	 *         if a parent property is null and allowNull is set to true
	 * @throws InterruptedPropertyPathException
	 *             If any property on the path to this {@link ReadableProperty} is
	 *             null and allowNull is set to false.
	 * @throws UncontextedPropertyPathException
	 *             If there is any listed property in this
	 *             {@link ReadableProperty}'s path.
	 */
	public default T get(M model, boolean allowNull) {
		return get(model, null, allowNull);
	}

	/**
	 * Retrieves this {@link ReadableProperty}'s property out of the given model,
	 * using the given {@link Context}.
	 * 
	 * @param model
	 *            The model to retrieve the property from; might <b>not</b> be null.
	 * @param context
	 *            The {@link Context} that should be used to satisfy the listed
	 *            properties from the root property to this
	 *            {@link ReadableProperty}; might be null.
	 * @return The retrieved property; might return null if the property is null
	 * @throws InterruptedPropertyPathException
	 *             If any property on the path to this {@link ReadableProperty} is
	 *             null.
	 * @throws UncontextedPropertyPathException
	 *             If there is any listed property in this
	 *             {@link ReadableProperty}'s path that does not have a
	 *             {@link PropertyIndex} included in the given {@link Context}.
	 */
	public default T get(M model, Context context) {
		return get(model, context, false);
	}

	/**
	 * Retrieves this {@link ReadableProperty}'s property out of the given model,
	 * using the given {@link Context}.
	 * 
	 * @param model
	 *            The model to retrieve the property from; might be null if
	 *            allowNull is set to true.
	 * @param context
	 *            The {@link Context} that should be used to satisfy the listed
	 *            properties from the root property to this
	 *            {@link ReadableProperty}; might be null.
	 * @param allowNull
	 *            Allows any parent property of this property to be null. If set to
	 *            true, instead of throwing an
	 *            {@link InterruptedPropertyPathException}, the method will just
	 *            return null.
	 * @return The retrieved property; might return null if the property is null or
	 *         if a parent property is null and allowNull is set to true
	 * @throws InterruptedPropertyPathException
	 *             If any property on the path to this {@link ReadableProperty} is
	 *             null and allowNull is set to false.
	 * @throws UncontextedPropertyPathException
	 *             If there is any listed property in this
	 *             {@link ReadableProperty}'s path that does not have a
	 *             {@link PropertyIndex} included in the given {@link Context}.
	 */
	public T get(M model, Context context, boolean allowNull);
}
