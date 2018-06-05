package com.mantledillusion.data.epiphy.list.tests;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Test;

import com.mantledillusion.data.epiphy.index.PropertyIndex;
import com.mantledillusion.data.epiphy.index.impl.DefaultIndexContext;
import com.mantledillusion.data.epiphy.list.AbstractListModelPropertyTest;
import com.mantledillusion.data.epiphy.list.ListModelProperties;

public class AddModelPropertyTest extends AbstractListModelPropertyTest {
	
	@Test
	public void testAddIndexedPropertyAtEnd() {
		ListModelProperties.MODEL.add(this.model, Arrays.asList("newElem"));
		DefaultIndexContext context = DefaultIndexContext.of(
				PropertyIndex.of(ListModelProperties.MODEL, 2),
				PropertyIndex.of(ListModelProperties.ELEMENTLIST, 0));
		assertEquals("newElem", ListModelProperties.ELEMENT.get(this.model, context));
	}
	
	@Test
	public void testAddIndexedPropertyAtIndex() {
		DefaultIndexContext context = DefaultIndexContext.of(
				PropertyIndex.of(ListModelProperties.MODEL, 0),
				PropertyIndex.of(ListModelProperties.ELEMENTLIST, 1));
		ListModelProperties.ELEMENTLIST.add(this.model, "newElem", context);
		assertEquals("newElem", ListModelProperties.ELEMENT.get(this.model, context));
		DefaultIndexContext context2 = DefaultIndexContext.of(
				PropertyIndex.of(ListModelProperties.MODEL, 0),
				PropertyIndex.of(ListModelProperties.ELEMENTLIST, 2));
		assertEquals("e1b", ListModelProperties.ELEMENT.get(this.model, context2));
	}
}
