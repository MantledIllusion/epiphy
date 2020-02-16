package com.mantledillusion.data.epiphy.context.function;

import com.mantledillusion.data.epiphy.Property;
import com.mantledillusion.data.epiphy.context.Context;
import com.mantledillusion.data.epiphy.context.ReferencableProperty;
import com.mantledillusion.data.epiphy.context.reference.PropertyReference;
import com.mantledillusion.data.epiphy.exception.InterruptedPropertyPathException;
import com.mantledillusion.data.epiphy.exception.OutboundPropertyPathException;
import com.mantledillusion.data.epiphy.exception.UnreferencedPropertyPathException;

/**
 * Interface for properties that carry elements which can be removed using the element itself.
 *
 * @param <O>
 *            The root parent object type of this {@link Property}.
 * @param <V>
 *     		  The type of the child value this {@link Property} represents.
 * @param <E>
 *            The type of the property element this {@link DropableProperty} represents.
 * @param <R>
 *            The type of the context reference.
 */
public interface DropableProperty<O, V, E, R> extends ReferencableProperty<O, V, E, R> {

    /**
     * Removes an element from the batch that is represented by this {@link DropableProperty}.
     * <p>
     * The element is removed by equality; the equality is determined in relation to the given element.
     * <p>
     * Note that this is a writing operation, so the property has to {@link Property#exists(Object)} in the object.
     *
     * @param object
     *          The object to remove the element from; might <b>not</b> be null.
     * @param element
     *          The element to remove. If it is contained by the elements multiple times, the first occurrence is
     *          removed; might be null.
     * @return
     *          The property reference of the element that has been removed; might be null if the given element was not
     *          included in its element
     * @throws InterruptedPropertyPathException
     *          If any property on the path to this {@link DropableProperty} is null.
     * @throws UnreferencedPropertyPathException
     *          If there is any uncontexted property in this {@link DropableProperty}'s path.
     */
    default R drop(O object, E element) throws
            InterruptedPropertyPathException, UnreferencedPropertyPathException {
        return drop(object, element, null);
    }

    /**
     * Removes an element from the batch that is represented by this {@link DropableProperty}.
     * <p>
     * The element is removed by equality; the equality is determined in relation to the given element.
     * <p>
     * Note that this is a writing operation, so the property has to {@link Property#exists(Object)} in the object.
     *
     * @param object
     *          The object to remove the element from; might <b>not</b> be null.
     * @param element
     *          The element to remove. If it is contained by the elements multiple times, the first occurrence is
     *          removed; might be null.
     * @param context
     *          The {@link Context} that should be used to satisfy the contexted properties from the root property to
     *          this {@link DropableProperty}; might be null.
     * @return
     *          The property reference of the element that has been removed; might be null if the given element was not
     *          included in its element
     * @throws InterruptedPropertyPathException
     *          If any property on the path to this {@link DropableProperty} is null.
     * @throws UnreferencedPropertyPathException
     *          If there is any uncontexted property in this {@link DropableProperty}'s path that does not have a
     *          {@link PropertyReference} included in the given {@link Context}.
     * @throws OutboundPropertyPathException
     *          If the property reference provided by the given {@link Context} is out of bounds on the given object's
     *          elements this {@link DropableProperty} represents.
     */
    R drop(O object, E element, Context context) throws
            InterruptedPropertyPathException, UnreferencedPropertyPathException, OutboundPropertyPathException;
}
