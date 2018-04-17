package com.mantledillusion.data.epiphy.interfaces;

import java.lang.reflect.Method;
import java.util.List;

import com.mantledillusion.data.epiphy.ModelProperty;
import com.mantledillusion.data.epiphy.ModelPropertyList;
import com.mantledillusion.data.epiphy.io.Getter;
import com.mantledillusion.data.epiphy.io.Setter;

/**
 * Defines a definite, single {@link Property}.
 * <p>
 * On a {@link DefiniteProperty} instance, the {@link Method}s...<br>
 * - {@link #registerChild(Getter, Setter)}<br>
 * - {@link #registerChild(String, Getter, Setter)}<br>
 * ... can be used to add a definite child property to that instance. Also, the
 * {@link Method}s...<br>
 * - {@link #registerChildList(Getter, Setter)}<br>
 * - {@link #registerChildList(String, Getter, Setter)}<br>
 * ... can be used to add child property lists to that instance.
 *
 * @param <M>
 * @param <T>
 */
public interface DefiniteProperty<M, T> extends Property<M, T> {

	/**
	 * Registers a child to this {@link DefiniteProperty} that can be accessed by
	 * the given {@link Getter}/{@link Setter} combination.
	 * 
	 * @param <C>
	 *            The child properties' type.
	 * @param getter
	 *            The {@link Getter} that can be applied onto this
	 *            {@link DefiniteProperty} to retrieve the child property; might
	 *            <b>NOT</b> be null.
	 * @param setter
	 *            The {@link Setter} that can be applied onto this
	 *            {@link DefiniteProperty} to set the child property; might
	 *            <b>NOT</b> be null.
	 * @return The instantiated child property; never null
	 */
	public <C> ModelProperty<M, C> registerChild(Getter<T, C> getter, Setter<T, C> setter);

	/**
	 * Registers an id'ed child to this {@link DefiniteProperty} that can be
	 * accessed by the given {@link Getter}/{@link Setter} combination.
	 * 
	 * @param <C>
	 *            The child properties' type.
	 * @param id
	 *            The id the returned child {@link ModelProperty} will be identified
	 *            by; might be null. All parent id's (including this
	 *            {@link DefiniteProperty}'s id) will be prepended.
	 * @param getter
	 *            The {@link Getter} that can be applied onto this
	 *            {@link DefiniteProperty} to retrieve the child property; might
	 *            <b>NOT</b> be null.
	 * @param setter
	 *            The {@link Setter} that can be applied onto this
	 *            {@link DefiniteProperty} to set the child property; might
	 *            <b>NOT</b> be null.
	 * @return The instantiated child property; never null
	 */
	public <C> ModelProperty<M, C> registerChild(String id, Getter<T, C> getter, Setter<T, C> setter);

	/**
	 * Registers a listed child to this {@link DefiniteProperty} that can be
	 * accessed by the given {@link Getter}/{@link Setter} combination.
	 * 
	 * @param <C>
	 *            The child properties' type.
	 * @param getter
	 *            The {@link Getter} that can be applied onto this
	 *            {@link DefiniteProperty} to retrieve the listed child property;
	 *            might <b>NOT</b> be null.
	 * @param setter
	 *            The {@link Setter} that can be applied onto this
	 *            {@link DefiniteProperty} to set the listed child property; might
	 *            <b>NOT</b> be null.
	 * @return The instantiated listed child property; never null
	 */
	public <C> ModelPropertyList<M, C> registerChildList(Getter<T, List<C>> getter, Setter<T, List<C>> setter);

	/**
	 * Registers an id'ed listed child to this {@link DefiniteProperty} that can be
	 * accessed by the given {@link Getter}/{@link Setter} combination.
	 * 
	 * @param <C>
	 *            The child properties' type.
	 * @param id
	 *            The id the returned listed child {@link ModelPropertyList} will be
	 *            identified by; might be null. All parent id's (including this
	 *            {@link DefiniteProperty}'s id) will be prepended.
	 * @param getter
	 *            The {@link Getter} that can be applied onto this
	 *            {@link DefiniteProperty} to retrieve the listed child property;
	 *            might <b>NOT</b> be null.
	 * @param setter
	 *            The {@link Setter} that can be applied onto this
	 *            {@link DefiniteProperty} to set the listed child property; might
	 *            <b>NOT</b> be null.
	 * @return The instantiated listed child property; never null
	 */
	public <C> ModelPropertyList<M, C> registerChildList(String id, Getter<T, List<C>> getter,
			Setter<T, List<C>> setter);
}
