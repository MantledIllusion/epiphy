package com.mantledillusion.data.epiphy.list.test;

import com.mantledillusion.data.epiphy.context.Context;
import com.mantledillusion.data.epiphy.context.reference.PropertyIndex;
import com.mantledillusion.data.epiphy.exception.InterruptedPropertyPathException;
import com.mantledillusion.data.epiphy.exception.OutboundPropertyPathException;
import com.mantledillusion.data.epiphy.exception.UnknownDropableElementException;
import com.mantledillusion.data.epiphy.exception.UnreferencedPropertyPathException;
import com.mantledillusion.data.epiphy.list.AbstractListModelPropertyTest;
import com.mantledillusion.data.epiphy.list.ListModelProperties;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DropListModelPropertyTest extends AbstractListModelPropertyTest {

	@Test
	public void testDrop() {
		Context context = Context.of(PropertyIndex.of(ListModelProperties.ELEMENTLIST, 0));
		assertEquals(0, (int) ListModelProperties.ELEMENTLIST.drop(this.model, ELEMENT_0_ELEMENT_0, context));
		assertEquals(1, this.model.get(0).size());
		assertSame(ELEMENT_0_ELEMENT_1, this.model.get(0).get(0));
	}

	@Test
	public void testDropUnknown() {
		Context context = Context.of(PropertyIndex.of(ListModelProperties.ELEMENTLIST, 0));
		assertThrows(UnknownDropableElementException.class, () -> {
			ListModelProperties.ELEMENTLIST.drop(this.model, ELEMENT_1_ELEMENT_0, context);
		});
	}

	@Test
	public void testDropInterrupted() {
		this.model.set(0, null);
		Context context = Context.of(PropertyIndex.of(ListModelProperties.ELEMENTLIST, 0));
		assertThrows(InterruptedPropertyPathException.class, () -> {
			ListModelProperties.ELEMENTLIST.drop(this.model, ELEMENT_0_ELEMENT_1, context);
		});
	}

	@Test
	public void testDropUnreferenced() {
		assertThrows(UnreferencedPropertyPathException.class, () -> {
			ListModelProperties.ELEMENTLIST.drop(this.model, ELEMENT_0_ELEMENT_1);
		});
	}

	@Test
	public void testDropOutbound() {
		Context context = Context.of(PropertyIndex.of(ListModelProperties.ELEMENTLIST, 3));
		assertThrows(OutboundPropertyPathException.class, () -> {
			ListModelProperties.ELEMENTLIST.drop(this.model, ELEMENT_0_ELEMENT_1, context);
		});
	}
}
