package com.mantledillusion.data.epiphy.list.tests;

import org.junit.jupiter.api.Test;

import com.mantledillusion.data.epiphy.context.PropertyIndex;
import com.mantledillusion.data.epiphy.context.impl.DefaultContext;
import com.mantledillusion.data.epiphy.exception.InterruptedPropertyPathException;
import com.mantledillusion.data.epiphy.list.AbstractListModelPropertyTest;
import com.mantledillusion.data.epiphy.list.ListModelProperties;

import static org.junit.jupiter.api.Assertions.*;

public class RemoveListModelPropertyTest extends AbstractListModelPropertyTest {

	@Test
	public void testRemoveIndexedPropertyAtEnd() {
		DefaultContext context = DefaultContext.of(PropertyIndex.of(ListModelProperties.MODEL, 0));
		
		assertSame(ELEMENT_0_ELEMENT_1, ListModelProperties.ELEMENTLIST.strip(this.model, context).getValue());
		assertEquals(1, this.model.get(0).size());
		assertSame(ELEMENT_0_ELEMENT_0, this.model.get(0).get(0));
		
		assertSame(ELEMENT_0_ELEMENT_0, ListModelProperties.ELEMENTLIST.strip(this.model, context).getValue());
		assertEquals(0, this.model.get(0).size());
		
		assertEquals(null, ListModelProperties.ELEMENTLIST.strip(this.model, context));
	}

	@Test
	public void testRemoveIndexedPropertyAtIndex() {
		DefaultContext context = DefaultContext.of(PropertyIndex.of(ListModelProperties.MODEL, 0), 
				PropertyIndex.of(ListModelProperties.ELEMENTLIST, 1));
		assertSame(ELEMENT_0_ELEMENT_1, ListModelProperties.ELEMENTLIST.removeAt(this.model, context));
		assertEquals(1, this.model.get(0).size());
		assertSame(ELEMENT_0_ELEMENT_0, this.model.get(0).get(0));
		
		context = DefaultContext.of(PropertyIndex.of(ListModelProperties.MODEL, 0), 
				PropertyIndex.of(ListModelProperties.ELEMENTLIST, 0));
		assertSame(ELEMENT_0_ELEMENT_0, ListModelProperties.ELEMENTLIST.removeAt(this.model, context));
		assertEquals(0, this.model.get(0).size());
	}

	@Test
	public void testRemoveIndexedPropertyByIdentity() {
		DefaultContext context = DefaultContext.of(PropertyIndex.of(ListModelProperties.MODEL, 0),
				PropertyIndex.of(ListModelProperties.ELEMENTLIST, 0));
		assertEquals(0, (int) ListModelProperties.ELEMENTLIST.remove(this.model, ELEMENT_0_ELEMENT_0, context));
		assertSame(ELEMENT_0_ELEMENT_1, this.model.get(0).get(0));
		assertEquals(null, ListModelProperties.ELEMENTLIST.remove(this.model, ELEMENT_0_ELEMENT_0, context));
	}

	@Test
	public void testRemoveIndexedPropertyIntermediateNull() {
		this.model.set(0, null);
		DefaultContext context = DefaultContext.of(PropertyIndex.of(ListModelProperties.MODEL, 0));
		assertThrows(InterruptedPropertyPathException.class, () -> {
			ListModelProperties.ELEMENTLIST.removeAt(this.model, context);
		});
	}
}
