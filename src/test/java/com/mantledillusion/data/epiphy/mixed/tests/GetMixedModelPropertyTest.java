package com.mantledillusion.data.epiphy.mixed.tests;

import static org.junit.Assert.assertSame;

import org.junit.Test;

import com.mantledillusion.data.epiphy.context.PropertyIndex;
import com.mantledillusion.data.epiphy.context.PropertyRoute;
import com.mantledillusion.data.epiphy.context.impl.DefaultContext;
import com.mantledillusion.data.epiphy.exception.OutboundPropertyPathException;
import com.mantledillusion.data.epiphy.exception.UncontextedPropertyPathException;
import com.mantledillusion.data.epiphy.mixed.AbstractMixedModelPropertyTest;
import com.mantledillusion.data.epiphy.mixed.MixedModelProperties;

public class GetMixedModelPropertyTest extends AbstractMixedModelPropertyTest {
	
	@Test
	public void testGetIndexedProperty() {
		DefaultContext context = DefaultContext.of(
				PropertyIndex.of(MixedModelProperties.SUBLIST, 1),
				PropertyRoute.of(MixedModelProperties.SUB, 1, 0));
		assertSame(ELEMENT_1_NODE_1_NODE_0, MixedModelProperties.SUBID.get(this.model, context));
	}

	@Test(expected=UncontextedPropertyPathException.class)
	public void testGetIndexedPropertyWithoutListIndex() {
		DefaultContext context = DefaultContext.of(
				PropertyRoute.of(MixedModelProperties.SUB, 1, 0));
		MixedModelProperties.SUBID.get(this.model, context);
	}
	
	@Test
	public void testGetIndexedPropertyWithoutNodeIndex() {
		DefaultContext context = DefaultContext.of(
				PropertyIndex.of(MixedModelProperties.SUBLIST, 1));
		assertSame(ELEMENT_1, MixedModelProperties.SUBID.get(this.model, context));
	}
	
	@Test(expected=OutboundPropertyPathException.class)
	public void testGetIndexPropertyWithOutOfBoundListIndex() {
		DefaultContext context = DefaultContext.of(
				PropertyIndex.of(MixedModelProperties.SUBLIST, -1),
				PropertyRoute.of(MixedModelProperties.SUB, 1, 0));
		MixedModelProperties.SUBID.get(this.model, context);
	}
	
	@Test(expected=OutboundPropertyPathException.class)
	public void testGetIndexPropertyWithOutOfBoundNodeIndex() {
		DefaultContext context = DefaultContext.of(
				PropertyIndex.of(MixedModelProperties.SUBLIST, 1),
				PropertyRoute.of(MixedModelProperties.SUB, 2, 0));
		MixedModelProperties.SUBID.get(this.model, context);
	}
}
