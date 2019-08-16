package com.mantledillusion.data.epiphy.context.function;

import com.mantledillusion.data.epiphy.Property;
import com.mantledillusion.data.epiphy.context.Context;
import com.mantledillusion.data.epiphy.context.reference.ReferencedValue;
import com.mantledillusion.data.epiphy.exception.InterruptedPropertyPathException;
import com.mantledillusion.data.epiphy.exception.UnreferencedPropertyPathException;

public interface StrippableProperties<C, E, R> {

    /**
     * Removes the element from its elements that is represented by this
     * {@link ExtractableProperties} in the given model.
     * <p>
     * Instead of using a property reference in the context, this operation uses the
     * property reference's natural order to remove the given element from the end.
     * <p>
     * Note that this is a writing operation, so the property has to
     * {@link Property#exists(Object)} in the model.
     *
     * @param collection
     *            The model to remove the element from; might <b>not</b> be null.
     * @return A {@link ReferencedValue} that contains the property reference and
     *         value that has been removed, might be null if nothing was removed
     * @throws InterruptedPropertyPathException
     *             If any property on the path to this {@link ExtractableProperties}
     *             is null.
     * @throws UnreferencedPropertyPathException
     *             If there is any uncontexted property in this
     *             {@link ExtractableProperties}'s path.
     */
    default ReferencedValue<Integer, E> strip(C collection) {
        return strip(collection, null);
    }

    /**
     * Removes the element from its elements that is represented by this
     * {@link ExtractableProperties} in the given model.
     * <p>
     * Instead of using a property reference in the context, this operation uses the
     * property reference's natural order to remove the given element from the end.
     * <p>
     * Note that this is a writing operation, so the property has to
     * {@link Property#exists(Object)} in the model.
     *
     * @param model
     *            The model to remove the element from; might <b>not</b> be null.
     * @param context
     *            The {@link Context} that should be used to satisfy the contexted
     *            properties from the root property to this
     *            {@link IncludableProperties}; might be null.
     * @return A {@link ReferencedValue} that contains the property reference and
     *         value that has been removed, might be null if nothing was removed
     * @throws InterruptedPropertyPathException
     *             If any property on the path to this {@link ExtractableProperties}
     *             is null.
     * @throws UnreferencedPropertyPathException
     *             If there is any uncontexted property in this
     *             {@link ExtractableProperties}'s path.
     */
    ReferencedValue<Integer, E> strip(C model, Context context);
}
