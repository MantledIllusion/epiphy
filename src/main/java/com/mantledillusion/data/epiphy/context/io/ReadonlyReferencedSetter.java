package com.mantledillusion.data.epiphy.context.io;

import com.mantledillusion.data.epiphy.Property;
import com.mantledillusion.data.epiphy.context.Context;
import com.mantledillusion.data.epiphy.exception.ReadonlyPropertyException;

public class ReadonlyReferencedSetter<O, V> implements ReferencedSetter<O, V> {

    private ReadonlyReferencedSetter() {

    }

    @Override
    public void set(Property<O, V> property, O object, V value, Context context) {
        throw new ReadonlyPropertyException(property);
    }

    @Override
    public boolean isWritable() {
        return false;
    }

    public static <O, V> ReadonlyReferencedSetter<O, V> from() {
        return new ReadonlyReferencedSetter<>();
    }
}
