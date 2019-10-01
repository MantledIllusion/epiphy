package com.mantledillusion.data.epiphy;

import com.mantledillusion.data.epiphy.context.Context;
import com.mantledillusion.data.epiphy.context.io.ReferencedGetter;
import com.mantledillusion.data.epiphy.context.io.ReferencedSetter;

import java.util.Collection;
import java.util.Set;

abstract class AbstractModelProperty<O, V> implements Property<O, V> {

    private final String id;
    private final ReferencedGetter<O, V> getter;
    private final ReferencedSetter<O, V> setter;

    AbstractModelProperty(String id, ReferencedGetter<O, V> getter, ReferencedSetter<O, V> setter) {
        this.id = id == null ? String.valueOf(System.identityHashCode(this)) : id;
        this.getter = getter;
        this.setter = setter;
    }

    @Override
    public String getId() {
        return this.id;
    }

    // ###########################################################################################################
    // ############################################### EXISTENCE #################################################
    // ###########################################################################################################

    @Override
    public boolean exists(O instance, Context context) {
        return instance != null;
    }

    @Override
    public boolean isWritable() {
        return this.setter.isWritable();
    }

    // ###########################################################################################################
    // ################################################ GETTING ##################################################
    // ###########################################################################################################

    protected ReferencedGetter<O, V> getGetter() {
        return getter;
    }

    @Override
    public V get(O instance, Context context, boolean allowNull) {
        return this.getter.get(this, instance, context, allowNull);
    }

    // ###########################################################################################################
    // ################################################ SETTING ##################################################
    // ###########################################################################################################

    protected ReferencedSetter<O, V> getSetter() {
        return setter;
    }

    @Override
    public void set(O instance, V value, Context context) {
        this.setter.set(this, instance, value, context);
    }

    // ###########################################################################################################
    // ############################################## CONTEXTING #################################################
    // ###########################################################################################################


    @Override
    public int occurrences(O object) {
        return this.getter.occurrences(this, object);
    }

    @Override
    public Collection<Context> contextualize(O object) {
        return this.getter.contextualize(this, object);
    }

    @Override
    public Set<Property<?, ?>> getHierarchy() {
        return this.getter.getHierarchy(this);
    }

    // ###########################################################################################################
    // ################################################# MISC ####################################################
    // ###########################################################################################################

    @Override
    public String toString() {
        return getId();
    }

    @Override
    public final int hashCode() {
        return System.identityHashCode(this);
    }

    @Override
    public final boolean equals(Object obj) {
        return obj == this;
    }
}
