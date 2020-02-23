package com.mantledillusion.data.epiphy;

import com.mantledillusion.data.epiphy.context.Context;
import com.mantledillusion.data.epiphy.context.io.*;
import com.mantledillusion.data.epiphy.context.io.ReferencedGetter;
import com.mantledillusion.data.epiphy.context.io.ReferencedSetter;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Represents a {@link Property} whose value is a node of a tree.
 * <p>
 * Instantiable using the <code>from...()</code> methods.
 *
 * @param <O>
 *          The parent object type of this {@link Property}.
 * @param <N>
 *          The type of the child value this {@link Property} represents.
 */
public class ModelPropertyNode<O, N> extends AbstractModelProperty<O, N> {

    private static class ModelPropertyNodeRetriever<N> implements NodeRetriever<N> {

        private final Property<N, N> nodeRetriever;

        private ModelPropertyNodeRetriever(Property<N, N> nodeRetriever) {
            this.nodeRetriever = nodeRetriever;
        }

        @Override
        public String getId() {
            return this.nodeRetriever.getId();
        }

        @Override
        public boolean exists(N object, Context context) {
            return this.nodeRetriever.exists(object, context);
        }

        @Override
        public boolean isWritable() {
            return this.nodeRetriever.isWritable();
        }

        @Override
        public N get(N object, Context context, boolean allowNull) {
            return this.nodeRetriever.get(object, context, allowNull);
        }

        @Override
        public void set(N object, N value, Context context) {
            this.nodeRetriever.set(object, value, context);
        }

        @Override
        public Property<?, ?> getParent() {
            return this.nodeRetriever.getParent();
        }

        @Override
        public Set<Property<?, ?>> getHierarchy() {
            return this.nodeRetriever.getHierarchy();
        }

        @Override
        public <S> Property<S, N> prepend(Property<S, N> parent) {
            return this.nodeRetriever.prepend(parent);
        }

        @Override
        public int occurrences(N object) {
            return this.nodeRetriever.occurrences(object);
        }

        @Override
        public Collection<Context> contextualize(N object, Context context, boolean includeNull) {
            return this.nodeRetriever.contextualize(object, context, includeNull);
        }

        @Override
        public Collection<Context> contextualize(N object, N value, Context context) {
            return this.nodeRetriever.contextualize(object, value, context);
        }
    }

    private final NodeRetriever<N> nodeRetriever;

    private ModelPropertyNode(String id, ReferencedGetter<O, N> getter, ReferencedSetter<O, N> setter,
                              NodeRetriever<N> nodeRetriever) {
        super(id, getter, setter);
        this.nodeRetriever = nodeRetriever;
    }

    // ###########################################################################################################
    // ################################################ PATHING ##################################################
    // ###########################################################################################################

    /**
     * Returns the {@link NodeRetriever} {@link Property} that enables this {@link Property} to access the child node
     * of a parent node.
     *
     * @return The {@link NodeRetriever}, never null
     */
    public NodeRetriever<N> getNodeRetriever() {
        return nodeRetriever;
    }

    @Override
    public <S> Property<S, N> prepend(Property<S, O> parent) {
        return new ModelPropertyNode<>(parent.getId()+'.'+getId(),
                PathReferencedGetter.from(parent, this, getGetter()),
                PathReferencedSetter.from(parent, this, getSetter()),
                this.nodeRetriever);
    }

    // ###########################################################################################################
    // ################################################ FACTORY ##################################################
    // ###########################################################################################################

    /**
     * Factory method for a {@link Property} that resides in an {@link Object} that is equal to the value type, so it
     * enables traversing through a node tree.
     *
     * @param <N>
     *          The type of the child value the {@link Property} represents.
     * @param nodeRetriever
     *          A {@link ModelProperty} that whose object and value type are the same, so it is able to access the
     *          child (value) of a parent (object) node; might <b>not</b> be null.
     * @return
     *          A new instance, never null
     */
    public static <N> ModelPropertyNode<N, N> from(ModelProperty<N, N> nodeRetriever) {
        return from(null, nodeRetriever);
    }

    /**
     * Factory method for a {@link Property} that resides in an {@link Object} that is equal to the value type, so it
     * enables traversing through a node tree.
     *
     * @param <N>
     *          The type of the child value the {@link Property} represents.
     * @param id
     *          The identifier of the {@link Property}; might be null, then the object id is used.
     * @param nodeRetriever
     *          A {@link ModelProperty} that whose object and value type are the same, so it is able to access the
     *          child (value) of a parent (object) node; might <b>not</b> be null.
     * @return
     *          A new instance, never null
     */
    public static <N> ModelPropertyNode<N, N> from(String id, ModelProperty<N, N> nodeRetriever) {
        NodeRetriever<N> retriever = new ModelPropertyNodeRetriever<>(nodeRetriever);
        return new ModelPropertyNode<>(id, NodeReferencedGetter.from(retriever),
                NodeReferencedSetter.from(retriever), retriever);
    }

