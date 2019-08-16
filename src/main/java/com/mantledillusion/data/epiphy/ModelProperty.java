package com.mantledillusion.data.epiphy;

import com.mantledillusion.data.epiphy.context.io.*;
import com.mantledillusion.data.epiphy.io.Getter;
import com.mantledillusion.data.epiphy.io.ReferencedGetter;
import com.mantledillusion.data.epiphy.io.ReferencedSetter;
import com.mantledillusion.data.epiphy.io.Setter;

import java.util.List;

public class ModelProperty<O, V> extends AbstractModelProperty<O, V> {

    private ModelProperty(String id, ReferencedGetter<O, V> getter, ReferencedSetter<O, V> setter) {
        super(id, getter, setter);
    }

    // ###########################################################################################################
    // ################################################ PATHING ##################################################
    // ###########################################################################################################

    @Override
    public <S> ModelProperty<S, V> prepend(Property<S, O> parent) {
        return new ModelProperty<>(parent.getId()+'.'+getId(),
                PathReferencedGetter.from(parent, this, getGetter()),
                PathReferencedSetter.from(parent, this, getSetter()));
    }

    // ###########################################################################################################
    // ################################################ FACTORY ##################################################
    // ###########################################################################################################

    public static <O, V> ModelProperty<O, V> fromObject(Getter<O, V> getter) {
        return fromObject(null, getter);
    }

    public static <O, V> ModelProperty<O, V> fromObject(String id, Getter<O, V> getter) {
        return new ModelProperty<>(id, ObjectReferencedGetter.from(getter), ReadonlyReferencedSetter.from());
    }

    public static <O, V> ModelProperty<O, V> fromObject(Getter<O, V> getter, Setter<O, V> setter) {
        return fromObject(null, getter, setter);
    }

    public static <O, V> ModelProperty<O, V> fromObject(String id, Getter<O, V> getter, Setter<O, V> setter) {
        return new ModelProperty<>(id, ObjectReferencedGetter.from(getter), ObjectReferencedSetter.from(setter));
    }

    public static <E> ModelProperty<List<E>, E> fromList() {
        return new ModelProperty<>(null, ListReferencedGetter.from(), ListReferencedSetter.from());
    }

    public static <E> ModelProperty<List<E>, E> fromList(String id) {
        return new ModelProperty<>(id, ListReferencedGetter.from(), ListReferencedSetter.from());
    }
}
