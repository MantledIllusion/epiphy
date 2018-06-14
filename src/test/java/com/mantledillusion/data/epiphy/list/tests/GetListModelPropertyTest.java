package com.mantledillusion.data.epiphy.list.tests;

import static org.junit.Assert.assertSame;

import org.junit.Test;

import com.mantledillusion.data.epiphy.context.PropertyIndex;
import com.mantledillusion.data.epiphy.context.impl.DefaultContext;
import com.mantledillusion.data.epiphy.exception.InterruptedPropertyPathException;
import com.mantledillusion.data.epiphy.exception.OutboundPropertyPathException;
import com.mantledillusion.data.epiphy.list.AbstractListModelPropertyTest;
import com.mantledillusion.data.epiphy.list.ListModelProperties;

public class GetListModelPropertyTest extends AbstractListModelPropertyTest {

	@Test
	public void testGetIndexedProperty() {
		DefaultContext context = DefaultContext.of(PropertyIndex.of(ListModelProperties.MODEL, 0),
				PropertyIndex.of(ListModelProperties.ELEMENTLIST, 0));
		assertSame(this.model.get(0), ListModelProperties.ELEMENTLIST.get(this.model, context));
	}

	@Test(expected = InterruptedPropertyPathException.class)
	public void testGetIndexedPropertyIntermediateNull() {
		this.model.set(0, null);
		DefaultContext context = DefaultContext.of(PropertyIndex.of(ListModelProperties.MODEL, 0),
				PropertyIndex.of(ListModelProperties.ELEMENTLIST, 1));
		ListModelProperties.ELEMENT.get(this.model, context);
	}

	@Test(expected = OutboundPropertyPathException.class)
	public void testGetIndexedPropertyOutOfBounds() {
		DefaultContext context = DefaultContext.of(PropertyIndex.of(ListModelProperties.MODEL, 0),
				PropertyIndex.of(ListModelProperties.ELEMENTLIST, 2));
		ListModelProperties.ELEMENT.get(this.model, context);
	}
}
