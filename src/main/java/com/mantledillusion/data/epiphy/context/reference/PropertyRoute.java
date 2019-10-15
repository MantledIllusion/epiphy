package com.mantledillusion.data.epiphy.context.reference;

import com.mantledillusion.data.epiphy.NodeRetriever;
import com.mantledillusion.data.epiphy.Property;
import com.mantledillusion.data.epiphy.context.Context;

import java.util.Arrays;
import java.util.Objects;

/**
 * {@link PropertyReference} for node element {@link Property}s.
 */
public class PropertyRoute extends PropertyReference<NodeRetriever<?>, Context[]> {

    private PropertyRoute(NodeRetriever<?> property, Context... reference) {
        super(property, reference);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Arrays.hashCode(getReference());
        result = prime * result + getProperty().hashCode();
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
        if (!getProperty().equals(other.getProperty()))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "PropertyRoute [property=" + getProperty() + ", contexts=" + Arrays.toString(getReference()) + "]";
    }

    public PropertyRoute append(Context context) {
        Context[] currentReferences = getReference();
        Context[] references = Arrays.copyOf(currentReferences, currentReferences.length+1);
        references[currentReferences.length] = context;
        return new PropertyRoute(getProperty(), references);
    }

    /**
     * Creates a new {@link PropertyRoute}.
     *
     * @param <N>
     *          The type of the node.
     * @param nodeRetriever
     *            The node property this {@link PropertyRoute} contexts; might <b>not</b> be null.
     * @param contexts
     * 			The {@link Context}s of the given {@link Property}'s element; might <b>not</b> be null or contain
     * 			nulls, might be empty.
     * @return A new {@link PropertyRoute}, never null
     */
    public static <N> PropertyRoute of(NodeRetriever<N> nodeRetriever, Context... contexts) {
        if (nodeRetriever == null) {
            throw new IllegalArgumentException("Cannot create a route for a null property.");
        } else if (contexts == null || Arrays.stream(contexts).anyMatch(Objects::isNull)) {
            throw new IllegalArgumentException("Cannot create a route for a null context.");
        }
        return new PropertyRoute(nodeRetriever, contexts);
    }
}
