package com.mantledillusion.data.epiphy;

import com.mantledillusion.data.epiphy.context.Context;
import com.mantledillusion.data.epiphy.context.io.*;
import com.mantledillusion.data.epiphy.context.io.ReferencedGetter;
import com.mantledillusion.data.epiphy.context.io.ReferencedSetter;

import java.util.Collection;
import java.util.List;
import java.util.Set;

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
        public Collection<Context> contextualize(N object) {
            return this.contextualize(object);
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

    public static <N> ModelPropertyNode<N, N> from(ModelProperty<N, N> nodeRetriever) {
        return from(null, nodeRetriever);
    }

    public static <N> ModelPropertyNode<N, N> from(String id, ModelProperty<N, N> nodeRetriever) {
        NodeRetriever<N> retriever = new ModelPropertyNodeRetriever<>(nodeRetriever);
        return new ModelPropertyNode<>(id, NodeReferencedGetter.from(retriever),
                NodeReferencedSetter.from(retriever), retriever);
    }

    public static <O, N> ModelPropertyNode<O, N> fromObject(Getter<O, N> getter, ModelProperty<N, N> nodeRetriever) {
        return fromObject(null, getter, nodeRetriever);
    }

    public static <O, N> ModelPropertyNode<O, N> fromObject(String id, Getter<O, N> getter, ModelProperty<N, N> nodeRetriever) {
        NodeRetriever<N> retriever = new ModelPropertyNodeRetriever<>(nodeRetriever);
        return new ModelPropertyNode<>(id, NodeReferencedGetter.from(ObjectReferencedGetter.from(getter), retriever),
                ReadonlyReferencedSetter.from(), retriever);
    }

    public static <O, N> ModelPropertyNode<O, N> fromObject(Getter<O, N> getter, Setter<O, N> setter, ModelProperty<N, N> nodeRetriever) {
        return fromObject(null, getter, setter, nodeRetriever);
    }

    public static <O, N> ModelPropertyNode<O, N> fromObject(String id, Getter<O, N> getter, Setter<O, N> setter, ModelProperty<N, N> nodeRetriever) {
        NodeRetriever<N> retriever = new ModelPropertyNodeRetriever<>(nodeRetriever);
        return new ModelPropertyNode<>(id, NodeReferencedGetter.from(ObjectReferencedGetter.from(getter), retriever),
                NodeReferencedSetter.from(ObjectReferencedGetter.from(getter), ObjectReferencedSetter.from(setter), retriever), retriever);
    }

    public static <N> ModelPropertyNode<List<N>, N> fromList(ModelProperty<N, N> nodeRetriever) {
        return fromList(null, nodeRetriever);
    }

    public static <N> ModelPropertyNode<List<N>, N> fromList(String id, ModelProperty<N, N> nodeRetriever) {
        NodeRetriever<N> retriever = new ModelPropertyNodeRetriever<>(nodeRetriever);
        return new ModelPropertyNode<>(id, NodeReferencedGetter.from(ListReferencedGetter.from(), retriever),
                NodeReferencedSetter.from(ListReferencedGetter.from(), ListReferencedSetter.from(), retriever), retriever);
    }
}
