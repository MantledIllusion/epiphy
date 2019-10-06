package com.mantledillusion.data.epiphy.list.test;

import com.mantledillusion.data.epiphy.context.Context;
import com.mantledillusion.data.epiphy.context.reference.PropertyIndex;
import com.mantledillusion.data.epiphy.context.reference.ReferencedValue;
import com.mantledillusion.data.epiphy.exception.InterruptedPropertyPathException;
import com.mantledillusion.data.epiphy.exception.OutboundPropertyPathException;
import com.mantledillusion.data.epiphy.exception.UnreferencedPropertyPathException;
import com.mantledillusion.data.epiphy.list.AbstractListModelPropertyTest;
import com.mantledillusion.data.epiphy.list.ListModelProperties;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class StripListModelPropertyTest extends AbstractListModelPropertyTest {

	@Test
	public void testStrip() {
		Context context = Context.of(PropertyIndex.of(ListModelProperties.ELEMENTLIST, 0));
		ReferencedValue<Integer, String> stripped = ListModelProperties.ELEMENTLIST.strip(this.model, context);
		assertEquals(1, stripped.getReference());
		assertSame(ELEMENT_0_ELEMENT_1, stripped.getValue());
		assertEquals(1, this.model.get(0).size());
		assertSame(ELEMENT_0_ELEMENT_0, this.model.get(0).get(0));
	}

	@Test
	public void testStripEmpty() {
		this.model.get(0).clear();
		Context context = Context.of(PropertyIndex.of(ListModelProperties.ELEMENTLIST, 0));
		assertEquals(null, ListModelProperties.ELEMENTLIST.strip(this.model, context));
	}

	@Test
	public void testStripInterrupted() {
		this.model.set(0, null);
		Context context = Context.of(PropertyIndex.of(ListModelProperties.ELEMENTLIST, 0));
		assertThrows(InterruptedPropertyPathException.class, () -> {
			ListModelProperties.ELEMENTLIST.strip(this.model, context);
		});
	}

	@Test
	public void testStripUnreferenced() {
		assertThrows(UnreferencedPropertyPathException.class, () -> {
			ListModelProperties.ELEMENTLIST.strip(this.model);
		});
	}

	@Test
	public void testStripOutbound() {
		Context context = Context.of(PropertyIndex.of(ListModelProperties.ELEMENTLIST, 3));
		assertThrows(OutboundPropertyPathException.class, () -> {
			ListModelProperties.ELEMENTLIST.strip(this.model, context);
		});
	}
}
