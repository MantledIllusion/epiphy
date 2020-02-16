package com.mantledillusion.data.epiphy.context.function;

import com.mantledillusion.data.epiphy.Property;
import com.mantledillusion.data.epiphy.context.Context;
import com.mantledillusion.data.epiphy.context.ReferencingProperty;
import com.mantledillusion.data.epiphy.context.reference.PropertyReference;
import com.mantledillusion.data.epiphy.context.reference.ReferencedValue;
import com.mantledillusion.data.epiphy.exception.InterruptedPropertyPathException;
import com.mantledillusion.data.epiphy.exception.OutboundPropertyPathException;
import com.mantledillusion.data.epiphy.exception.UnreferencedPropertyPathException;

/**
 * Interface for properties that carry elements whose references have a natural order, so elements can be removed
 * without explicitly providing a reference.
 *
 * @param <O>
 *            The root parent object type of this {@link Property}.
 * @param <V>
 *     		  The type of the child value this {@link Property} represents.
 * @param <E>
 *            The type of the property element this {@link StripableProperty} represents.
 * @param <R>
 *            The type of the context reference.
 */
public interface StripableProperty<O, V, E, R> extends ReferencingProperty<O, V, E, R> {

    /**
     * Removes an element from the batch that is represented by this {@link StripableProperty}.
     * <p>
     * The element is removed at the last natural reference.
     * <p>
     * Note that this is a writing operation, so the property has to {@link Property#exists(Object)} in the object.
     *
     * @param object
     * 			The object to add the element to; might <b>not</b> be null.
     * @return
     *          A {@link ReferencedValue} that contains the property reference and value that has been removed, might be
     *          null if nothing was removed
     * @throws InterruptedPropertyPathException
     * 			If any property on the path to this {@link StripableProperty} is null.
     * @throws UnreferencedPropertyPathException
     *          If there is any uncontexted property in this {@link StripableProperty}'s path.
     */
    default ReferencedValue<R, E> strip(O object) throws
            InterruptedPropertyPathException, UnreferencedPropertyPathException {
        return strip(object, null);
    }

    /**
     * Removes an element from the batch that is represented by this {@link StripableProperty}.
     * <p>
     * The element is removed at the last natural reference.
     * <p>
     * Note that this is a writing operation, so the property has to {@link Property#exists(Object)} in the object.
     *
     * @param object
     * 			The object to add the element to; might <b>not</b> be null.
     * @param context
     * 			The {@link Context} that should be used to satisfy the contexted properties from the root property to
     * 			this {@link StripableProperty}; might be null.
     * @return
     *          A {@link ReferencedValue} that contains the property reference and value that has been removed, might be
     *          null if nothing was removed
     * @throws InterruptedPropertyPathException
     * 			If any property on the path to this {@link StripableProperty} is null.
     * @throws UnreferencedPropertyPathException
     * 			If there is any uncontexted property in this {@link StripableProperty}'s path that does not have a
     * 			{@link PropertyReference} included in the given {@link Context}.
     * @throws OutboundPropertyPathException
     * 			If the property reference provided by the given {@link Context} is out of bounds on the given object's
     * 			elements this {@link StripableProperty} represents.
     */
    ReferencedValue<R, E> strip(O object, Context context) throws
            InterruptedPropertyPathException, UnreferencedPropertyPathException, OutboundPropertyPathException;
}
