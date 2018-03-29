package com.mantledillusion.data.epiphy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.mantledillusion.data.epiphy.exception.InterruptedPropertyPathException;
import com.mantledillusion.data.epiphy.exception.UnindexedPropertyPathException;
import com.mantledillusion.data.epiphy.index.IndexContext;
import com.mantledillusion.data.epiphy.index.PropertyIndex;
import com.mantledillusion.data.epiphy.index.impl.DefaultIndexContext;
import com.mantledillusion.data.epiphy.io.Getter;
import com.mantledillusion.data.epiphy.io.Setter;

/**
 * Base type identifying a property to a model.
 * <p>
 * This {@link Class} should be used to begin a new property tree using the
 * static methods...<br>
 * - {@link ModelProperty#rootChild()}<br>
 * - {@link ModelProperty#rootChild(String)}<br>
 * - {@link ModelProperty#rootChildList()}<br>
 * - {@link ModelProperty#rootChildList(String)}<br>
 * ... that are able to create a property tree root.
 * <p>
 * Such a root may then be used to define the model property tree upon.
 * 
 * @param <M>
 *            The root model type of this {@link ModelProperty}'s property tree.
 * @param <T>
 *            The type of the property this {@link ModelProperty} represents.
 */
public abstract class ModelProperty<M, T> {

	public static final String PROPERTY_ID_PATTERN = "[a-zA-Z0-9_-]+";
	public static final String PROPERTY_PATH_PATTERN = PROPERTY_ID_PATTERN + "(\\." + PROPERTY_ID_PATTERN + ")*";

	interface IndexedGetter<P, T> {

		T get(P source, IndexContext context, boolean allowNull);
	}

	interface IndexedSetter<P, T> {

		void set(P target, T value, IndexContext context);
	}

	private final String id;
	private final String name;

	private final ModelProperty<M, ?> parent;
	private final Map<String, ModelProperty<M, ?>> childrenByPaths = new HashMap<>();
	private final Map<ModelProperty<M, ?>, String> pathsByChildren = new IdentityHashMap<>();

	private final IndexedGetter<?, T> getter;
	private final IndexedSetter<?, T> setter;

	private final List<ModelProperty<M, ?>> path;
	private final Set<ModelProperty<M, ?>> context;
	private final boolean isList;
	private final Set<ModelProperty<M, ?>> indices;

	ModelProperty(String id) {
		this(id, null, null, null, false);
	}

	<P> ModelProperty(String id, ModelProperty<M, P> parent, IndexedGetter<P, T> getter, IndexedSetter<P, T> setter,
			boolean isListed) {
		id = id == null ? String.valueOf(System.identityHashCode(this)) : id;
		if (!id.matches(PROPERTY_ID_PATTERN)) {
			throw new IllegalArgumentException("The property id '" + id + "' does not match the pattern '"
					+ PROPERTY_ID_PATTERN + "' for model property names.");
		}

		this.parent = parent;

		this.getter = getter;
		this.setter = setter;

		this.isList = isListed;

		if (this.parent == null) {
			this.id = id;
			this.name = id;
			this.path = Collections.singletonList(this);

			this.indices = Collections.emptySet();
		} else {
			this.id = id;
			this.name = parent.getName() + '.' + id;
			List<ModelProperty<M, ?>> path = new ArrayList<ModelProperty<M, ?>>(this.parent.path);
			path.addAll(Collections.singletonList(this));
			this.path = Collections.unmodifiableList(path);

			this.parent.addChild(id, this);

			Set<ModelProperty<M, ?>> indices = new HashSet<>(this.parent.indices);
			if (this.parent.isList()) {
				indices.add(this.parent);
			}
			this.indices = Collections.unmodifiableSet(indices);
		}
		this.context = Collections.unmodifiableSet(new HashSet<>(this.path));
	}

	private <T2> void addChild(String id, ModelProperty<M, T2> child) {
		String path = this.id + '.' + id;

		if (this.childrenByPaths.containsKey(path)) {
			throw new IllegalStateException(
					"Cannot add more than one child to the property " + this + " with the id " + id);
		}

		this.childrenByPaths.put(path, child);
		this.pathsByChildren.put(child, path);
		if (this.parent != null) {
			this.parent.addChild(path, child);
		}
	}

	/**
	 * The id this {@link ModelProperty} is identified by in relation to its direct
	 * parent.
	 * <p>
	 * If not set to a specific value (or null) upon creation, this method returns
	 * this {@link ModelProperty}'s objectId as provided by the JVM.
	 * 
	 * @return This {@link ModelProperty}'s id; never null
	 */
	public String getId() {
		return id;
	}

