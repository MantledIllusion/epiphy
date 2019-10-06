package com.mantledillusion.data.epiphy.list.test;

import com.mantledillusion.data.epiphy.context.Context;
import com.mantledillusion.data.epiphy.context.reference.PropertyIndex;
import com.mantledillusion.data.epiphy.exception.InterruptedPropertyPathException;
import com.mantledillusion.data.epiphy.exception.OutboundExtractableReferenceException;
import com.mantledillusion.data.epiphy.exception.OutboundPropertyPathException;
import com.mantledillusion.data.epiphy.exception.UnreferencedPropertyPathException;
import com.mantledillusion.data.epiphy.list.AbstractListModelPropertyTest;
import com.mantledillusion.data.epiphy.list.ListModelProperties;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ExtractListModelPropertyTest extends AbstractListModelPropertyTest {

	@Test
	public void testExtract() {
		Context context = Context.of(PropertyIndex.of(ListModelProperties.ELEMENTLIST, 0));
		assertSame(ELEMENT_0_ELEMENT_1, ListModelProperties.ELEMENTLIST.extract(this.model, 1, context));
		assertEquals(1, this.model.get(0).size());
		assertSame(ELEMENT_0_ELEMENT_0, this.model.get(0).get(0));
	}

	@Test
	public void testExtractUnknown() {
		Context context = Context.of(PropertyIndex.of(ListModelProperties.ELEMENTLIST, 0));
		assertThrows(OutboundExtractableReferenceException.class, () -> {
			ListModelProperties.ELEMENTLIST.extract(this.model, 3, context);
		});
	}

	@Test
	public void testExtractInterrupted() {
		this.model.set(0, null);
		Context context = Context.of(PropertyIndex.of(ListModelProperties.ELEMENTLIST, 0));
		assertThrows(InterruptedPropertyPathException.class, () -> {
			ListModelProperties.ELEMENTLIST.extract(this.model, 0, context);
		});
	}

	@Test
	public void testExtractUnreferenced() {
		assertThrows(UnreferencedPropertyPathException.class, () -> {
			ListModelProperties.ELEMENTLIST.extract(this.model, 0);
		});
	}

	@Test
	public void testExtractOutbound() {
		Context context = Context.of(PropertyIndex.of(ListModelProperties.ELEMENTLIST, 3));
		assertThrows(OutboundPropertyPathException.class, () -> {
			ListModelProperties.ELEMENTLIST.extract(this.model, 0, context);
		});
	}
}
