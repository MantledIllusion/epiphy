package com.mantledillusion.data.epiphy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashSet;

import org.junit.Test;

import com.mantledillusion.data.epiphy.mixed.MixedModelProperties;

public class BasicModelPropertyFunctionTest {
	
	@Test
	public void testPropertySpec() {
		assertTrue(MixedModelProperties.MODEL.isRoot());
		assertFalse(MixedModelProperties.SUB.isRoot());
		
		assertTrue(MixedModelProperties.SUBLIST.isList());
		assertFalse(MixedModelProperties.SUB.isList());
		
		assertTrue(MixedModelProperties.SUB.isListed());
		assertFalse(MixedModelProperties.MODELID.isListed());

		assertTrue(MixedModelProperties.MODEL.getParent() == null);
		assertTrue(MixedModelProperties.SUBLIST.getParent() == MixedModelProperties.MODEL);

		assertTrue(MixedModelProperties.MODEL.getAllChildren().equals(new HashSet<>(Arrays.asList(
				MixedModelProperties.MODELID,
				MixedModelProperties.SUBLIST,
				MixedModelProperties.SUB,
				MixedModelProperties.SUBID))));
		
		assertTrue(MixedModelProperties.SUBID.getPath().equals(Arrays.asList(
				MixedModelProperties.MODEL,
				MixedModelProperties.SUBLIST,
				MixedModelProperties.SUB,
				MixedModelProperties.SUBID)));
		
		assertTrue(MixedModelProperties.SUBID.getContext().equals(new HashSet<>(Arrays.asList(
				MixedModelProperties.MODEL,
				MixedModelProperties.SUBLIST,
				MixedModelProperties.SUB,
				MixedModelProperties.SUBID))));
		
		assertTrue(MixedModelProperties.SUBID.getIndices().equals(new HashSet<>(Arrays.asList(
				MixedModelProperties.SUBLIST))));
	}
	
	@Test
	public void testId() {
		assertEquals("model.subList.sub.subId", MixedModelProperties.SUBID.toString());
	}
}
