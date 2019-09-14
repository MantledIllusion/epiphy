package com.mantledillusion.data.epiphy.list.test;

import com.mantledillusion.data.epiphy.context.Context;
import com.mantledillusion.data.epiphy.context.reference.PropertyIndex;
import com.mantledillusion.data.epiphy.exception.InterruptedPropertyPathException;
import com.mantledillusion.data.epiphy.exception.OutboundPropertyPathException;
import com.mantledillusion.data.epiphy.list.AbstractListModelPropertyTest;
import com.mantledillusion.data.epiphy.list.ListModelProperties;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class GetListModelPropertyTest extends AbstractListModelPropertyTest {

	@Test
	public void testGetIndexedProperty() {
		Context context = Context.of(PropertyIndex.of(ListModelProperties.ELEMENTLIST, 0),
				PropertyIndex.of(ListModelProperties.ELEMENT, 0));
		assertSame(this.model.get(0).get(0), ListModelProperties.ELEMENTLIST_TO_ELEMENT.get(this.model, context));
	}

	@Test
	public void testGetIndexedPropertyIntermediateNull() {
		this.model.set(0, null);
		Context context = Context.of(PropertyIndex.of(ListModelProperties.ELEMENTLIST, 0),
				PropertyIndex.of(ListModelProperties.ELEMENT, 1));
		assertThrows(InterruptedPropertyPathException.class, () -> {
			ListModelProperties.ELEMENTLIST_TO_ELEMENT.get(this.model, context);
		});
	}

	@Test
	public void testGetIndexedPropertyOutOfBounds() {
		Context context = Context.of(PropertyIndex.of(ListModelProperties.ELEMENTLIST, 0),
				PropertyIndex.of(ListModelProperties.ELEMENT, 2));
		assertThrows(OutboundPropertyPathException.class, () -> {
			ListModelProperties.ELEMENTLIST_TO_ELEMENT.get(this.model, context);
		});
	}
}
