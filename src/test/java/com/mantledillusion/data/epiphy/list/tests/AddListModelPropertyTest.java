package com.mantledillusion.data.epiphy.list.tests;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.mantledillusion.data.epiphy.context.PropertyIndex;
import com.mantledillusion.data.epiphy.context.impl.DefaultContext;
import com.mantledillusion.data.epiphy.exception.InterruptedPropertyPathException;
import com.mantledillusion.data.epiphy.list.AbstractListModelPropertyTest;
import com.mantledillusion.data.epiphy.list.ListModelProperties;

public class AddListModelPropertyTest extends AbstractListModelPropertyTest {

	@Test
	public void testAddIndexedPropertyAtEnd() {
		String newElem = "newElem";
		DefaultContext context = DefaultContext.of(PropertyIndex.of(ListModelProperties.MODEL, 0));
		ListModelProperties.ELEMENTLIST.append(this.model, newElem, context);
		assertSame(newElem, this.model.get(0).get(2));
	}

	@Test
	public void testAddIndexedPropertyAtIndex() {
		String newElem = "newElem";
		DefaultContext context = DefaultContext.of(PropertyIndex.of(ListModelProperties.MODEL, 0),
				PropertyIndex.of(ListModelProperties.ELEMENTLIST, 0));
		ListModelProperties.ELEMENTLIST.addAt(this.model, newElem, context);
		assertSame(newElem, this.model.get(0).get(0));
	}

	@Test
	public void testAddIndexedPropertyIntermediateNull() {
		this.model.set(0, null);
		DefaultContext context = DefaultContext.of(PropertyIndex.of(ListModelProperties.MODEL, 0),
				PropertyIndex.of(ListModelProperties.ELEMENTLIST, 0));
		assertThrows(InterruptedPropertyPathException.class, () -> {
			ListModelProperties.ELEMENTLIST.addAt(this.model, "newElem", context);
		});
	}
}
