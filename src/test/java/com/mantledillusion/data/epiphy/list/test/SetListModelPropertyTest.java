package com.mantledillusion.data.epiphy.list.test;

import com.mantledillusion.data.epiphy.context.Context;
import com.mantledillusion.data.epiphy.context.reference.PropertyIndex;
import com.mantledillusion.data.epiphy.exception.InterruptedPropertyPathException;
import com.mantledillusion.data.epiphy.exception.OutboundPropertyPathException;
import com.mantledillusion.data.epiphy.exception.UnreferencedPropertyPathException;
import com.mantledillusion.data.epiphy.list.AbstractListModelPropertyTest;
import com.mantledillusion.data.epiphy.list.ListModelProperties;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SetListModelPropertyTest extends AbstractListModelPropertyTest {

	@Test
	public void testSet() {
		Context context = Context.of(PropertyIndex.of(ListModelProperties.ELEMENTLIST, 0),
				PropertyIndex.of(ListModelProperties.ELEMENT, 0));
		ListModelProperties.ELEMENTLIST_TO_ELEMENT.set(this.model, NEW_ELEMENT, context);
		assertSame(NEW_ELEMENT, ListModelProperties.ELEMENTLIST_TO_ELEMENT.get(this.model, context));
	}

	@Test
	public void testSetInterrupted() {
		this.model.set(0, null);
		Context context = Context.of(PropertyIndex.of(ListModelProperties.ELEMENTLIST, 0),
				PropertyIndex.of(ListModelProperties.ELEMENT, 0));
		assertThrows(InterruptedPropertyPathException.class, () -> {
			ListModelProperties.ELEMENTLIST_TO_ELEMENT.set(this.model, NEW_ELEMENT, context);
		});
	}

	@Test
	public void testSetUnreferenced() {
		assertThrows(UnreferencedPropertyPathException.class, () -> {
			ListModelProperties.ELEMENTLIST_TO_ELEMENT.set(this.model, NEW_ELEMENT);
		});
	}

	@Test
	public void testSetOutbound() {
		Context context = Context.of(PropertyIndex.of(ListModelProperties.ELEMENTLIST, 0),
				PropertyIndex.of(ListModelProperties.ELEMENT, 2));
		assertThrows(OutboundPropertyPathException.class, () -> {
			ListModelProperties.ELEMENTLIST_TO_ELEMENT.set(this.model, NEW_ELEMENT, context);
		});
	}
}
