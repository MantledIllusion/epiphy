package com.mantledillusion.data.epiphy;

import com.mantledillusion.data.epiphy.context.function.*;
import com.mantledillusion.data.epiphy.context.io.ReferencedGetter;
import com.mantledillusion.data.epiphy.context.io.ReferencedSetter;
import com.mantledillusion.data.epiphy.context.Context;
import com.mantledillusion.data.epiphy.context.reference.ReferencedValue;
import com.mantledillusion.data.epiphy.context.io.*;
import com.mantledillusion.data.epiphy.exception.InterruptedPropertyPathException;
import com.mantledillusion.data.epiphy.exception.OutboundPropertyPathException;

import java.util.List;

public class ModelPropertyList<O, E> extends AbstractModelProperty<O, List<E>> implements
        IncludableProperties<O, E, Integer>,
        InsertableProperties<O, E, Integer>,
        StripableProperties<O, E, Integer>,
        ExtractableProperties<O, E, Integer>,
        DropableProperties<O, E, Integer> {

    private ModelPropertyList(String id, ReferencedGetter<O, List<E>> getter, ReferencedSetter<O, List<E>> setter) {
        super(id, getter, setter);
    }

    // ###########################################################################################################
    // ################################################ CONTEXT ##################################################
    // ###########################################################################################################

    private List<E> elements(O object, Context context) {
        List<E> elements = get(object, context, false);
        if (elements == null) {
            throw new InterruptedPropertyPathException(this);
        }
        return elements;
    }

    @Override
    public Integer include(O object, E element, Context context) {
        List<E> elements = elements(object, context);
        elements.add(element);
        return elements.size()-1;
    }

    @Override
    public void insert(O object, E element, Integer reference, Context context) {
        elements(object, context).add(reference, element);
    }

    @Override
    public ReferencedValue<Integer, E> strip(O object, Context context) {
        List<E> elements = elements(object, context);
        return elements.isEmpty() ? null : ReferencedValue.of(elements.size()-1, elements.remove(elements.size()-1));
    }

    @Override
    public E extract(O object, Integer reference, Context context) {
        return elements(object, context).remove((int) reference);
    }

    @Override
    public Integer drop(O object, E element, Context context) {
        List<E> elements = elements(object, context);
        int index = elements.indexOf(element);
        if (index == -1) {
            throw new OutboundPropertyPathException(this, null); // TODO other exception
        }
        elements.remove(element);
        return index;
    }

    // ###########################################################################################################
    // ################################################ PATHING ##################################################
    // ###########################################################################################################

    @Override
    public <S> ModelPropertyList<S, E> prepend(Property<S, O> parent) {
        return new ModelPropertyList<>(parent.getId()+'.'+getId(),
                PathReferencedGetter.from(parent, this, getGetter()),
                PathReferencedSetter.from(parent, this, getSetter()));
    }

    // ###########################################################################################################
    // ################################################ FACTORY ##################################################
    // ###########################################################################################################

    public static <O, E> ModelPropertyList<O, E> fromObject(Getter<O, List<E>> getter) {
        return fromObject(null, getter);
    }

    public static <O, E> ModelPropertyList<O, E> fromObject(String id, Getter<O, List<E>> getter) {
        return new ModelPropertyList<>(id, ObjectReferencedGetter.from(getter), ReadonlyReferencedSetter.from());
    }

    public static <O, E> ModelPropertyList<O, E> fromObject(Getter<O, List<E>> getter, Setter<O, List<E>> setter) {
        return fromObject(null, getter, setter);
    }

    public static <O, E> ModelPropertyList<O, E> fromObject(String id, Getter<O, List<E>> getter, Setter<O, List<E>> setter) {
        return new ModelPropertyList<>(id, ObjectReferencedGetter.from(getter), ObjectReferencedSetter.from(setter));
    }

    public static <E> ModelPropertyList<List<List<E>>, E> fromList() {
        return fromList(null);
    }

    public static <E> ModelPropertyList<List<List<E>>, E> fromList(String id) {
        return new ModelPropertyList<>(id, ListReferencedGetter.from(), ListReferencedSetter.from());
    }
}