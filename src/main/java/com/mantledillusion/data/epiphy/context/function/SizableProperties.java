package com.mantledillusion.data.epiphy.context.function;

import com.mantledillusion.data.epiphy.context.Context;
import com.mantledillusion.data.epiphy.context.reference.PropertyReference;
import com.mantledillusion.data.epiphy.exception.InterruptedPropertyPathException;
import com.mantledillusion.data.epiphy.exception.OutboundPropertyPathException;
import com.mantledillusion.data.epiphy.exception.UnreferencedPropertyPathException;

public interface SizableProperties<C, E, R> {

    /**
     * Returns the size of the contextable property without context.
     *
     * @param collection
     *            The model to add the element to; might <b>not</b> be null.
     * @return The size of the property
     * @throws InterruptedPropertyPathException
     *             If any property on the path to this {@link ExtractableProperties}
     *             is null.
     * @throws UnreferencedPropertyPathException
     *             If there is any uncontexted property in this
     *             {@link ExtractableProperties}'s path that does not have a
     *             {@link PropertyReference} included in the given {@link Context}.
     */
    default int size(C collection) {
        return size(collection, null);
    }

    /**
     * Returns the size of the contextable property at the given context.
     *
     * @param collection
     *            The model to add the element to; might <b>not</b> be null.
     * @param context
     *            The {@link Context} that should be used to satisfy the contexted
     *            properties from the root property to this
     *            {@link ExtractableProperties}; might be null.
     * @return The size of the property
     * @throws InterruptedPropertyPathException
     *             If any property on the path to this {@link ExtractableProperties}
     *             is null.
     * @throws UnreferencedPropertyPathException
     *             If there is any uncontexted property in this
     *             {@link ExtractableProperties}'s path that does not have a
     *             {@link PropertyReference} included in the given {@link Context}.
     * @throws OutboundPropertyPathException
     *             If the property reference provided by the given {@link Context}
     *             is out of bounds on the given model's elements this
     *             {@link ExtractableProperties} represents.
     */
    int size(C collection, Context context);
}
