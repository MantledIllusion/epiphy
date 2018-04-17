package com.mantledillusion.data.epiphy.interfaces;

import java.util.List;
import java.util.Set;

import com.mantledillusion.data.epiphy.index.IndexContext;
import com.mantledillusion.data.epiphy.index.PropertyIndex;

/**
 * Interface for types identifying a property to a model.
 * 
 * @param <M>
 *            The root model type of this {@link Property}'s property tree.
 * @param <T>
 *            The type of the property this {@link Property} represents.
 */
public interface Property<M, T> {

	/**
	 * The id this {@link Property} is identified by in relation to its direct
	 * parent.
	 * <p>
	 * If not set to a specific value (or null) upon creation, this method returns
	 * this {@link Property}'s objectId as provided by the JVM.
	 * 
	 * @return This {@link Property}'s id; never null
	 */
	public String getId();

	/**
	 * The name this {@link Property} is identified by in relation to the model's
	 * root property.
	 * <p>
	 * The name of a property is the {@link Property}'s path from the model root
	 * property to the property itself.
	 * <p>
	 * A property path is a properties' id, appended to the id's of 0-n of it's
	 * parents, separated by the '.' character.
	 * 
	 * @return This {@link Property}'s name; never null
	 */
	public String getName();

	/**
	 * Returns whether this {@link Property} is the root of a property model (that
	 * is, the root of a tree structure of {@link Property}s).
	 * 
	 * @return True if this {@link Property} instance if a root to a property tree;
	 *         false if not
	 */
	public boolean isRoot();

	/**
	 * Returns whether this {@link Property} instance represents a property that is
	 * a {@link List} of other properties, or in other words, a
	 * {@link ListedProperty}.
	 * 
	 * @return True if this {@link Property} represents a {@link List} of
	 *         properties; false if not
	 */
	public boolean isList();

	/**
	 * Returns whether this {@link Property} is a listed property (or put
	 * differently; whether this {@link Property}'s instances are nested in a
	 * {@link List}, so the parent property is a {@link ListedProperty}.
	 * 
	 * @return True if this {@link Property} is a listed property; false if not
	 */
	public boolean isListed();

	/**
	 * Returns the parent of this {@link Property}.
	 * 
	 * @return The parent of this {@link Property}; might return null if this
	 *         {@link Property} is a root
	 */
	public Property<M, ?> getParent();

	/**
	 * Returns whether this {@link Property} has any registered children.
	 * 
	 * @return True if least one child has been registered, false otherwise
	 */
	public boolean hasChildren();

	/**
	 * Returns whether this {@link Property} has any children in the given model.
	 * 
	 * @param model
	 *            The model to retrieve the property from; might <b>NOT</b> be null.
	 * @return True if this property is not null and has any children in the given
	 *         model.
	 */
	public boolean hasChildrenIn(M model);

	/**
	 * Returns whether this {@link Property} has any children in the given model.
	 * 
	 * @param model
	 *            The model to retrieve the property from; might <b>NOT</b> be null.
	 * @param context
	 *            The {@link IndexContext} that should be used to satisfy the listed
	 *            properties from the root property to this {@link Property}; might
	 *            be null.
	 * @return True if this property is not null and has any children in the given
	 *         model.
	 */
	public boolean hasChildrenIn(M model, IndexContext context);

	/**
	 * Returns the child of this {@link Property} with the given path.
	 * <p>
	 * A property path is a properties' id, appended to the id's of 0-n of it's
	 * parents, separated by the '.' character.
	 * <p>
	 * A property path is always relative to the {@link Property} it is used on. For
	 * example, in a property tree with a hierarchy of A-&gt;B-&gt;C-&gt;D;, the
	 * property path from B to D is B.C.D, as B is the property to begin at.
	 * 
	 * @param propertyPath
	 *            The property path to return the child for; might be null, although
	 *            there will never be a child for a null child path.
	 * @return The child at the end of the given path, as seen from this
	 *         {@link Property}; might be null if there is none
	 */
	public Property<M, ?> getChild(String propertyPath);

	/**
	 * Returns the path of this {@link Property}'s child.
	 * <p>
	 * Note that the child is recognized by objectId; the method will return null
	 * for given a child instance of another property model, even if that model is
	 * equal.
	 * 
	 * @param child
	 *            The child to returns the path for; might be null, although there
	 *            will never be a path for a null child.
	 * @return The path at whose end the given child is located, as seen from this
	 *         {@link Property}; might be null if the child is unknown
	 */
	public String getChildPath(Property<M, ?> child);

	/**
	 * Returns a collapsed {@link Set} of all child properties in the complete
	 * subtree this {@link Property} is the parent of.
	 * 
	 * @return Collapsed {@link Set} of the property subtree beneath this
	 *         {@link Property}; never null
	 */
	public Set<Property<M, ?>> getAllChildren();

	/**
	 * Returns the property paths of all of this {@link Property}'s children.
	 * 
	 * @return A {@link Set} of all paths leading to this properties's children;
	 *         never null, might be empty
	 */
	public Set<String> getAllChildPaths();

	/**
	 * Returns an ordered {@link List} of this {@link Property}'s parent properties,
	 * from the property tree's root property to this {@link Property}.
	 * 
	 * @return An unmodifiable, ordered {@link List} of all properties from the
	 *         property tree's root to this {@link Property}; never null
	 */
	public List<Property<M, ?>> getPath();

	/**
	 * Returns a collapsed {@link Set} of this {@link Property}'s parent properties.
	 * 
	 * @return An unmodifiable {@link Set} of all properties from the property
	 *         tree's root to this {@link Property}; never null
	 */
	public Set<Property<M, ?>> getContext();

	/**
	 * Returns a collapsed {@link Set} of this {@link Property}'s parent properties
	 * that are listed.
	 * <p>
	 * In order words, all of the returned {@link Property}s would need to be
	 * indexed so this {@link Property} could be used to successfully identify the
	 * property in a model.
	 * <p>
	 * That being said, when applying this {@link Property} on a model instance, all
	 * of the returned properties have to be included in a {@link PropertyIndex} of
	 * the {@link IndexContext} delivered to the operation.
	 * 
	 * @return An unmodifiable {@link Set} of all properties from the property
	 *         tree's root to this {@link Property} that are listed; never null
	 */
	public Set<Property<M, ?>> getIndices();

	/**
	 * Returns whether this {@link Property} is reachable, so the property itself
	 * might be null, but all of its parents aren't.
	 * 
	 * @param model
	 *            The model to lookup the property from; might be null.
	 * @return True if all of this {@link Property}'s parents
	 */
	public boolean exists(M model);

	/**
	 * Returns whether this {@link Property} is reachable, so the property itself
	 * might be null, but all of its parents aren't.
	 * 
	 * @param model
	 *            The model to lookup the property from; might be null.
	 * @param context
	 *            The {@link IndexContext} that should be used to satisfy the listed
	 *            properties from the root property to his {@link Property};
	 * @return True if all of this {@link Property}'s parents
	 */
	public boolean exists(M model, IndexContext context);
}
