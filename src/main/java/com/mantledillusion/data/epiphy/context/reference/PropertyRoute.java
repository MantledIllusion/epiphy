package com.mantledillusion.data.epiphy.context.reference;

import com.mantledillusion.data.epiphy.Property;
import com.mantledillusion.data.epiphy.context.Context;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class PropertyRoute extends PropertyReference<Property<?, ?>, Context[]> {

    private PropertyRoute(Property<?, ?> property, Context... reference) {
        super(property, reference);
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

    public PropertyRoute append(Context context) {
        Context[] currentReferences = getReference();
        Context[] references = Arrays.copyOf(currentReferences, currentReferences.length+1);
        references[currentReferences.length] = context;
        return new PropertyRoute(getProperty(), references);
    }

    /**
     * Creates a new {@link PropertyRoute}.
     * <p>
     * <b>NOTE:</b> The property route is created using the node receiver property, not the node property itself!
     *
     * @param <N> The node type of
     * @param property
     *            The listed property this {@link PropertyIndex} indexes; might
     *            <b>not</b> be null.
     * @param contexts
     *            The contexts the given property has to have.
     * @return A new {@link PropertyRoute}, never null
     */
    public static <N> PropertyRoute of(Property<N, N> property, Context... contexts) {
        if (property == null) {
            throw new IllegalArgumentException("Cannot create a route for a null property.");
        } else if (contexts == null || Arrays.stream(contexts).anyMatch(Objects::isNull)) {
            throw new IllegalArgumentException("Cannot create a route for a null context.");
        }
        return new PropertyRoute(property, contexts);
    }
}
