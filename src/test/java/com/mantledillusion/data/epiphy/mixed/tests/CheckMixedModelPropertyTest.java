package com.mantledillusion.data.epiphy.mixed.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.mantledillusion.data.epiphy.index.IndexContext;
import com.mantledillusion.data.epiphy.index.PropertyIndex;
import com.mantledillusion.data.epiphy.index.impl.DefaultIndexContext;
import com.mantledillusion.data.epiphy.mixed.AbstractMixedModelPropertyTest;
import com.mantledillusion.data.epiphy.mixed.MixedModelProperties;

public class CheckMixedModelPropertyTest extends AbstractMixedModelPropertyTest {
	
	@Test
	public void testIsNull() {
		IndexContext context = DefaultIndexContext.of(PropertyIndex.of(MixedModelProperties.SUBLIST, 0));
		
		assertFalse(MixedModelProperties.SUBID.isNull(this.model, context));
		this.model.subList.get(0).subId = null;
		assertTrue(MixedModelProperties.SUBID.isNull(this.model, context));
		this.model.subList.set(0, null);
		assertTrue(MixedModelProperties.SUBID.isNull(this.model, context));
	}
	
	@Test
	public void testIsExists() {
		IndexContext contextIdx0 = DefaultIndexContext.of(PropertyIndex.of(MixedModelProperties.SUBLIST, 0));
		IndexContext contextIdx2 = DefaultIndexContext.of(PropertyIndex.of(MixedModelProperties.SUBLIST, 2));
		
		assertTrue(MixedModelProperties.SUBID.exists(this.model, contextIdx0));
		this.model.subList.get(0).subId = null;
		assertTrue(MixedModelProperties.SUBID.exists(this.model, contextIdx0));
		assertFalse(MixedModelProperties.SUBID.exists(this.model, contextIdx2));
		this.model.subList.set(0, null);
		assertFalse(MixedModelProperties.SUBID.exists(this.model, contextIdx0));
	}
}
