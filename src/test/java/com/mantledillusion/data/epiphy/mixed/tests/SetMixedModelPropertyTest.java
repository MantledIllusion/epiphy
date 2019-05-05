package com.mantledillusion.data.epiphy.mixed.tests;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.mantledillusion.data.epiphy.context.PropertyIndex;
import com.mantledillusion.data.epiphy.context.PropertyRoute;
import com.mantledillusion.data.epiphy.context.impl.DefaultContext;
import com.mantledillusion.data.epiphy.exception.OutboundPropertyPathException;
import com.mantledillusion.data.epiphy.exception.UncontextedPropertyPathException;
import com.mantledillusion.data.epiphy.mixed.AbstractMixedModelPropertyTest;
import com.mantledillusion.data.epiphy.mixed.MixedModelProperties;

public class SetMixedModelPropertyTest extends AbstractMixedModelPropertyTest {

	@Test
	public void testSetIndexedProperty() {
		String newSubId = "newId";
		DefaultContext context = DefaultContext.of(
				PropertyIndex.of(MixedModelProperties.SUBLIST, 1),
				PropertyRoute.of(MixedModelProperties.SUB, 1, 0));
		MixedModelProperties.SUBID.set(this.model, newSubId, context);
		assertSame(newSubId, this.model.subList.get(1).leaves.get(1).leaves.get(0).subId);
	}

	@Test
	public void testSetIndexedPropertyWithoutListIndex() {
		DefaultContext context = DefaultContext.of(
				PropertyRoute.of(MixedModelProperties.SUB, 1, 0));
		assertThrows(UncontextedPropertyPathException.class, () -> {
			MixedModelProperties.SUBID.set(this.model, "newId", context);
		});
	}

	@Test
	public void testSetIndexedPropertyWithoutNodeIndex() {
		String newId = "newId";
		DefaultContext context = DefaultContext.of(
				PropertyIndex.of(MixedModelProperties.SUBLIST, 1));
		MixedModelProperties.SUBID.set(this.model, newId, context);
		assertSame(newId, this.model.subList.get(1).subId);
	}
	
	@Test
	public void testSetIndexPropertyWithOutOfBoundListIndex() {
		String newId = "newId";
		DefaultContext context = DefaultContext.of(
				PropertyIndex.of(MixedModelProperties.SUBLIST, -1),
				PropertyRoute.of(MixedModelProperties.SUB, 1, 0));
		assertThrows(OutboundPropertyPathException.class, () -> {
			MixedModelProperties.SUBID.set(this.model, newId, context);
		});
	}
	
	@Test
	public void testSetIndexPropertyWithOutOfBoundNodeIndex() {
		String newId = "newId";
		DefaultContext context = DefaultContext.of(
				PropertyIndex.of(MixedModelProperties.SUBLIST, 1),
				PropertyRoute.of(MixedModelProperties.SUB, 2, 0));
		assertThrows(OutboundPropertyPathException.class, () -> {
			MixedModelProperties.SUBID.set(this.model, newId, context);
		});
	}
}
