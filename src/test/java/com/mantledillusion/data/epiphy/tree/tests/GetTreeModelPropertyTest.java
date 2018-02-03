package com.mantledillusion.data.epiphy.tree.tests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.mantledillusion.data.epiphy.exception.InterruptedPropertyPathException;
import com.mantledillusion.data.epiphy.tree.AbstractTreeModelPropertyTest;
import com.mantledillusion.data.epiphy.tree.TreeModelProperties;

public class GetTreeModelPropertyTest extends AbstractTreeModelPropertyTest {

	@Test
	public void testGetProperty() {
		assertEquals("model", TreeModelProperties.MODELID.get(this.model));
	}

	@Test(expected=InterruptedPropertyPathException.class)
	public void testGetPropertyIntermediateNull() {
		this.model.sub.subSub = null;
		TreeModelProperties.SUBSUBID.get(this.model);
	}
}
