package com.mantledillusion.data.epiphy.context.io;

import com.mantledillusion.data.epiphy.Property;
import com.mantledillusion.data.epiphy.context.Context;
import com.mantledillusion.data.epiphy.context.reference.PropertyRoute;
import com.mantledillusion.data.epiphy.io.ReferencedGetter;
import com.mantledillusion.data.epiphy.io.ReferencedSetter;

public class NodeReferencedSetter<O, N> implements ReferencedSetter<O, N> {

    private final ReferencedGetter<O, N> getter;
    private final ReferencedSetter<O, N> setter;
    private final Property<N, N> property;

    private NodeReferencedSetter(ReferencedGetter<O, N> getter, ReferencedSetter<O, N> setter, Property<N, N> property) {
        this.getter = getter;
        this.setter = setter;
        this.property = property;
    }

    @Override
    public void set(Property<O, N> property, O object, N value, Context context) {
        if (context.containsReference(property, PropertyRoute.class)) {
            N node = this.getter.get(property, object, context, false);
            for (Context routeContext: context.getReference(property, PropertyRoute.class).getReference()) {
                node = this.property.get(node, routeContext, false);
            }
            this.property.set(node, value, context);
        } else {
            this.setter.set(property, object, value, context);
        }
    }

    public static <N> NodeReferencedSetter<N, N> from(Property<N, N> nodeRetriever) {
        return from((property, object, context, allowNull) -> nodeRetriever.get(object, context, allowNull),
                (property, object, value, context) -> nodeRetriever.set(object, value, context), nodeRetriever);
    }

    public static <O, N> NodeReferencedSetter<O, N> from(ReferencedGetter<O, N> getter, ReferencedSetter<O, N> setter, Property<N, N> nodeRetriever) {
        if (getter == null) {
            throw new IllegalArgumentException("Cannot create a property from a null getter");
        } else if (setter == null) {
            throw new IllegalArgumentException("Cannot create a property from a null setter");
        } else if (nodeRetriever == null) {
            throw new IllegalArgumentException("Cannot create a property from a null node retriever");
        }
        return new NodeReferencedSetter<>(getter, setter, nodeRetriever);
    }
}
