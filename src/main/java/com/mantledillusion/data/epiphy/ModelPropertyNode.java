package com.mantledillusion.data.epiphy;

import com.mantledillusion.data.epiphy.context.io.*;
import com.mantledillusion.data.epiphy.io.Getter;
import com.mantledillusion.data.epiphy.io.ReferencedGetter;
import com.mantledillusion.data.epiphy.io.ReferencedSetter;
import com.mantledillusion.data.epiphy.io.Setter;

import java.util.List;

public class ModelPropertyNode<O, N> extends AbstractModelProperty<O, N> {

    private ModelPropertyNode(String id, ReferencedGetter<O, N> getter, ReferencedSetter<O, N> setter) {
        super(id, getter, setter);
    }

    // ###########################################################################################################
    // ################################################ PATHING ##################################################
    // ###########################################################################################################

    @Override
    public <S> Property<S, N> prepend(Property<S, O> parent) {
        return new ModelPropertyNode<>(parent.getId()+'.'+getId(),
                PathReferencedGetter.from(parent, this, getGetter()),
                PathReferencedSetter.from(parent, this, getSetter()));
    }

    // ###########################################################################################################
    // ################################################ FACTORY ##################################################
    // ###########################################################################################################

    public static <N> ModelPropertyNode<N, N> from(Property<N, N> nodeRetriever) {
        return from(null, nodeRetriever);
    }

    public static <N> ModelPropertyNode<N, N> from(String id, Property<N, N> nodeRetriever) {
        return new ModelPropertyNode<>(id, NodeReferencedGetter.from(nodeRetriever),
                NodeReferencedSetter.from(nodeRetriever));
    }

    public static <O, N> ModelPropertyNode<O, N> fromObject(Getter<O, N> getter, Property<N, N> nodeRetriever) {
        return fromObject(null, getter, nodeRetriever);
    }

    public static <O, N> ModelPropertyNode<O, N> fromObject(String id, Getter<O, N> getter, Property<N, N> nodeRetriever) {
        return new ModelPropertyNode<>(id, NodeReferencedGetter.from(ObjectReferencedGetter.from(getter), nodeRetriever),
                ReadonlyReferencedSetter.from());
    }

    public static <O, N> ModelPropertyNode<O, N> fromObject(Getter<O, N> getter, Setter<O, N> setter, Property<N, N> nodeRetriever) {
        return fromObject(null, getter, setter, nodeRetriever);
    }

    public static <O, N> ModelPropertyNode<O, N> fromObject(String id, Getter<O, N> getter, Setter<O, N> setter, Property<N, N> nodeRetriever) {
        return new ModelPropertyNode<>(id, NodeReferencedGetter.from(ObjectReferencedGetter.from(getter), nodeRetriever),
                NodeReferencedSetter.from(ObjectReferencedGetter.from(getter), ObjectReferencedSetter.from(setter), nodeRetriever));
    }

    public static <N> ModelPropertyNode<List<N>, N> fromList(Property<N, N> nodeRetriever) {
        return fromList(null, nodeRetriever);
    }

    public static <N> ModelPropertyNode<List<N>, N> fromList(String id, Property<N, N> nodeRetriever) {
        return new ModelPropertyNode<>(id, NodeReferencedGetter.from(ListReferencedGetter.from(), nodeRetriever),
                NodeReferencedSetter.from(ListReferencedGetter.from(), ListReferencedSetter.from(), nodeRetriever));
    }
}
