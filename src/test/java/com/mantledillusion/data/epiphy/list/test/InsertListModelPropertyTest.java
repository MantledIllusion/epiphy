package com.mantledillusion.data.epiphy.list.test;

import com.mantledillusion.data.epiphy.context.Context;
import com.mantledillusion.data.epiphy.context.reference.PropertyIndex;
import com.mantledillusion.data.epiphy.exception.InterruptedPropertyPathException;
import com.mantledillusion.data.epiphy.exception.OutboundInsertableReferenceException;
import com.mantledillusion.data.epiphy.exception.OutboundPropertyPathException;
import com.mantledillusion.data.epiphy.exception.UnreferencedPropertyPathException;
import com.mantledillusion.data.epiphy.list.AbstractListModelPropertyTest;
import com.mantledillusion.data.epiphy.list.ListModelProperties;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class InsertListModelPropertyTest extends AbstractListModelPropertyTest {

	@Test
	public void testInsert() {
		Context context = Context.of(PropertyIndex.of(ListModelProperties.ELEMENTLIST, 0));
		ListModelProperties.ELEMENTLIST.insert(this.model, NEW_ELEMENT, 0, context);
		assertEquals(3, this.model.get(0).size());
		assertSame(NEW_ELEMENT, this.model.get(0).get(0));
	}

	@Test
	public void testInsertUnknown() {
		Context context = Context.of(PropertyIndex.of(ListModelProperties.ELEMENTLIST, 0));
		assertThrows(OutboundInsertableReferenceException.class, () -> {
			ListModelProperties.ELEMENTLIST.insert(this.model, NEW_ELEMENT, 3, context);
		});
	}

	@Test
	public void testInsertInterrupted() {
		this.model.set(0, null);
		Context context = Context.of(PropertyIndex.of(ListModelProperties.ELEMENTLIST, 0));
		assertThrows(InterruptedPropertyPathException.class, () -> {
			ListModelProperties.ELEMENTLIST.insert(this.model, NEW_ELEMENT, 0, context);
		});
	}

	@Test
	public void testInsertUnreferenced() {
		assertThrows(UnreferencedPropertyPathException.class, () -> {
			ListModelProperties.ELEMENTLIST.insert(this.model, NEW_ELEMENT, 0);
		});
	}

	@Test
	public void testInsertOutbound() {
		Context context = Context.of(PropertyIndex.of(ListModelProperties.ELEMENTLIST, 3));
		assertThrows(OutboundPropertyPathException.class, () -> {
			ListModelProperties.ELEMENTLIST.insert(this.model, NEW_ELEMENT, 0, context);
		});
	}
}