	/**
	 * The name this {@link ModelProperty} is identified by in relation to the
	 * model's root property.
	 * <p>
	 * The name of a property is the {@link ModelProperty}'s path from the model
	 * root property to the property itself.
	 * <p>
	 * A property path is a properties' id, appended to the id's of 0-n of it's
	 * parents, separated by the '.' character.
	 * 
	 * @return This {@link ModelProperty}'s name; never null
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Returns whether this {@link ModelProperty} is the root of a property model
	 * (that is, the root of a tree structure of {@link ModelProperty}s).
	 * 
	 * @return True if this {@link ModelProperty} instance if a root to a property
	 *         tree; false if not
	 */
	public final boolean isRoot() {
		return this.parent == null;
	}

	/**
	 * Returns whether this {@link ModelProperty} instance represents a property
	 * that is a {@link List} of other properties, or in other words, a
	 * {@link ModelPropertyList}.
	 * 
	 * @return True if this {@link ModelProperty} represents a {@link List} of
	 *         properties; false if not
	 */
	public final boolean isList() {
		return isList;
	}

	/**
	 * Returns whether this {@link ModelProperty} is a listed property (or put
	 * differently; whether this {@link ModelProperty}'s instances are nested in a
	 * {@link List}, so the parent property is a {@link ModelPropertyList}.
	 * 
	 * @return True if this {@link ModelProperty} is a listed property; false if not
	 */
	public final boolean isListed() {
		return parent.isList();
	}

	/**
	 * Returns the parent of this {@link ModelProperty}.
	 * 
	 * @return The parent of this {@link ModelProperty}; might return null if this
	 *         {@link ModelProperty} is a root
	 */
	public final ModelProperty<M, ?> getParent() {
		return this.parent;
	}

	/**
	 * Returns the child of this {@link ModelProperty} with the given path.
	 * <p>
	 * A property path is a properties' id, appended to the id's of 0-n of it's
	 * parents, separated by the '.' character.
	 * <p>
	 * A property path is always relative to the {@link ModelProperty} it is used
	 * on. For example, in a property tree with a hierarchy of A-&gt;B-&gt;C-&gt;D;,
	 * the property path from B to D is B.C.D, as B is the property to begin at.
	 * 
	 * @param propertyPath
	 *            The property path to return the child for; might be null, although
	 *            there will never be a child for a null child path.
	 * @return The child at the end of the given path, as seen from this
	 *         {@link ModelProperty}; might be null if there is none
	 */
	public final ModelProperty<M, ?> getChild(String propertyPath) {
		if (propertyPath != null && !propertyPath.matches(PROPERTY_PATH_PATTERN)) {
			throw new IllegalArgumentException("The property path '" + propertyPath
					+ "' is not valid; a property path has to match " + PROPERTY_PATH_PATTERN);
		}
		return this.childrenByPaths.get(propertyPath);
	}

	/**
	 * Returns the path of this {@link ModelProperty}'s child.
	 * <p>
	 * Note that the child is recognized by objectId; the method will return null
	 * for given a child instance of another property model, even if that model is
	 * equal.
	 * 
	 * @param child
	 *            The child to returns the path for; might be null, although there
	 *            will never be a path for a null child.
	 * @return The path at whose end the given child is located, as seen from this
	 *         {@link ModelProperty}; might be null if the child is unknown
	 */
	public final String getChildPath(ModelProperty<M, ?> child) {
		return this.pathsByChildren.get(child);
	}

	/**
	 * Returns a collapsed {@link Set} of all child properties in the complete
	 * subtree this {@link ModelProperty} is the parent of.
	 * 
	 * @return Collapsed {@link Set} of the property subtree beneath this
	 *         {@link ModelProperty}; never null
	 */
	public final Set<ModelProperty<M, ?>> getAllChildren() {
		return Collections.unmodifiableSet(new HashSet<>(this.childrenByPaths.values()));
	}

	/**
	 * Returns the property paths of all of this {@link ModelProperty}'s children.
	 * 
	 * @return A {@link Set} of all paths leading to this properties's children;
	 *         never null, might be empty
	 */
	public final Set<String> getAllChildPaths() {
		return Collections.unmodifiableSet(this.childrenByPaths.keySet());
	}

