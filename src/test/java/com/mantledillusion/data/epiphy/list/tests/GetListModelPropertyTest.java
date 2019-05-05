package com.mantledillusion.data.epiphy.list.tests;

import org.junit.jupiter.api.Test;

import com.mantledillusion.data.epiphy.context.PropertyIndex;
import com.mantledillusion.data.epiphy.context.impl.DefaultContext;
import com.mantledillusion.data.epiphy.exception.InterruptedPropertyPathException;
import com.mantledillusion.data.epiphy.exception.OutboundPropertyPathException;
import com.mantledillusion.data.epiphy.list.AbstractListModelPropertyTest;
import com.mantledillusion.data.epiphy.list.ListModelProperties;

import static org.junit.jupiter.api.Assertions.*;

public class GetListModelPropertyTest extends AbstractListModelPropertyTest {

	@Test
	public void testGetIndexedProperty() {
		DefaultContext context = DefaultContext.of(PropertyIndex.of(ListModelProperties.MODEL, 0),
				PropertyIndex.of(ListModelProperties.ELEMENTLIST, 0));
		assertSame(this.model.get(0), ListModelProperties.ELEMENTLIST.get(this.model, context));
	}

	@Test
	public void testGetIndexedPropertyIntermediateNull() {
		this.model.set(0, null);
		DefaultContext context = DefaultContext.of(PropertyIndex.of(ListModelProperties.MODEL, 0),
				PropertyIndex.of(ListModelProperties.ELEMENTLIST, 1));
		assertThrows(InterruptedPropertyPathException.class, () -> {
			ListModelProperties.ELEMENT.get(this.model, context);
		});
	}

	@Test
	public void testGetIndexedPropertyOutOfBounds() {
		DefaultContext context = DefaultContext.of(PropertyIndex.of(ListModelProperties.MODEL, 0),
				PropertyIndex.of(ListModelProperties.ELEMENTLIST, 2));
		assertThrows(OutboundPropertyPathException.class, () -> {
			ListModelProperties.ELEMENT.get(this.model, context);
		});
	}
}
