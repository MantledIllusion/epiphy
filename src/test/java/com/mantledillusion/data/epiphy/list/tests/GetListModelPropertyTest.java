package com.mantledillusion.data.epiphy.list.tests;

import static org.junit.Assert.assertSame;

import org.junit.Test;

import com.mantledillusion.data.epiphy.exception.InterruptedPropertyPathException;
import com.mantledillusion.data.epiphy.index.PropertyIndex;
import com.mantledillusion.data.epiphy.index.impl.DefaultIndexContext;
import com.mantledillusion.data.epiphy.list.AbstractListModelPropertyTest;
import com.mantledillusion.data.epiphy.list.ListModelProperties;

public class GetListModelPropertyTest extends AbstractListModelPropertyTest {

	@Test
	public void testGetProperty() {
		DefaultIndexContext context = DefaultIndexContext.of(PropertyIndex.of(ListModelProperties.MODEL, 0),
				PropertyIndex.of(ListModelProperties.ELEMENTLIST, 0));
		assertSame(this.model.get(0), ListModelProperties.ELEMENTLIST.get(this.model, context));
	}

	@Test(expected = InterruptedPropertyPathException.class)
	public void testGetPropertyIntermediateNull() {
		this.model.set(0, null);
		DefaultIndexContext context = DefaultIndexContext.of(PropertyIndex.of(ListModelProperties.MODEL, 0),
				PropertyIndex.of(ListModelProperties.ELEMENTLIST, 0));
		ListModelProperties.ELEMENT.get(this.model, context);
	}
}
