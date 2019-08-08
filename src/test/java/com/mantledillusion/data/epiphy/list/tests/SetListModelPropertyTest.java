package com.mantledillusion.data.epiphy.list.tests;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.mantledillusion.data.epiphy.context.PropertyIndex;
import com.mantledillusion.data.epiphy.context.impl.DefaultContext;
import com.mantledillusion.data.epiphy.exception.InterruptedPropertyPathException;
import com.mantledillusion.data.epiphy.exception.OutboundPropertyPathException;
import com.mantledillusion.data.epiphy.list.AbstractListModelPropertyTest;
import com.mantledillusion.data.epiphy.list.ListModelProperties;

public class SetListModelPropertyTest extends AbstractListModelPropertyTest {

	@Test
	public void testSetIndexedProperty() {
		List<String> newElementList = Arrays.asList();
		DefaultContext context = DefaultContext.of(PropertyIndex.of(ListModelProperties.MODEL, 0),
				PropertyIndex.of(ListModelProperties.ELEMENTLIST, 0));
		ListModelProperties.ELEMENTLIST.set(this.model, newElementList, context);
		assertSame(newElementList, ListModelProperties.ELEMENTLIST.get(this.model, context));
	}

	@Test
	public void testSetIndexedPropertyIntermediateNull() {
		this.model.set(0, null);
		DefaultContext context = DefaultContext.of(PropertyIndex.of(ListModelProperties.MODEL, 0),
				PropertyIndex.of(ListModelProperties.ELEMENTLIST, 0));
		assertThrows(InterruptedPropertyPathException.class, () -> {
			ListModelProperties.ELEMENT.set(this.model, "newElem", context);
		});
	}

	@Test
	public void testSetIndexedPropertyOutOfBounds() {
		DefaultContext context = DefaultContext.of(PropertyIndex.of(ListModelProperties.MODEL, 0),
				PropertyIndex.of(ListModelProperties.ELEMENTLIST, 2));
		assertThrows(OutboundPropertyPathException.class, () -> {
			ListModelProperties.ELEMENT.set(this.model, "newElem", context);
		});
	}
}
