package com.mantledillusion.data.epiphy.interfaces.function;

import java.util.Set;

import com.mantledillusion.data.epiphy.context.Context;
import com.mantledillusion.data.epiphy.context.PropertyReference;
import com.mantledillusion.data.epiphy.interfaces.ReadableProperty;

/**
 * Interface for properties that are identifiable.
 */
public interface IdentifyableProperty<M> {

	/**
	 * The id this {@link IdentifyableProperty} is identified by in relation to its
	 * direct parent.
	 * <p>
	 * If not set to a specific value (or null) upon creation, this method returns
	 * this {@link IdentifyableProperty}'s objectId as provided by the JVM.
	 * 
	 * @return This {@link IdentifyableProperty}'s id; never null
	 */
	public String getId();

	/**
	 * The name this {@link IdentifyableProperty} is identified by in relation to
	 * the model's root property.
	 * <p>
	 * The name of a property is the {@link IdentifyableProperty}'s path from the
	 * model root property to the property itself.
	 * <p>
	 * A property path is a properties' id, appended to the id's of 0-n of it's
	 * parents, separated by the '.' character.
	 * 
	 * @return This {@link IdentifyableProperty}'s name; never null
	 */
	public String getName();

	/**
	 * Returns whether this {@link ReadableProperty} is the root of a property model
	 * (that is, the root of a tree structure of {@link ReadableProperty}s).
	 * 
	 * @return True if this {@link ReadableProperty} instance if a root to a
	 *         property tree; false if not
	 */
	public boolean isModelRoot();

	/**
	 * Returns the parent of this {@link ReadableProperty}.
	 * 
	 * @return The parent of this {@link ReadableProperty}; might return null if
	 *         this {@link ReadableProperty} is a root
	 */
	public ReadableProperty<M, ?> getParent();

	/**
	 * Returns whether the given {@link IdentifyableProperty} is this
	 * {@link ReadableProperty} or one of its parents, up to this property tree's
	 * root property.
	 * 
	 * @param property
	 *            The property to check; might be null.
	 * @return True if the given {@link ReadableProperty} is this
	 *         {@link ReadableProperty} or one of its parents, false otherwise
	 */
	public boolean isParent(IdentifyableProperty<M> property);

	/**
	 * Returns whether this {@link ReadableProperty} is a
	 * {@link ContextableProperty}, so its elements require an {@link PropertyReference}
	 * to be reached.
	 * 
	 * @return True if this {@link ReadableProperty} implements
	 *         {@link ContextableProperty}, false otherwise.
	 */
	public boolean isContexted();

	/**
	 * Returns a collapsed {@link Set} of this {@link ReadableProperty}'s parent
	 * properties that are {@link ContextableProperty}s.
	 * <p>
	 * In order words, all of the returned {@link ReadableProperty}s would need to
	 * be contexted by {@link PropertyReference}s so this {@link ReadableProperty} could
	 * be used to successfully identify the property in a model.
	 * <p>
	 * That being said, when applying this {@link ReadableProperty} on a model
	 * instance, all of the returned properties have to be included in a
	 * {@link PropertyReference} of the {@link Context} delivered to the operation.
	 * 
	 * @return An unmodifiable {@link Set} of all properties from the property
	 *         tree's root to this {@link ReadableProperty} that are listed; never
	 *         null
	 */
	public Set<ContextableProperty<M, ?, ?>> getContext();
}
