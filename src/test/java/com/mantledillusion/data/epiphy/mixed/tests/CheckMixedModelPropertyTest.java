package com.mantledillusion.data.epiphy.mixed.tests;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.mantledillusion.data.epiphy.context.Context;
import com.mantledillusion.data.epiphy.context.PropertyIndex;
import com.mantledillusion.data.epiphy.context.impl.DefaultContext;
import com.mantledillusion.data.epiphy.mixed.AbstractMixedModelPropertyTest;
import com.mantledillusion.data.epiphy.mixed.MixedModelProperties;

public class CheckMixedModelPropertyTest extends AbstractMixedModelPropertyTest {
	
	@Test
	public void testIsNull() {
		Context context = DefaultContext.of(PropertyIndex.of(MixedModelProperties.SUBLIST, 0));
		
		assertFalse(MixedModelProperties.SUBID.isNull(this.model, context));
		this.model.subList.get(0).subId = null;
		assertTrue(MixedModelProperties.SUBID.isNull(this.model, context));
		this.model.subList.set(0, null);
		assertTrue(MixedModelProperties.SUBID.isNull(this.model, context));
	}
	
	@Test
	public void testExists() {
		Context contextIdx0 = DefaultContext.of(PropertyIndex.of(MixedModelProperties.SUBLIST, 0));
		Context contextIdx2 = DefaultContext.of(PropertyIndex.of(MixedModelProperties.SUBLIST, 2));
		
		assertTrue(MixedModelProperties.SUBID.exists(this.model, contextIdx0));
		this.model.subList.get(0).subId = null;
		assertTrue(MixedModelProperties.SUBID.exists(this.model, contextIdx0));
		assertFalse(MixedModelProperties.SUBID.exists(this.model, contextIdx2));
		this.model.subList.set(0, null);
		assertFalse(MixedModelProperties.SUBID.exists(this.model, contextIdx0));
	}
}
