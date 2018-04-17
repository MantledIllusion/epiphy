package com.mantledillusion.data.epiphy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.mantledillusion.data.epiphy.index.IndexContext;
import com.mantledillusion.data.epiphy.interfaces.Property;
import com.mantledillusion.data.epiphy.interfaces.ReadableProperty;
import com.mantledillusion.data.epiphy.io.Getter;
import com.mantledillusion.data.epiphy.io.Setter;

abstract class AbstractModelProperty<M, T> implements ReadableProperty<M, T> {

	public static final String PROPERTY_ID_PATTERN = "[a-zA-Z0-9_-]+";
	public static final String PROPERTY_PATH_PATTERN = PROPERTY_ID_PATTERN + "(\\." + PROPERTY_ID_PATTERN + ")*";

	private final String id;
	private final String name;

	protected final AbstractModelProperty<M, ?> parent;
	protected final Map<String, Property<M, ?>> childrenByPaths = new HashMap<>();
	protected final Map<Property<M, ?>, String> pathsByChildren = new IdentityHashMap<>();

	private final List<Property<M, ?>> path;
	private final Set<Property<M, ?>> context;
	private final boolean isList;
	private final Set<Property<M, ?>> indices;

	<P> AbstractModelProperty(String id, AbstractModelProperty<M, P> parent, boolean isListed) {
		id = id == null ? String.valueOf(System.identityHashCode(this)) : id;
		if (!id.matches(PROPERTY_ID_PATTERN)) {
			throw new IllegalArgumentException("The property id '" + id + "' does not match the pattern '"
					+ PROPERTY_ID_PATTERN + "' for model property names.");
		}

		this.parent = parent;

		this.isList = isListed;

		if (this.parent == null) {
			this.id = id;
			this.name = id;
			this.path = Collections.singletonList(this);

			this.indices = Collections.emptySet();
		} else {
			this.id = id;
			this.name = parent.getName() + '.' + id;
			List<Property<M, ?>> path = new ArrayList<>(this.parent.path);
			path.addAll(Collections.singletonList(this));
			this.path = Collections.unmodifiableList(path);

			this.parent.addChild(id, this);

			Set<Property<M, ?>> indices = new HashSet<>(this.parent.indices);
			if (this.parent.isList()) {
				indices.add(this.parent);
			}
			this.indices = Collections.unmodifiableSet(indices);
		}
		this.context = Collections.unmodifiableSet(new HashSet<>(this.path));
	}

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
	public final boolean isList() {
		return isList;
	}

	@Override
	public final boolean isListed() {
		return parent.isList();
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
	public final boolean hasChildrenIn(M model) {
		return hasChildrenIn(model, null);
	}

	@Override
	public final Property<M, ?> getChild(String propertyPath) {
		if (propertyPath != null && !propertyPath.matches(PROPERTY_PATH_PATTERN)) {
			throw new IllegalArgumentException("The property path '" + propertyPath
					+ "' is not valid; a property path has to match " + PROPERTY_PATH_PATTERN);
		}
		return this.childrenByPaths.get(propertyPath);
	}

	@Override
	public final String getChildPath(Property<M, ?> child) {
		return this.pathsByChildren.get(child);
	}

	@Override
	public final Set<Property<M, ?>> getAllChildren() {
		return Collections.unmodifiableSet(new HashSet<>(this.childrenByPaths.values()));
	}

	@Override
	public final Set<String> getAllChildPaths() {
		return Collections.unmodifiableSet(this.childrenByPaths.keySet());
	}

	@Override
	public final List<Property<M, ?>> getPath() {
		return this.path;
	}

	@Override
	public final Set<Property<M, ?>> getContext() {
		return this.context;
	}

	@Override
	public final Set<Property<M, ?>> getIndices() {
		return this.indices;
	}

	@Override
	public final boolean exists(M model) {
		return exists(model, null);
	}

	@Override
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
	 * Returns the hash code of this {@link Property}, which is the
	 * hash code of its id.
	 * 
	 * @return The hash code of this {@link Property}'s id.
	 */
	@Override
	public final int hashCode() {
		return name.hashCode();
	}

	/**
	 * Returns whether this {@link Property} is the exact same
	 * instance as the given {@link Object}.
	 * <p>
	 * The reason to handle this so strictly is that due to the generic nature and
	 * the {@link Getter}/{@link Setter} mechanic of {@link Property}s
	 * it can never be determined whether 2 {@link Property} instances
	 * actually refer to the same property in the same parent type.
	 * 
	 * @param obj
	 *            The {@link Object} to check against; might be null.
	 * @return True if the given object is the exact same as this
	 *         {@link Property}; false if not
	 */
	@Override
	public final boolean equals(Object obj) {
		return this == obj;
	}

	/**
	 * Returns the id of this {@link Property}, as that is the {@link String} an
	 * {@link Property} is identified by.
	 * 
	 * @return This {@link Property}'s id
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
	 *            {@link ModelProperty} represents.
	 * @return A new {@link ModelProperty} without any parent that may function as
	 *         property tree root; never null
	 */
	public static <M> ModelProperty<M, M> rootChild() {
		return new ModelProperty<>(null);
	}

	/**
	 * Creates a new id'ed model property root to start building a property tree
	 * with.
	 * 
	 * @param <M>
	 *            The root model type of the property tree the created
	 *            {@link ModelProperty} represents.
	 * @param id
	 *            The id the returned root {@link ModelProperty} will be identified
	 *            by; might be null.
	 * @return A new {@link ModelProperty} without any parent that may function as
	 *         property tree root; never null
	 */
	public static <M> ModelProperty<M, M> rootChild(String id) {
		return new ModelProperty<>(id);
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
