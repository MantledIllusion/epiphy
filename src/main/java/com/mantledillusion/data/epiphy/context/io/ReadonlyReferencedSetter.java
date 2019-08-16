package com.mantledillusion.data.epiphy.context.io;

import com.mantledillusion.data.epiphy.Property;
import com.mantledillusion.data.epiphy.context.Context;
import com.mantledillusion.data.epiphy.exception.ReadonlyPropertyException;
import com.mantledillusion.data.epiphy.io.ReferencedSetter;

public class ReadonlyReferencedSetter<O, V> implements ReferencedSetter<O, V> {

    private ReadonlyReferencedSetter() {

    }

    @Override
    public void set(Property<O, V> property, O instance, V value, Context context) {
        throw new ReadonlyPropertyException(property);
    }

    public static <O, V> ReadonlyReferencedSetter<O, V> from() {
        return new ReadonlyReferencedSetter<>();
    }
}