    /**
     * Factory method for a {@link Property} that resides in an {@link Object} and whose value type represents a node
     * that enables traversing through a node tree.
     * <p>
     * Creates a read-only ({@link Property#isWritable()} == false) {@link Property} since no {@link Setter} is involved.
     *
     * @param <O>
     *          The parent object type of the {@link Property}.
     * @param <N>
     *          The type of the child value the {@link Property} represents.
     * @param getter
     *          A function that is able to retrieve the value from its parent object; might <b>not</b> be null.
     * @param nodeRetriever
     *          A {@link ModelProperty} that whose object and value type are the same, so it is able to access the
     *          child (value) of a parent (object) node; might <b>not</b> be null.
     * @return
     *          A new instance, never null
     */
    public static <O, N> ModelPropertyNode<O, N> fromObject(Getter<O, N> getter, ModelProperty<N, N> nodeRetriever) {
        return fromObject(null, getter, nodeRetriever);
    }

    /**
     * Factory method for a {@link Property} that resides in an {@link Object} and whose value type represents a node
     * that enables traversing through a node tree.
     * <p>
     * Creates a read-only ({@link Property#isWritable()} == false) {@link Property} since no {@link Setter} is involved.
     *
     * @param <O>
     *          The parent object type of the {@link Property}.
     * @param <N>
     *          The type of the child value the {@link Property} represents.
     * @param id
     *          The identifier of the {@link Property}; might be null, then the object id is used.
     * @param getter
     *          A function that is able to retrieve the value from its parent object; might <b>not</b> be null.
     * @param nodeRetriever
     *          A {@link ModelProperty} that whose object and value type are the same, so it is able to access the
     *          child (value) of a parent (object) node; might <b>not</b> be null.
     * @return
     *          A new instance, never null
     */
    public static <O, N> ModelPropertyNode<O, N> fromObject(String id, Getter<O, N> getter, ModelProperty<N, N> nodeRetriever) {
        NodeRetriever<N> retriever = new ModelPropertyNodeRetriever<>(nodeRetriever);
        return new ModelPropertyNode<>(id, NodeReferencedGetter.from(ObjectReferencedGetter.from(getter), retriever),
                ReadonlyReferencedSetter.from(), retriever);
    }

    /**
     * Factory method for a {@link Property} that resides in an {@link Object} and whose value type represents a node
     * that enables traversing through a node tree.
     * <p>
     * Creates a read-only ({@link Property#isWritable()} == false) {@link Property} since no {@link Setter} is involved.
     *
     * @param <O>
     *          The parent object type of the {@link Property}.
     * @param <N>
     *          The type of the child value the {@link Property} represents.
     * @param getter
     *          A function that is able to retrieve the value from its parent object; might <b>not</b> be null.
     * @param setter
     *          A function that is able to write a value to its parent object; might <b>not</b> be null.
     * @param nodeRetriever
     *          A {@link ModelProperty} that whose object and value type are the same, so it is able to access the
     *          child (value) of a parent (object) node; might <b>not</b> be null.
     * @return
     *          A new instance, never null
     */
    public static <O, N> ModelPropertyNode<O, N> fromObject(Getter<O, N> getter, Setter<O, N> setter, ModelProperty<N, N> nodeRetriever) {
        return fromObject(null, getter, setter, nodeRetriever);
    }

    /**
     * Factory method for a {@link Property} that resides in an {@link Object} and whose value type represents a node
     * that enables traversing through a node tree.
     * <p>
     * Creates a read-only ({@link Property#isWritable()} == false) {@link Property} since no {@link Setter} is involved.
     *
     * @param <O>
     *          The parent object type of the {@link Property}.
     * @param <N>
     *          The type of the child value the {@link Property} represents.
     * @param id
     *          The identifier of the {@link Property}; might be null, then the object id is used.
     * @param getter
     *          A function that is able to retrieve the value from its parent object; might <b>not</b> be null.
     * @param setter
     *          A function that is able to write a value to its parent object; might <b>not</b> be null.
     * @param nodeRetriever
     *          A {@link ModelProperty} that whose object and value type are the same, so it is able to access the
     *          child (value) of a parent (object) node; might <b>not</b> be null.
     * @return
     *          A new instance, never null
     */
    public static <O, N> ModelPropertyNode<O, N> fromObject(String id, Getter<O, N> getter, Setter<O, N> setter, ModelProperty<N, N> nodeRetriever) {
        NodeRetriever<N> retriever = new ModelPropertyNodeRetriever<>(nodeRetriever);
        return new ModelPropertyNode<>(id, NodeReferencedGetter.from(ObjectReferencedGetter.from(getter), retriever),
                NodeReferencedSetter.from(ObjectReferencedGetter.from(getter), ObjectReferencedSetter.from(setter), retriever), retriever);
    }

