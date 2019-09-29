package com.mantledillusion.data.epiphy.context.io;

import com.mantledillusion.data.epiphy.NodeRetriever;
import com.mantledillusion.data.epiphy.Property;
import com.mantledillusion.data.epiphy.context.Context;
import com.mantledillusion.data.epiphy.context.reference.PropertyRoute;
import com.mantledillusion.data.epiphy.io.ReferencedGetter;
import com.mantledillusion.data.epiphy.io.ReferencedSetter;

public class NodeReferencedSetter<O, N> implements ReferencedSetter<O, N> {

    private final ReferencedGetter<O, N> getter;
    private final ReferencedSetter<O, N> setter;
    private final NodeRetriever<N> nodeRetriever;

    private NodeReferencedSetter(ReferencedGetter<O, N> getter, ReferencedSetter<O, N> setter, NodeRetriever<N> nodeRetriever) {
        this.getter = getter;
        this.setter = setter;
        this.nodeRetriever = nodeRetriever;
    }

    @Override
    public void set(Property<O, N> property, O object, N value, Context context) {
        if (context.containsReference(this.nodeRetriever, PropertyRoute.class)) {
            N node = this.getter.get(property, object, context, false);
            for (Context routeContext: context.getReference(this.nodeRetriever, PropertyRoute.class).getReference()) {
                node = this.nodeRetriever.get(node, routeContext, false);
            }
            this.nodeRetriever.set(node, value, context);
        } else {
            this.setter.set(property, object, value, context);
        }
    }

    @Override
    public boolean isWritable() {
        return true;
    }

    public static <N> NodeReferencedSetter<N, N> from(NodeRetriever<N> nodeRetriever) {
        return from(SelfReferencedGetter.from(), SelfReferencedSetter.from(nodeRetriever), nodeRetriever);
    }

    public static <O, N> NodeReferencedSetter<O, N> from(ReferencedGetter<O, N> getter, ReferencedSetter<O, N> setter, NodeRetriever<N> nodeRetriever) {
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
