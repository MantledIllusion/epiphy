package com.mantledillusion.data.epiphy.context.io;

import com.mantledillusion.data.epiphy.Property;
import com.mantledillusion.data.epiphy.context.Context;

import java.util.*;
import java.util.stream.Collectors;

public class PathReferencedGetter<S, O, V> implements ReferencedGetter<S, V> {

    private final Property<S, O> parent;
    private final Property<O, V> child;
    private final ReferencedGetter<O, V> getter;
    private final Set<Property<?, ?>> hierarchy;

    private PathReferencedGetter(Property<S, O> parent, Property<O, V> child, ReferencedGetter<O, V> getter) {
        this.parent = parent;
        this.child = child;
        this.getter = getter;

        Set<Property<?, ?>> parentHierarchy = parent.getHierarchy();
        Set<Property<?, ?>> childHierarchy = child.getHierarchy();
        if (parentHierarchy.parallelStream().anyMatch(childHierarchy::contains)) {
            throw new IllegalArgumentException("The property "+parent+" contains at least one property in its " +
                    "hierarchy that is also contained by the property "+child+"; creating a path using these two " +
                    "would create an infinite loop.");
        }
        Set<Property<?, ?>> hierarchy = new HashSet<>(parentHierarchy);
        hierarchy.addAll(childHierarchy);
        this.hierarchy = Collections.unmodifiableSet(hierarchy);
    }

    @Override
    public V get(Property<S, V> property, S object, Context context, boolean allowNull) {
        return this.getter.get(this.child, this.parent.get(object, context, allowNull), context, allowNull);
    }

    @Override
    public Property<?, ?> getParent() {
        return this.parent;
    }

    @Override
    public Set<Property<?, ?>> getHierarchy(Property<S, V> property) {
        return this.hierarchy;
    }

    @Override
    public int occurrences(Property<S, V> property, S object) {
        return this.parent.stream(object).
                map(intermediate -> this.child.occurrences(intermediate)).
                collect(Collectors.summingInt(Integer::intValue));
    }

    @Override
    public Collection<Context> contextualize(Property<S, V> property, S object, Context context, boolean includeNull) {
        return this.parent.contextualize(object, context, false).stream().
                flatMap(parentContext ->
                        this.child.contextualize(this.parent.get(object, parentContext), parentContext, includeNull).
                        stream().map(childContext -> parentContext.union(childContext))).
                collect(Collectors.toList());
    }

    @Override
    public Collection<Context> contextualize(Property<S, V> property, S object, V value, Context context) {
        return this.parent.contextualize(object, context, false).stream().
                flatMap(parentContext -> {
                    O parent = this.parent.get(object, parentContext);
                    return this.child.contextualize(parent, parentContext, true).stream().
                        filter(childContext -> Objects.equals(this.child.get(parent, childContext, false), value)).
                        map(childContext -> parentContext.union(childContext));
                }).
                collect(Collectors.toList());
    }

    public static <S, O, V> PathReferencedGetter<S, O, V> from(Property<S, O> parent, Property<O, V> child, ReferencedGetter<O, V> getter) {
        return new PathReferencedGetter<>(parent, child, getter);
    }
}
