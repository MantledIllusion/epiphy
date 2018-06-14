package com.mantledillusion.data.epiphy.interfaces.function;

/**
 * Interface for properties that are identifiable.
 */
public interface IdentifyableProperty {

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
}
