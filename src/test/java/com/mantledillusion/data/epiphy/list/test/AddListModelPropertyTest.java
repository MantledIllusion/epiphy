package com.mantledillusion.data.epiphy.list.test;

import com.mantledillusion.data.epiphy.context.Context;
import com.mantledillusion.data.epiphy.context.reference.PropertyIndex;
import com.mantledillusion.data.epiphy.exception.InterruptedPropertyPathException;
import com.mantledillusion.data.epiphy.list.AbstractListModelPropertyTest;
import com.mantledillusion.data.epiphy.list.ListModelProperties;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AddListModelPropertyTest extends AbstractListModelPropertyTest {

	@Test
	public void testAddIndexedPropertyAtEnd() {
		String newElem = "newElem";
		Context context = Context.of(PropertyIndex.of(ListModelProperties.ELEMENTLIST, 0));
		ListModelProperties.ELEMENTLIST.include(this.model, newElem, context);
		assertSame(newElem, this.model.get(0).get(2));
	}

	@Test
	public void testAddIndexedPropertyAtIndex() {
		String newElem = "newElem";
		Context context = Context.of(PropertyIndex.of(ListModelProperties.ELEMENTLIST, 0));
		ListModelProperties.ELEMENTLIST.insert(this.model, newElem, 0, context);
		assertSame(newElem, this.model.get(0).get(0));
	}

	@Test
	public void testAddIndexedPropertyIntermediateNull() {
		this.model.set(0, null);
		Context context = Context.of(PropertyIndex.of(ListModelProperties.ELEMENTLIST, 0));
		assertThrows(InterruptedPropertyPathException.class, () -> {
			ListModelProperties.ELEMENTLIST.insert(this.model, "newElem", 0, context);
		});
	}
}