	/**
	 * Returns an ordered {@link List} of this {@link ModelProperty}'s parent
	 * properties, from the property tree's root property to this
	 * {@link ModelProperty}.
	 * 
	 * @return An unmodifiable, ordered {@link List} of all properties from the
	 *         property tree's root to this {@link ModelProperty}; never null
	 */
	public final List<ModelProperty<M, ?>> getPath() {
		return this.path;
	}

	/**
	 * Returns a collapsed {@link Set} of this {@link ModelProperty}'s parent
	 * properties.
	 * 
	 * @return An unmodifiable {@link Set} of all properties from the property
	 *         tree's root to this {@link ModelProperty}; never null
	 */
	public final Set<ModelProperty<M, ?>> getContext() {
		return this.context;
	}

	/**
	 * Returns a collapsed {@link Set} of this {@link ModelProperty}'s parent
	 * properties that are listed.
	 * <p>
	 * In order words, all of the returned {@link ModelProperty}s would need to be
	 * indexed so this {@link ModelProperty} could be used to successfully identify
	 * the property in a model.
	 * <p>
	 * That being said, when applying this {@link ModelProperty} on a model
	 * instance, all of the returned properties have to be included in a
	 * {@link PropertyIndex} of the {@link IndexContext} delivered to the operation.
	 * 
	 * @return An unmodifiable {@link Set} of all properties from the property
	 *         tree's root to this {@link ModelProperty} that are listed; never null
	 */
	public final Set<ModelProperty<M, ?>> getIndices() {
		return this.indices;
	}

	/**
	 * Returns whether this {@link ModelProperty}'s property is null, including the
	 * case when a parent property is null.
	 * 
	 * @param model
	 *            The model to lookup the property from; might be null.
	 * @return True if this {@link ModelProperty} or any of its parents is null,
	 *         false if it has a non-null value
	 */
	public final boolean isNull(M model) {
		return get(model, true) == null;
	}

	/**
	 * Returns whether this {@link ModelProperty}'s property is null, including the
	 * case when a parent property is null.
	 * 
	 * @param model
	 *            The model to lookup the property from; might be null.
	 * @param context
	 *            The {@link IndexContext} that should be used to satisfy the listed
	 *            properties from the root property to his {@link ModelProperty};
	 *            might be null.
	 * @return True if this {@link ModelProperty} or any of its parents is null,
	 *         false if it has a non-null value
	 */
	public final boolean isNull(M model, IndexContext context) {
		return get(model, context, true) == null;
	}

	/**
	 * Returns whether this {@link ModelProperty} is reachable, so the property
	 * itself might be null, but all of its parents aren't.
	 * 
	 * @param model
	 *            The model to lookup the property from; might be null.
	 * @return True if all of this {@link ModelProperty}'s parents
	 */
	public final boolean exists(M model) {
		return exists(model, null);
	}

	/**
	 * Returns whether this {@link ModelProperty} is reachable, so the property
	 * itself might be null, but all of its parents aren't.
	 * 
	 * @param model
	 *            The model to lookup the property from; might be null.
	 * @param context
	 *            The {@link IndexContext} that should be used to satisfy the listed
	 *            properties from the root property to his {@link ModelProperty};
	 * @return True if all of this {@link ModelProperty}'s parents
	 */
	public final boolean exists(M model, IndexContext context) {
		if (this.parent == null) {
			return true;
		} else if (this.parent.exists(model, context)) {
			return this.parent.hasChildrenIn(model, context);
		} else {
			return false;
		}
	}

	/**
	 * Returns whether this {@link ModelProperty} has any registered children.
	 * 
	 * @return True if least one child has been registered, false otherwise
	 */
	public final boolean hasChildren() {
		return !this.childrenByPaths.isEmpty();
	}

	/**
	 * Returns whether this {@link ModelProperty} has any children in the given
	 * model.
	 * 
	 * @param model
	 *            The model to retrieve the property from; might <b>NOT</b> be null.
	 * @return True if this property is not null and has any children in the given
	 *         model.
	 */
	public final boolean hasChildrenIn(M model) {
		return hasChildrenIn(model, null);
	}

	/**
	 * Returns whether this {@link ModelProperty} has any children in the given
	 * model.
	 * 
	 * @param model
	 *            The model to retrieve the property from; might <b>NOT</b> be null.
	 * @param context
	 *            The {@link IndexContext} that should be used to satisfy the listed
	 *            properties from the root property to this {@link ModelProperty};
	 *            might be null.
	 * @return True if this property is not null and has any children in the given
	 *         model.
	 */
	public abstract boolean hasChildrenIn(M model, IndexContext context);

