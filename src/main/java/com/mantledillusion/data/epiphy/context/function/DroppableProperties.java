package com.mantledillusion.data.epiphy.context.function;

import com.mantledillusion.data.epiphy.Property;
import com.mantledillusion.data.epiphy.context.Context;
import com.mantledillusion.data.epiphy.context.reference.PropertyReference;
import com.mantledillusion.data.epiphy.exception.InterruptedPropertyPathException;
import com.mantledillusion.data.epiphy.exception.OutboundPropertyPathException;
import com.mantledillusion.data.epiphy.exception.UnreferencedPropertyPathException;

public interface DroppableProperties<C, E, R> {

    /**
     * Removes the element from its elements that is represented by this
     * {@link ExtractableProperties} in the given model.
     * <p>
     * The element is removed by equality; the equality is determined in relation to
     * the given element.
     * <p>
     * Note that this is a writing operation, so the property has to
     * {@link Property#exists(Object)} in the model.
     *
     * @param collection
     *            The model to remove the element from; might <b>not</b> be null.
     * @param element
     *            The element to remove. If it is contained by the elements multiple
     *            times, the first occurrence is removed; might be null.
     * @return The property reference of the element that has been removed; might be
     *         null if the given element was not included in its element
     * @throws InterruptedPropertyPathException
     *             If any property on the path to this {@link ExtractableProperties}
     *             is null.
     * @throws UnreferencedPropertyPathException
     *             If there is any uncontexted property in this
     *             {@link ExtractableProperties}'s path.
     */
    default R drop(C collection, E element) {
        return drop(collection, element, null);
    }

    /**
     * Removes the element from its elements that is represented by this
     * {@link ExtractableProperties} in the given model.
     * <p>
     * The element is removed by equality; the equality is determined in relation to
     * the given element.
     * <p>
     * Note that this is a writing operation, so the property has to
     * {@link Property#exists(Object, Context)} in the model.
     *
     * @param collection
     *            The model to remove the element from; might <b>not</b> be null.
     * @param element
     *            The element to remove. If it is contained by the elements multiple
     *            times, the first occurrence is removed; might be null.
     * @param context
     *            The {@link Context} that should be used to satisfy the contexted
     *            properties from the root property to this
     *            {@link ExtractableProperties}; might be null.
     * @return The property reference of the element that has been removed; might be
     *         null if the given element was not included in its element
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
    R drop(C collection, E element, Context context);
}
