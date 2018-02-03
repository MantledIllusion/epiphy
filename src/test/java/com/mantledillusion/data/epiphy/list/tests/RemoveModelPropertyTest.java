package com.mantledillusion.data.epiphy.list.tests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.mantledillusion.data.epiphy.index.PropertyIndex;
import com.mantledillusion.data.epiphy.index.impl.DefaultIndexContext;
import com.mantledillusion.data.epiphy.list.AbstractListModelPropertyTest;
import com.mantledillusion.data.epiphy.list.ListModelProperties;

public class RemoveModelPropertyTest extends AbstractListModelPropertyTest {
	
	@Test
	public void testRemoveIndexedPropertyAtEnd() {
		ListModelProperties.MODEL.remove(model);
		assertEquals(1, ListModelProperties.MODEL.get(this.model).size());
		DefaultIndexContext context = DefaultIndexContext.of(
				PropertyIndex.of(ListModelProperties.MODEL, 0),
				PropertyIndex.of(ListModelProperties.ELEMENTLIST, 0));
		assertEquals("e1a", ListModelProperties.ELEMENT.get(model, context));
	}
	
	@Test
	public void testRemoveIndexedPropertyAtIndex() {
		DefaultIndexContext context = DefaultIndexContext.of(
				PropertyIndex.of(ListModelProperties.MODEL, 0),
				PropertyIndex.of(ListModelProperties.ELEMENTLIST, 0));
		assertEquals("e1a", ListModelProperties.ELEMENTLIST.remove(model, context));
		assertEquals("e1b", ListModelProperties.ELEMENT.get(model, context));
	}
}
