package com.mantledillusion.data.epiphy;

import com.mantledillusion.data.epiphy.context.io.*;
import com.mantledillusion.data.epiphy.io.Getter;
import com.mantledillusion.data.epiphy.io.ReferencedGetter;
import com.mantledillusion.data.epiphy.io.ReferencedSetter;
import com.mantledillusion.data.epiphy.io.Setter;

import java.util.List;

public class ModelPropertyNode<O, N> extends AbstractModelProperty<O, N> {

    private final Property<N, N> nodeRetriever;

    private ModelPropertyNode(String id, ReferencedGetter<O, N> getter, ReferencedSetter<O, N> setter,
                              Property<N, N> nodeRetriever) {
        super(id, getter, setter);
        this.nodeRetriever = nodeRetriever;
    }

    // ###########################################################################################################
    // ################################################ PATHING ##################################################
    // ###########################################################################################################

    public Property<N, N> getNodeRetriever() {
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
        return new ModelPropertyNode<>(id, NodeReferencedGetter.from(nodeRetriever),
                NodeReferencedSetter.from(nodeRetriever), nodeRetriever);
    }

    public static <O, N> ModelPropertyNode<O, N> fromObject(Getter<O, N> getter, ModelProperty<N, N> nodeRetriever) {
        return fromObject(null, getter, nodeRetriever);
    }

    public static <O, N> ModelPropertyNode<O, N> fromObject(String id, Getter<O, N> getter, ModelProperty<N, N> nodeRetriever) {
        return new ModelPropertyNode<>(id, NodeReferencedGetter.from(ObjectReferencedGetter.from(getter), nodeRetriever),
                ReadonlyReferencedSetter.from(), nodeRetriever);
    }

    public static <O, N> ModelPropertyNode<O, N> fromObject(Getter<O, N> getter, Setter<O, N> setter, ModelProperty<N, N> nodeRetriever) {
        return fromObject(null, getter, setter, nodeRetriever);
    }

    public static <O, N> ModelPropertyNode<O, N> fromObject(String id, Getter<O, N> getter, Setter<O, N> setter, ModelProperty<N, N> nodeRetriever) {
        return new ModelPropertyNode<>(id, NodeReferencedGetter.from(ObjectReferencedGetter.from(getter), nodeRetriever),
                NodeReferencedSetter.from(ObjectReferencedGetter.from(getter), ObjectReferencedSetter.from(setter), nodeRetriever), nodeRetriever);
    }

    public static <N> ModelPropertyNode<List<N>, N> fromList(ModelProperty<N, N> nodeRetriever) {
        return fromList(null, nodeRetriever);
    }

    public static <N> ModelPropertyNode<List<N>, N> fromList(String id, ModelProperty<N, N> nodeRetriever) {
        return new ModelPropertyNode<>(id, NodeReferencedGetter.from(ListReferencedGetter.from(), nodeRetriever),
                NodeReferencedSetter.from(ListReferencedGetter.from(), ListReferencedSetter.from(), nodeRetriever), nodeRetriever);
    }
}
