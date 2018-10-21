package com.mantledillusion.data.epiphy.context;

import java.util.Arrays;

import com.mantledillusion.data.epiphy.interfaces.type.NodedProperty;

/**
 * Represents a single property index, which is basically an
 * {@link NodedProperty}-&gt;int[] pair.
 * <p>
 * {@link PropertyRoute}es can be created using the
 * {@link #of(NodedProperty, int, int...)} method.
 */
public final class PropertyRoute extends PropertyReference<NodedProperty<?, ?>, int[]> {

	private PropertyRoute(NodedProperty<?, ?> property, int[] key) {
		super(property, key);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(getReference());
		result = prime * result + ((getProperty() == null) ? 0 : getProperty().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PropertyRoute other = (PropertyRoute) obj;
		if (!Arrays.equals(getReference(), other.getReference()))
			return false;
		if (getProperty() == null) {
			if (other.getProperty() != null)
				return false;
		} else if (!getProperty().equals(other.getProperty()))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PropertyRoute [property=" + getProperty() + ", indices=" + Arrays.toString(getReference()) + "]";
	}

	/**
	 * Creates a new {@link PropertyRoute}.
	 * 
	 * @param nodedProperty
	 *            The noded property this {@link PropertyRoute} indexes; might
	 *            <b>not</b> be null.
	 * @param index
	 *            The index the given property has to have.
	 * @param indices
	 *            Additional indices the given property has to have.
	 * @return A new {@link PropertyRoute}; never null
	 */
	public static PropertyRoute of(NodedProperty<?, ?> nodedProperty, int index, int... indices) {
		if (nodedProperty == null) {
			throw new IllegalArgumentException("Cannot create a route for a null noded property.");
		}
		int[] path = new int[indices.length + 1];
		Arrays.setAll(path, i -> i == 0 ? index : indices[i - 1]);
		return new PropertyRoute(nodedProperty, path);
	}

	/**
	 * Creates a new {@link PropertyRoute}.
	 * 
	 * @param nodedProperty
	 *            The noded property this {@link PropertyRoute} indexes; might
	 *            <b>not</b> be null.
	 * @param indices
	 *            The indices the given property has to have.
	 * @return A new {@link PropertyRoute}; never null
	 */
	public static PropertyRoute of(NodedProperty<?, ?> nodedProperty, int[] indices) {
		if (nodedProperty == null) {
			throw new IllegalArgumentException("Cannot create a route for a null noded property.");
		} else if (indices == null || indices.length == 0) {
			throw new IllegalArgumentException("Cannot create a route for a null or empty route.");
		}
		return new PropertyRoute(nodedProperty, indices);
	}
}