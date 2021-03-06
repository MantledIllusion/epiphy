package com.mantledillusion.data.epiphy.context.io;

import com.mantledillusion.data.epiphy.NodeRetriever;
import com.mantledillusion.data.epiphy.Property;
import com.mantledillusion.data.epiphy.context.Context;
import com.mantledillusion.data.epiphy.context.TraversingMode;
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
                node = this.nodeRetriever.get(node, routeContext, allowNull);
            }
        }
        return node;
    }

    @Override
    public Property<?, ?> getParent() {
        return null;
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

    @Override
    public int occurrences(Property<O, N> property, O object) {
        return subOccurrences(this.getter.get(property, object, Context.EMPTY, false));
    }

    private int subOccurrences(N node) {
        return node == null ? 0 : 1 + this.nodeRetriever.contextualize(node).parallelStream().
                map(subContext -> subOccurrences(this.nodeRetriever.get(node, subContext, false))).
                reduce(0, Integer::sum);
    }

    @Override
    public Collection<Context> contextualize(Property<O, N> property, O object, Context context, TraversingMode traversingMode, boolean includeNull) {
        N node = get(property, object, context, false);
        if (node != null) {
            PropertyRoute baseRoute = context.containsReference(this.nodeRetriever, PropertyRoute.class) ?
                    context.getReference(this.nodeRetriever, PropertyRoute.class) : PropertyRoute.of(this.nodeRetriever);
            return subContextualize(node, baseRoute, context, traversingMode, includeNull).collect(Collectors.toList());
        } else {
            return Collections.emptyList();
        }
    }

    private Stream<Context> subContextualize(N node, PropertyRoute route, Context baseContext, TraversingMode traversingMode, boolean includeNull) {
        return Stream.concat(!traversingMode.isIncludeParent() ? Stream.empty() : Stream.of(baseContext.union(route)),
                !traversingMode.isIncludeChildren() ? Stream.empty() : this.nodeRetriever.contextualize(node, includeNull).stream().
                        flatMap(subContext -> {
                            PropertyRoute appendedRoute = route.append(subContext);
                            if (traversingMode == TraversingMode.RECURSIVE) {
                                N child = this.nodeRetriever.get(node, subContext, false);
                                return subContextualize(child, appendedRoute, baseContext, traversingMode, includeNull);
                            } else {
                                return Stream.of(baseContext.union(appendedRoute));
                            }
                        }));
    }

    @Override
    public Collection<Context> contextualize(Property<O, N> property, O object, N value, Context context) {
        N node = get(property, object, context, false);
        if (node != null) {
            PropertyRoute baseRoute = context.containsReference(this.nodeRetriever, PropertyRoute.class) ?
                    context.getReference(this.nodeRetriever, PropertyRoute.class) : PropertyRoute.of(this.nodeRetriever);
            return subContextualize(node, value, baseRoute, context).collect(Collectors.toList());
        } else {
            return Collections.emptyList();
        }
    }

    private Stream<Context> subContextualize(N node, N value, PropertyRoute route, Context baseContext) {
        return Stream.concat(Objects.equals(node, value) ? Stream.of(baseContext.union(route)) : Stream.empty(),
                this.nodeRetriever.contextualize(node, true).stream().
                flatMap(subContext -> {
                    PropertyRoute appendedRoute = route.append(subContext);
                    N child = this.nodeRetriever.get(node, subContext, false);
                    return subContextualize(child, value, appendedRoute, baseContext);
                }));
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
