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
import com.mantledillusion.data.epiphy.mixed.model.MixedSubType;

public class SetMixedModelPropertyTest extends AbstractMixedModelPropertyTest {

	@Test
	public void testSetListedProperty() {
		MixedSubType newSub = new MixedSubType();
		newSub.subId = "newSub";
		DefaultIndexContext context = DefaultIndexContext.of(
				PropertyIndex.of(MixedModelProperties.SUBLIST, 0));
		MixedModelProperties.SUB.set(this.model, newSub, context);
		assertEquals("newSub", MixedModelProperties.SUB.get(this.model, context).subId);
	}
	
	@Test
	public void testSetIndexedProperties() {
		for (Integer subIndex: Arrays.asList(0, 1)) {
			DefaultIndexContext context = DefaultIndexContext.of(
					PropertyIndex.of(MixedModelProperties.SUBLIST, subIndex));
			MixedModelProperties.SUBID.set(this.model, "changed"+subIndex, context);
		}
		
		for (Integer subIndex: Arrays.asList(0, 1)) {
			DefaultIndexContext context = DefaultIndexContext.of(
					PropertyIndex.of(MixedModelProperties.SUBLIST, subIndex));
			assertEquals("changed"+subIndex, MixedModelProperties.SUBID.get(this.model, context));
		}
	}

	@Test(expected=UnindexedPropertyPathException.class)
	public void testSetListedPropertyWithoutIndex() {
		MixedModelProperties.SUBID.set(this.model, "newId");
	}
	
	@Test(expected=OutboundPropertyPathException.class)
	public void testSetIndexedPropertyOutOfBound() {
		DefaultIndexContext context = DefaultIndexContext.of(
				PropertyIndex.of(MixedModelProperties.SUBLIST, 2));
		MixedModelProperties.SUBID.set(this.model, "newId", context);
	}
}
