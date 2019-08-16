package com.mantledillusion.data.epiphy.list.test;

import com.mantledillusion.data.epiphy.context.reference.ReferencedValue;
import com.mantledillusion.data.epiphy.context.DefaultContext;
import com.mantledillusion.data.epiphy.context.reference.PropertyIndex;
import com.mantledillusion.data.epiphy.list.AbstractListModelPropertyTest;
import com.mantledillusion.data.epiphy.list.ListModelProperties;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RemoveListModelPropertyTest extends AbstractListModelPropertyTest {

	@Test
	public void testRemoveIndexedPropertyAtEnd() {
		DefaultContext context = DefaultContext.of(PropertyIndex.of(ListModelProperties.ELEMENTLIST, 0));

		ReferencedValue<Integer, String> stripped = ListModelProperties.ELEMENTLIST.strip(this.model, context);
		assertSame(1, stripped.getReference());
		assertSame(ELEMENT_0_ELEMENT_1, stripped.getValue());
		assertEquals(1, this.model.get(0).size());
		assertSame(ELEMENT_0_ELEMENT_0, this.model.get(0).get(0));

		stripped = ListModelProperties.ELEMENTLIST.strip(this.model, context);
		assertSame(0, stripped.getReference());
		assertSame(ELEMENT_0_ELEMENT_0, stripped.getValue());
		assertEquals(0, this.model.get(0).size());
		
		assertEquals(null, ListModelProperties.ELEMENTLIST.strip(this.model, context));
	}

	@Test
	public void testRemoveIndexedPropertyAtIndex() {
		DefaultContext context = DefaultContext.of(PropertyIndex.of(ListModelProperties.ELEMENTLIST, 0));
		assertSame(ELEMENT_0_ELEMENT_1, ListModelProperties.ELEMENTLIST.extract(this.model, 1, context));
		assertEquals(1, this.model.get(0).size());
		assertSame(ELEMENT_0_ELEMENT_0, this.model.get(0).get(0));

		assertSame(ELEMENT_0_ELEMENT_0, ListModelProperties.ELEMENTLIST.extract(this.model, 0, context));
		assertEquals(0, this.model.get(0).size());
	}
/*
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
	}*/
}
