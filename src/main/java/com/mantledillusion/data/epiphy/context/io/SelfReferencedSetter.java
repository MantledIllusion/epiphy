package com.mantledillusion.data.epiphy.context.io;

import com.mantledillusion.data.epiphy.Property;
import com.mantledillusion.data.epiphy.context.Context;
import com.mantledillusion.data.epiphy.io.ReferencedSetter;

public class SelfReferencedSetter<O, V> implements ReferencedSetter<O, V> {

    private final Property<O, V> property;

    private SelfReferencedSetter(Property<O, V> property) {
        this.property = property;
    }

    @Override
    public void set(Property<O, V> property, O object, V value, Context context) {
        this.property.set(object, value, context);
    }

    @Override
    public boolean isWritable() {
        return true;
    }

    public static <O, V> SelfReferencedSetter from(Property<O, V> property) {
        return new SelfReferencedSetter(property);
    }
}
