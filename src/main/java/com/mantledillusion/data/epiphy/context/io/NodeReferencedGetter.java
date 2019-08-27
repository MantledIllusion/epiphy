package com.mantledillusion.data.epiphy.context.io;

import com.mantledillusion.data.epiphy.Property;
import com.mantledillusion.data.epiphy.context.Context;
import com.mantledillusion.data.epiphy.context.reference.PropertyRoute;
import com.mantledillusion.data.epiphy.exception.InterruptedPropertyPathException;
import com.mantledillusion.data.epiphy.exception.OutboundPropertyPathException;
import com.mantledillusion.data.epiphy.exception.UnreferencedPropertyPathException;
import com.mantledillusion.data.epiphy.io.ReferencedGetter;

public class NodeReferencedGetter<O, N> implements ReferencedGetter<O, N> {

    private final ReferencedGetter<O, N> getter;
    private final Property<N, N> property;

    private NodeReferencedGetter(ReferencedGetter<O, N> getter, Property<N, N> property) {
        this.getter = getter;
        this.property = property;
    }

    @Override
    public N get(Property<O, N> property, O object, Context context, boolean allowNull)
            throws InterruptedPropertyPathException, UnreferencedPropertyPathException, OutboundPropertyPathException {
        N node = this.getter.get(property, object, context, allowNull);
        if (context.containsReference(property, PropertyRoute.class)) {
            for (Context routeContext: context.getReference(property, PropertyRoute.class).getReference()) {
                node = this.property.get(node, routeContext, false);
            }
        }
        return node;
    }

    public static <N> NodeReferencedGetter<N, N> from(Property<N, N> nodeRetriever) {
        return new NodeReferencedGetter<>(((property, object, context, allowNull) -> object), nodeRetriever);
    }

    public static <O, N> NodeReferencedGetter<O, N> from(ReferencedGetter<O, N> getter, Property<N, N> nodeRetriever) {
        if (getter == null) {
            throw new IllegalArgumentException("Cannot create a property from a null getter");
        } else if (nodeRetriever == null) {
            throw new IllegalArgumentException("Cannot create a property from a null node retriever");
        }
        return new NodeReferencedGetter<>(getter, nodeRetriever);
    }
}