	/**
	 * Retrieves this {@link ModelProperty}'s property out of the given model.
	 * 
	 * @param model
	 *            The model to retrieve the property from; might <b>NOT</b> be null.
	 * @return The retrieved property; might return null if the property is null
	 * @throws InterruptedPropertyPathException
	 *             If any property on the path to this {@link ModelProperty} is
	 *             null.
	 * @throws UnindexedPropertyPathException
	 *             If there is any listed property in this {@link ModelProperty}'s
	 *             path.
	 */
	public final T get(M model) {
		return get(model, null, false);
	}

	/**
	 * Retrieves this {@link ModelProperty}'s property out of the given model.
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
	 *             If any property on the path to this {@link ModelProperty} is null
	 *             and allowNull is set to false.
	 * @throws UnindexedPropertyPathException
	 *             If there is any listed property in this {@link ModelProperty}'s
	 *             path.
	 */
	public final T get(M model, boolean allowNull) {
		return get(model, null, allowNull);
	}

	/**
	 * Retrieves this {@link ModelProperty}'s property out of the given model, using
	 * the given {@link IndexContext}.
	 * 
	 * @param model
	 *            The model to retrieve the property from; might <b>NOT</b> be null.
	 * @param context
	 *            The {@link IndexContext} that should be used to satisfy the listed
	 *            properties from the root property to this {@link ModelProperty};
	 *            might be null.
	 * @return The retrieved property; might return null if the property is null
	 * @throws InterruptedPropertyPathException
	 *             If any property on the path to this {@link ModelProperty} is
	 *             null.
	 * @throws UnindexedPropertyPathException
	 *             If there is any listed property in this {@link ModelProperty}'s
	 *             path that does not have a {@link PropertyIndex} included in the
	 *             given {@link IndexContext}.
	 */
	public final T get(M model, IndexContext context) {
		return get(model, context, false);
	}

	/**
	 * Retrieves this {@link ModelProperty}'s property out of the given model, using
	 * the given {@link IndexContext}.
	 * 
	 * @param model
	 *            The model to retrieve the property from; might be null if
	 *            allowNull is set to true.
	 * @param context
	 *            The {@link IndexContext} that should be used to satisfy the listed
	 *            properties from the root property to this {@link ModelProperty};
	 *            might be null.
	 * @param allowNull
	 *            Allows any parent property of this property to be null. If set to
	 *            true, instead of throwing an
	 *            {@link InterruptedPropertyPathException}, the method will just
	 *            return null.
	 * @return The retrieved property; might return null if the property is null or
	 *         if a parent property is null and allowNull is set to true
	 * @throws InterruptedPropertyPathException
	 *             If any property on the path to this {@link ModelProperty} is null
	 *             and allowNull is set to false.
	 * @throws UnindexedPropertyPathException
	 *             If there is any listed property in this {@link ModelProperty}'s
	 *             path that does not have a {@link PropertyIndex} included in the
	 *             given {@link IndexContext}.
	 */
	@SuppressWarnings("unchecked")
	public final T get(M model, IndexContext context, boolean allowNull) {
		if (this.parent == null) {
			return (T) model;
		} else {
			context = context == null ? DefaultIndexContext.EMPTY : context;
			return castAndGet(model, context, allowNull);
		}
	}

	@SuppressWarnings("unchecked")
	private <P> T castAndGet(M model, IndexContext context, boolean allowNull) {
		P parentValue = ((ModelProperty<M, P>) this.parent).get(model, context, allowNull);
		return ((IndexedGetter<P, T>) this.getter).get(parentValue, context, allowNull);
	}

	/**
	 * Sets the given value to the property of the given model instance this
	 * {@link ModelProperty} represents.
	 * <p>
	 * Note that this is a writing operation, so the property has to
	 * {@link #exists(Object)} in the model.
	 * 
	 * @param model
	 *            The model to set the property on; might <b>NOT</b> be null.
	 * @param value
	 *            The value to set; might be null.
	 * @throws InterruptedPropertyPathException
	 *             If any property on the path to this {@link ModelProperty} is
	 *             null.
	 * @throws UnindexedPropertyPathException
	 *             If there is any listed property in this {@link ModelProperty}'s
	 *             path.
	 */
	public final void set(M model, T value) {
		set(model, value, null);
	}

