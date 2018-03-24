package com.mantledillusion.data.epiphy.mixed.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import com.mantledillusion.data.epiphy.mixed.AbstractMixedModelPropertyTest;
import com.mantledillusion.data.epiphy.mixed.MixedModelProperties;

public class NameMixedModelPropertyTest extends AbstractMixedModelPropertyTest {

	private static final String SUBID_NAME = MixedModelProperties.ID_MODEL + '.' + MixedModelProperties.ID_SUBLIST + '.'
			+ MixedModelProperties.ID_SUB + '.' + MixedModelProperties.ID_SUBID;
	
	@Test
	public void testId() {
		assertEquals(MixedModelProperties.ID_SUBID, MixedModelProperties.SUBID.getId());
	}

	@Test
	public void testName() {
		assertEquals(SUBID_NAME, MixedModelProperties.SUBID.getName());
	}
	
	@Test
	public void testGetChild() {
		assertSame(MixedModelProperties.SUBID, MixedModelProperties.MODEL.getChild(SUBID_NAME));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testGetChildForInvalidPath() {
		MixedModelProperties.MODEL.getChild("model modelId");
	}
	
	@Test
	public void testChildPaths() {
		Set<String> paths = new HashSet<>(Arrays.asList(MixedModelProperties.ID_SUB + '.' + MixedModelProperties.ID_SUBID));
		
		assertEquals(paths, MixedModelProperties.SUB.getAllChildPaths());
	}
	
	@Test
	public void testPathChild() {
		assertEquals(SUBID_NAME, MixedModelProperties.MODEL.getChildPath(MixedModelProperties.SUBID));
	}
}