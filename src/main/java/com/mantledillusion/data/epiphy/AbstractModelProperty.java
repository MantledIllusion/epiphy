package com.mantledillusion.data.epiphy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.mantledillusion.data.epiphy.context.Context;
import com.mantledillusion.data.epiphy.interfaces.ReadableProperty;
import com.mantledillusion.data.epiphy.interfaces.function.ContextableProperty;
import com.mantledillusion.data.epiphy.io.Getter;
import com.mantledillusion.data.epiphy.io.Setter;

abstract class AbstractModelProperty<M, T> implements ReadableProperty<M, T> {

	public static final String PROPERTY_ID_PATTERN = "[a-zA-Z0-9_-]+";
	public static final String PROPERTY_PATH_PATTERN = PROPERTY_ID_PATTERN + "(\\." + PROPERTY_ID_PATTERN + ")*";

	private final String id;
	private final String name;

	protected final AbstractModelProperty<M, ?> parent;
	protected final Map<String, ReadableProperty<M, ?>> childrenByPaths = new HashMap<>();
	protected final Map<ReadableProperty<M, ?>, String> pathsByChildren = new IdentityHashMap<>();

	private final List<ReadableProperty<M, ?>> path;
	private final Set<ReadableProperty<M, ?>> parents;
	private final boolean isContexted;
	private final Set<ContextableProperty<M, ?, ?, ?>> context;

	@SuppressWarnings("unchecked")
	<P> AbstractModelProperty(String id, AbstractModelProperty<M, P> parent, boolean isListed) {
		id = id == null ? String.valueOf(System.identityHashCode(this)) : id;
		if (!id.matches(PROPERTY_ID_PATTERN)) {
			throw new IllegalArgumentException("The property id '" + id + "' does not match the pattern '"
					+ PROPERTY_ID_PATTERN + "' for model property names.");
		}

		this.parent = parent;

		Set<ContextableProperty<M, ?, ?, ?>> context;
		if (this.parent == null) {
			this.id = id;
			this.name = id;
			this.path = Collections.singletonList(this);
			this.parents = Collections.singleton(this);

			context = new HashSet<>();
		} else {
			this.id = id;
			this.name = parent.getName() + '.' + id;
			List<ReadableProperty<M, ?>> path = new ArrayList<>(this.parent.path);
			path.addAll(Collections.singletonList(this));
			this.path = Collections.unmodifiableList(path);
			this.parents = Collections.unmodifiableSet(new HashSet<>(path));

			this.parent.addChild(id, this);

			context = new HashSet<>(this.parent.context);
		}

		this.isContexted = ContextableProperty.class.isAssignableFrom(getClass());
		if (this.isContexted) {
			context.add((ContextableProperty<M, ?, ?, ?>) this);
		}
		this.context = Collections.unmodifiableSet(context);
	}

	// ###########################################################################################################
	// ############################################### INTERNAL ##################################################
	// ###########################################################################################################