	/**
	 * Sets the given value to the property of the given model instance this
	 * {@link ModelProperty} represents, using the given {@link IndexContext}.
	 * <p>
	 * Note that this is a writing operation, so the property has to
	 * {@link #exists(Object, IndexContext)} in the model.
	 * 
	 * @param model
	 *            The model to set the property on; might <b>NOT</b> be null.
	 * @param value
	 *            The value to set; might be null.
	 * @param context
	 *            The {@link IndexContext} that should be used to satisfy the listed
	 *            properties from the root property to this {@link ModelProperty};
	 *            might be null.
	 * @throws InterruptedPropertyPathException
	 *             If any property on the path to this {@link ModelProperty} is
	 *             null.
	 * @throws UnindexedPropertyPathException
	 *             If there is any listed property in this {@link ModelProperty}'s
	 *             path that does not have a {@link PropertyIndex} included in the
	 *             given {@link IndexContext}.
	 */
	public final void set(M model, T value, IndexContext context) {
		if (this.parent != null) {
			context = context == null ? DefaultIndexContext.EMPTY : context;
			castAndSet(model, value, context);
		}
	}

	@SuppressWarnings("unchecked")
	private <P> void castAndSet(M model, T value, IndexContext context) {
		P parentValue = ((ModelProperty<M, P>) this.parent).get(model, context);
		((IndexedSetter<P, T>) this.setter).set(parentValue, value, context);
	}

	/**
	 * Returns the hash code of this {@link ModelProperty}, which is the hash code
	 * of its id.
	 * 
	 * @return The hash code of this {@link ModelProperty}'s id.
	 */
	@Override
	public final int hashCode() {
		return name.hashCode();
	}

	/**
	 * Returns whether this {@link ModelProperty} is the exact same instance as the
	 * given {@link Object}.
	 * <p>
	 * The reason to handle this so strictly is that due to the generic nature and
	 * the {@link Getter}/{@link Setter} mechanic of {@link ModelProperty}s it can
	 * never be determined whether 2 {@link ModelProperty} instances actually refer
	 * to the same property in the same parent type.
	 * 
	 * @param obj
	 *            The {@link Object} to check against; might be null.
	 * @return True if the given object is the exact same as this
	 *         {@link ModelProperty}; false if not
	 */
	@Override
	public final boolean equals(Object obj) {
		return this == obj;
	}

	/**
	 * Returns the id of this {@link ModelProperty}, as that is the {@link String}
	 * an {@link ModelProperty} is identified by.
	 * 
	 * @return This {@link ModelProperty}'s id
	 */
	@Override
	public final String toString() {
		return this.name;
	}

	/**
	 * Creates a new model property root to start building a property tree with.
	 * 
	 * @param <M>
	 *            The root model type of the property tree the created
	 *            {@link DefiniteModelProperty} represents.
	 * @return A new {@link DefiniteModelProperty} without any parent that may
	 *         function as property tree root; never null
	 */
	public static <M> DefiniteModelProperty<M, M> rootChild() {
		return new DefiniteModelProperty<>(null);
	}

	/**
	 * Creates a new id'ed model property root to start building a property tree
	 * with.
	 * 
	 * @param <M>
	 *            The root model type of the property tree the created
	 *            {@link DefiniteModelProperty} represents.
	 * @param id
	 *            The id the returned root {@link DefiniteModelProperty} will be
	 *            identified by; might be null.
	 * @return A new {@link DefiniteModelProperty} without any parent that may
	 *         function as property tree root; never null
	 */
	public static <M> DefiniteModelProperty<M, M> rootChild(String id) {
		return new DefiniteModelProperty<>(id);
	}

	/**
	 * Creates a new id'ed model property list root to start building a property
	 * tree with.
	 * 
	 * @param <M>
	 *            The root model element type of the property tree the created
	 *            {@link ModelPropertyList} represents.
	 * @return A new {@link ModelPropertyList} without any parent that may function
	 *         as property tree root; never null
	 */
	public static <M> ModelPropertyList<List<M>, M> rootChildList() {
		return new ModelPropertyList<>(null);
	}

	/**
	 * Creates a new id'ed model property list root to start building a property
	 * tree with.
	 * 
	 * @param <M>
	 *            The root model element type of the property tree the created
	 *            {@link ModelPropertyList} represents.
	 * @param id
	 *            The id the returned root {@link ModelPropertyList} will be
	 *            identified by; might be null.
	 * @return A new {@link ModelPropertyList} without any parent that may function
	 *         as property tree root; never null
	 */
	public static <M> ModelPropertyList<List<M>, M> rootChildList(String id) {
		return new ModelPropertyList<>(id);
	}
}