    /**
     * Factory method for a listed {@link Property} that resides in a {@link List} and whose value type represents a
     * node that enables traversing through a node tree.
     *
     * @param <N>
     *          The element type of the {@link List}.
     * @param nodeRetriever
     *          A {@link ModelProperty} that whose object and value type are the same, so it is able to access the
     *          child (value) of a parent (object) node; might <b>not</b> be null.
     * @return
     *          A new instance, never null
     */
    public static <N> ModelPropertyNode<List<N>, N> fromList(ModelProperty<N, N> nodeRetriever) {
        return fromList(null, nodeRetriever);
    }

    /**
     * Factory method for a listed {@link Property} that resides in a {@link List} and whose value type represents a
     * node that enables traversing through a node tree.
     *
     * @param <N>
     *          The element type of the {@link List}.
     * @param id
     *          The identifier of the {@link Property}; might be null, then the object id is used.
     * @param nodeRetriever
     *          A {@link ModelProperty} that whose object and value type are the same, so it is able to access the
     *          child (value) of a parent (object) node; might <b>not</b> be null.
     * @return
     *          A new instance, never null
     */
    public static <N> ModelPropertyNode<List<N>, N> fromList(String id, ModelProperty<N, N> nodeRetriever) {
        NodeRetriever<N> retriever = new ModelPropertyNodeRetriever<>(nodeRetriever);
        return new ModelPropertyNode<>(id, NodeReferencedGetter.from(ListReferencedGetter.from(), retriever),
                NodeReferencedSetter.from(ListReferencedGetter.from(), ListReferencedSetter.from(), retriever), retriever);
    }

    /**
     * Factory method for a listed {@link Property} that resides in a {@link Set} and whose value type represents a
     * node that enables traversing through a node tree.
     *
     * @param <N>
     *          The element type of the {@link Set}.
     * @param nodeRetriever
     *          A {@link ModelProperty} that whose object and value type are the same, so it is able to access the
     *          child (value) of a parent (object) node; might <b>not</b> be null.
     * @return
     *          A new instance, never null
     */
    public static <N> ModelPropertyNode<Set<N>, N> fromSet(ModelProperty<N, N> nodeRetriever) {
        return fromSet(null, nodeRetriever);
    }

    /**
     * Factory method for a listed {@link Property} that resides in a {@link Set} and whose value type represents a
     * node that enables traversing through a node tree.
     *
     * @param <N>
     *          The element type of the {@link Set}.
     * @param id
     *          The identifier of the {@link Property}; might be null, then the object id is used.
     * @param nodeRetriever
     *          A {@link ModelProperty} that whose object and value type are the same, so it is able to access the
     *          child (value) of a parent (object) node; might <b>not</b> be null.
     * @return
     *          A new instance, never null
     */
    public static <N> ModelPropertyNode<Set<N>, N> fromSet(String id, ModelProperty<N, N> nodeRetriever) {
        NodeRetriever<N> retriever = new ModelPropertyNodeRetriever<>(nodeRetriever);
        return new ModelPropertyNode<>(id, NodeReferencedGetter.from(SetReferencedGetter.from(), retriever),
                NodeReferencedSetter.from(SetReferencedGetter.from(), SetReferencedSetter.from(), retriever), retriever);
    }

    /**
     * Factory method for a listed {@link Property} that resides in a {@link Map} and whose value type represents a
     * node that enables traversing through a node tree.
     *
     * @param <K>
     *          The key type of the map.
     * @param <N>
     *          The element type of the map.
     * @param nodeRetriever
     *          A {@link ModelProperty} that whose object and value type are the same, so it is able to access the
     *          child (value) of a parent (object) node; might <b>not</b> be null.
     * @return
     *          A new instance, never null
     */
    public static <K, N> ModelPropertyNode<Map<K, N>, N> fromMap(ModelProperty<N, N> nodeRetriever) {
        return fromMap(null, nodeRetriever);
    }

    /**
     * Factory method for a listed {@link Property} that resides in a {@link Map} and whose value type represents a
     * node that enables traversing through a node tree.
     *
     * @param <K>
     *          The key type of the map.
     * @param <N>
     *          The element type of the map.
     * @param id
     *          The identifier of the {@link Property}; might be null, then the object id is used.
     * @param nodeRetriever
     *          A {@link ModelProperty} that whose object and value type are the same, so it is able to access the
     *          child (value) of a parent (object) node; might <b>not</b> be null.
     * @return
     *          A new instance, never null
     */
    public static <K, N> ModelPropertyNode<Map<K, N>, N> fromMap(String id, ModelProperty<N, N> nodeRetriever) {
        NodeRetriever<N> retriever = new ModelPropertyNodeRetriever<>(nodeRetriever);
        return new ModelPropertyNode<>(id, NodeReferencedGetter.from(MapReferencedGetter.from(), retriever),
                NodeReferencedSetter.from(MapReferencedGetter.from(), MapReferencedSetter.from(), retriever), retriever);
    }
}
