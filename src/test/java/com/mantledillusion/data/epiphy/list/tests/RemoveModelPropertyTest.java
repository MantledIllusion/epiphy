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
		ListModelProperties.MODEL.remove(this.model);
		assertEquals(1, ListModelProperties.MODEL.get(this.model).size());
		DefaultIndexContext context = DefaultIndexContext.of(
				PropertyIndex.of(ListModelProperties.MODEL, 0),
				PropertyIndex.of(ListModelProperties.ELEMENTLIST, 0));
		assertEquals(ELEMENT_1A, ListModelProperties.ELEMENT.get(this.model, context));
	}
	
	@Test
	public void testRemoveIndexedPropertyAtIndex() {
		DefaultIndexContext context = DefaultIndexContext.of(
				PropertyIndex.of(ListModelProperties.MODEL, 0),
				PropertyIndex.of(ListModelProperties.ELEMENTLIST, 0));
		assertEquals(ELEMENT_1A, ListModelProperties.ELEMENTLIST.remove(this.model, context));
		assertEquals(ELEMENT_1B, ListModelProperties.ELEMENT.get(this.model, context));
	}
	
	@Test
	public void testRemoveIdentifiedProperty() {
		DefaultIndexContext context = DefaultIndexContext.of(
				PropertyIndex.of(ListModelProperties.MODEL, 0),
				PropertyIndex.of(ListModelProperties.ELEMENTLIST, 0));
		assertEquals(0, (long) ListModelProperties.ELEMENTLIST.remove(this.model, ELEMENT_1A, context));
		assertEquals(ELEMENT_1B, ListModelProperties.ELEMENT.get(this.model, context));
		assertEquals(null, ListModelProperties.ELEMENTLIST.remove(this.model, ELEMENT_1A, context));
	}
}