	private <T2> void addChild(String id, AbstractModelProperty<M, T2> child) {
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

	// ###########################################################################################################
	// ############################################ FUNCTIONALITY ################################################
	// ###########################################################################################################

	@Override
	public String getId() {
		return id;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public final boolean isRoot() {
		return this.parent == null;
	}

	@Override
	public final AbstractModelProperty<M, ?> getParent() {
		return this.parent;
	}

	@Override
	public final boolean hasChildren() {
		return !this.childrenByPaths.isEmpty();
	}

	@Override
	public final ReadableProperty<M, ?> getChild(String propertyPath) {
		if (propertyPath != null && !propertyPath.matches(PROPERTY_PATH_PATTERN)) {
			throw new IllegalArgumentException("The property path '" + propertyPath
					+ "' is not valid; a property path has to match " + PROPERTY_PATH_PATTERN);
		}
		return this.childrenByPaths.get(propertyPath);
	}

	@Override
	public final String getChildPath(ReadableProperty<M, ?> child) {
		return this.pathsByChildren.get(child);
	}

	@Override
	public final Set<ReadableProperty<M, ?>> getAllChildren() {
		return Collections.unmodifiableSet(new HashSet<>(this.childrenByPaths.values()));
	}

	@Override
	public final Set<String> getAllChildPaths() {
		return Collections.unmodifiableSet(this.childrenByPaths.keySet());
	}

	@Override
	public final List<ReadableProperty<M, ?>> getPath() {
		return this.path;
	}
	
	@Override
	public Set<ReadableProperty<M, ?>> getParents() {
		return this.parents;
	}
	
	@Override
	public boolean isParent(ReadableProperty<M, ?> property) {
		return this.parents.contains(property);
	}
	
	@Override
	public boolean isContexted() {
		return this.isContexted;
	}
	
	@Override
	public Set<ContextableProperty<M, ?, ?, ?>> getContext() {
		return this.context;
	}

	@Override
	public final boolean exists(M model, Context context) {
		if (this.parent == null) {
			return true;
		} else if (this.parent.exists(model, context)) {
			return this.parent.hasChildrenIn(model, context);
		} else {
			return false;
		}
	}

	/**
	 * Returns the hash code of this {@link ReadableProperty}, which is the hash code of its
	 * id.
	 * 
	 * @return The hash code of this {@link ReadableProperty}'s id.
	 */
	@Override
	public final int hashCode() {
		return name.hashCode();
	}

	/**
	 * Returns whether this {@link ReadableProperty} is the exact same instance as the given
	 * {@link Object}.
	 * <p>
	 * The reason to handle this so strictly is that due to the generic nature and
	 * the {@link Getter}/{@link Setter} mechanic of {@link ReadableProperty}s it can never
	 * be determined whether 2 {@link ReadableProperty} instances actually refer to the same
	 * property in the same parent type.
	 * 
	 * @param obj
	 *            The {@link Object} to check against; might be null.
	 * @return True if the given object is the exact same as this {@link ReadableProperty};
	 *         false if not
	 */
	@Override
	public final boolean equals(Object obj) {
		return this == obj;
	}

	/**
	 * Returns the id of this {@link ReadableProperty}, as that is the {@link String} an
	 * {@link ReadableProperty} is identified by.
	 * 
	 * @return This {@link ReadableProperty}'s id
	 */
	@Override
	public final String toString() {
		return this.name;
	}

	// ###########################################################################################################
	// ############################################### CHILDREN ##################################################
	// ###########################################################################################################

	/**
	 * Creates a new model property root to start building a property tree with.
	 * <p>
	 * Is read-only because the model is always given to a property upon function
	 * execution, hence the root property representing the model cannot set it.
	 * 
	 * @param <M>
	 *            The root model type of the property tree the created
	 *            {@link ReadOnlyModelProperty} represents.
	 * @return A new {@link ReadOnlyModelProperty} without any parent that may
	 *         function as property tree root; never null
	 */
	public static <M> ReadOnlyModelProperty<M, M> rootChild() {
		return new ReadOnlyModelProperty<>(null);
	}

	/**
	 * Creates a new id'ed model property root to start building a property tree
	 * with.
	 * <p>
	 * Is read-only because the model is always given to a property upon function
	 * execution, hence the root property representing the model cannot set it.
	 * 
	 * @param <M>
	 *            The root model type of the property tree the created
	 *            {@link ReadOnlyModelProperty} represents.
	 * @param id
	 *            The id the returned root {@link ReadOnlyModelProperty} will be
	 *            identified by; might be null.
	 * @return A new {@link ReadOnlyModelProperty} without any parent that may
	 *         function as property tree root; never null
	 */
	public static <M> ReadOnlyModelProperty<M, M> rootChild(String id) {
		return new ReadOnlyModelProperty<>(id);
	}

	/**
	 * Creates a new id'ed model property list root to start building a property
	 * tree with.
	 * <p>
	 * Is read-only because the model is always given to a property upon function
	 * execution, hence the root property representing the model cannot set it.
	 * 
	 * @param <M>
	 *            The root model element type of the property tree the created
	 *            {@link ReadOnlyModelPropertyList} represents.
	 * @return A new {@link ReadOnlyModelPropertyList} without any parent that may
	 *         function as property tree root; never null
	 */
	public static <M> ReadOnlyModelPropertyList<List<M>, M> rootChildList() {
		return new ReadOnlyModelPropertyList<>(null);
	}

	/**
	 * Creates a new id'ed model property list root to start building a property
	 * tree with.
	 * <p>
	 * Is read-only because the model is always given to a property upon function
	 * execution, hence the root property representing the model cannot set it.
	 * 
	 * @param <M>
	 *            The root model element type of the property tree the created
	 *            {@link ReadOnlyModelPropertyList} represents.
	 * @param id
	 *            The id the returned root {@link ReadOnlyModelPropertyList} will be
	 *            identified by; might be null.
	 * @return A new {@link ReadOnlyModelPropertyList} without any parent that may
	 *         function as property tree root; never null
	 */
	public static <M> ReadOnlyModelPropertyList<List<M>, M> rootChildList(String id) {
		return new ReadOnlyModelPropertyList<>(id);
	}
}
