package com.mantledillusion.data.epiphy.list.test;

import com.mantledillusion.data.epiphy.context.Context;
import com.mantledillusion.data.epiphy.context.reference.PropertyIndex;
import com.mantledillusion.data.epiphy.exception.InterruptedPropertyPathException;
import com.mantledillusion.data.epiphy.exception.OutboundPropertyPathException;
import com.mantledillusion.data.epiphy.exception.UnreferencedPropertyPathException;
import com.mantledillusion.data.epiphy.list.AbstractListModelPropertyTest;
import com.mantledillusion.data.epiphy.list.ListModelProperties;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class IncludeListModelPropertyTest extends AbstractListModelPropertyTest {

	@Test
	public void testInclude() {
		Context context = Context.of(PropertyIndex.of(ListModelProperties.ELEMENTLIST, 0));
		assertEquals(2, ListModelProperties.ELEMENTLIST.include(this.model, NEW_ELEMENT, context));
		assertEquals(3, this.model.get(0).size());
		assertSame(NEW_ELEMENT, this.model.get(0).get(2));
	}

	@Test
	public void testIncludeInterrupted() {
		this.model.set(0, null);
		Context context = Context.of(PropertyIndex.of(ListModelProperties.ELEMENTLIST, 0));
		assertThrows(InterruptedPropertyPathException.class, () -> {
			ListModelProperties.ELEMENTLIST.include(this.model, NEW_ELEMENT, context);
		});
	}

	@Test
	public void testIncludeUnreferenced() {
		assertThrows(UnreferencedPropertyPathException.class, () -> {
			ListModelProperties.ELEMENTLIST.include(this.model, NEW_ELEMENT);
		});
	}

	@Test
	public void testIncludeOutbound() {
		Context context = Context.of(PropertyIndex.of(ListModelProperties.ELEMENTLIST, 3));
		assertThrows(OutboundPropertyPathException.class, () -> {
			ListModelProperties.ELEMENTLIST.include(this.model, NEW_ELEMENT, context);
		});
	}
}
