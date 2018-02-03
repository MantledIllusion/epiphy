package com.mantledillusion.data.epiphy.tree.tests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.mantledillusion.data.epiphy.exception.InterruptedPropertyPathException;
import com.mantledillusion.data.epiphy.tree.AbstractTreeModelPropertyTest;
import com.mantledillusion.data.epiphy.tree.TreeModelProperties;

public class SetTreeModelPropertyTest extends AbstractTreeModelPropertyTest {

	@Test
	public void testSetProperty() {
		TreeModelProperties.MODELID.set(this.model, "reset");
		assertEquals("reset", TreeModelProperties.MODELID.get(this.model));
	}

	@Test(expected=InterruptedPropertyPathException.class)
	public void testSetPropertyIntermediateNull() {
		this.model.sub.subSub = null;
		TreeModelProperties.SUBSUBID.set(this.model, "newId");
	}
}
