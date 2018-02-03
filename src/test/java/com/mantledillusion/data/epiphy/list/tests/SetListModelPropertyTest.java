package com.mantledillusion.data.epiphy.list.tests;

import static org.junit.Assert.assertSame;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.mantledillusion.data.epiphy.exception.InterruptedPropertyPathException;
import com.mantledillusion.data.epiphy.index.PropertyIndex;
import com.mantledillusion.data.epiphy.index.impl.DefaultIndexContext;
import com.mantledillusion.data.epiphy.list.AbstractListModelPropertyTest;
import com.mantledillusion.data.epiphy.list.ListModelProperties;

public class SetListModelPropertyTest extends AbstractListModelPropertyTest {

	@Test
	public void testSetProperty() {
		List<String> newElementList = Arrays.asList();
		DefaultIndexContext context = DefaultIndexContext.of(PropertyIndex.of(ListModelProperties.MODEL, 0),
				PropertyIndex.of(ListModelProperties.ELEMENTLIST, 0));
		ListModelProperties.ELEMENTLIST.set(this.model, newElementList, context);
		assertSame(newElementList, ListModelProperties.ELEMENTLIST.get(this.model, context));
	}

	@Test(expected = InterruptedPropertyPathException.class)
	public void testSetPropertyIntermediateNull() {
		this.model.set(0, null);
		DefaultIndexContext context = DefaultIndexContext.of(PropertyIndex.of(ListModelProperties.MODEL, 0),
				PropertyIndex.of(ListModelProperties.ELEMENTLIST, 0));
		ListModelProperties.ELEMENT.set(this.model, "newElem", context);
	}
}
