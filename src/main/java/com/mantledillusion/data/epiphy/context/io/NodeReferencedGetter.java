package com.mantledillusion.data.epiphy.context.io;

import com.mantledillusion.data.epiphy.NodeRetriever;
import com.mantledillusion.data.epiphy.Property;
import com.mantledillusion.data.epiphy.context.Context;
import com.mantledillusion.data.epiphy.context.reference.PropertyRoute;
import com.mantledillusion.data.epiphy.exception.InterruptedPropertyPathException;
import com.mantledillusion.data.epiphy.exception.OutboundPropertyPathException;
import com.mantledillusion.data.epiphy.exception.UnreferencedPropertyPathException;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class NodeReferencedGetter<O, N> implements ReferencedGetter<O, N> {

    private final ReferencedGetter<O, N> getter;
    private final NodeRetriever<N> nodeRetriever;

    private Set<Property<?, ?>> hierarchy;

    private NodeReferencedGetter(ReferencedGetter<O, N> getter, NodeRetriever<N> nodeRetriever) {
        this.getter = getter;
        this.nodeRetriever = nodeRetriever;
    }

    @Override
    public N get(Property<O, N> property, O object, Context context, boolean allowNull)
            throws InterruptedPropertyPathException, UnreferencedPropertyPathException, OutboundPropertyPathException {
        N node = this.getter.get(property, object, context, allowNull);
        if (context.containsReference(this.nodeRetriever, PropertyRoute.class)) {
            for (Context routeContext: context.getReference(this.nodeRetriever, PropertyRoute.class).getReference()) {
                node = this.nodeRetriever.get(node, routeContext, false);
            }
        }
        return node;
    }

    @Override
    public int occurrences(Property<O, N> property, O object) {
        N node = this.getter.get(property, object, Context.EMPTY, false);
        return node == null ? 0 : 1 + this.nodeRetriever.occurrences(node);
    }

    @Override
    public Collection<Context> contextualize(Property<O, N> property, O object) {
        N node = this.getter.get(property, object, Context.EMPTY, false);
        Set<Context> contexts = new HashSet<>();
        if (node != null) {
            PropertyRoute baseRoute = PropertyRoute.of(this.nodeRetriever);
            contexts.add(Context.of(baseRoute));
            contexts.addAll(subContextualize(node, baseRoute).collect(Collectors.toSet()));
        }
        return contexts;
    }

    private Stream<Context> subContextualize(N node, PropertyRoute route) {
        return this.nodeRetriever.contextualize(node).parallelStream().
                flatMap(subContext -> {
                    PropertyRoute appendedRoute = route.append(subContext);
                    N child = this.nodeRetriever.get(node, subContext, false);
                    return Stream.concat(
                            Stream.of(Context.of(appendedRoute)),
                            subContextualize(child, appendedRoute)
                    );
                });
    }

    @Override
    public Set<Property<?, ?>> getHierarchy(Property<O, N> property) {
        if (this.hierarchy == null) {
            this.hierarchy = new HashSet<>(this.getter.getHierarchy(property));
            this.hierarchy.addAll(this.nodeRetriever.getHierarchy());
            this.hierarchy = Collections.unmodifiableSet(this.hierarchy);
        }
        return this.hierarchy;
    }

    public static <N> NodeReferencedGetter<N, N> from(NodeRetriever<N> nodeRetriever) {
        return from(SelfReferencedGetter.from(), nodeRetriever);
    }

    public static <O, N> NodeReferencedGetter<O, N> from(ReferencedGetter<O, N> getter, NodeRetriever<N> nodeRetriever) {
        if (getter == null) {
            throw new IllegalArgumentException("Cannot create a property from a null getter");
        } else if (nodeRetriever == null) {
            throw new IllegalArgumentException("Cannot create a property from a null node retriever");
        }
        return new NodeReferencedGetter<>(getter, nodeRetriever);
    }
}
