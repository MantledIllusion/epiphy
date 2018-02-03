package com.mantledillusion.data.epiphy.mixed.tests;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Test;

import com.mantledillusion.data.epiphy.exception.OutboundPropertyPathException;
import com.mantledillusion.data.epiphy.exception.UnindexedPropertyPathException;
import com.mantledillusion.data.epiphy.index.PropertyIndex;
import com.mantledillusion.data.epiphy.index.impl.DefaultIndexContext;
import com.mantledillusion.data.epiphy.mixed.AbstractMixedModelPropertyTest;
import com.mantledillusion.data.epiphy.mixed.MixedModelProperties;

public class GetMixedModelPropertyTest extends AbstractMixedModelPropertyTest {
	
	@Test
	public void testGetListedProperty() {
		DefaultIndexContext context = DefaultIndexContext.of(
				PropertyIndex.of(MixedModelProperties.SUBLIST, 0));
		assertEquals("1", MixedModelProperties.SUB.get(this.model, context).subId);
	}
	
	@Test
	public void testGetIndexedProperties() {
		for (Integer subIndex: Arrays.asList(0, 1)) {
			DefaultIndexContext context = DefaultIndexContext.of(
					PropertyIndex.of(MixedModelProperties.SUBLIST, subIndex));
			assertEquals(String.valueOf(subIndex+1), MixedModelProperties.SUBID.get(this.model, context));
		}
	}

	@Test(expected=UnindexedPropertyPathException.class)
	public void testSetListedPropertyWithoutIndex() {
		MixedModelProperties.SUBID.get(this.model);
	}
	
	@Test(expected=OutboundPropertyPathException.class)
	public void testGetIndexedPropertyOutOfBound() {
		DefaultIndexContext context = DefaultIndexContext.of(
				PropertyIndex.of(MixedModelProperties.SUBLIST, -1));
		MixedModelProperties.SUBID.get(this.model, context);
	}
}
