package com.mantledillusion.data.epiphy.tree.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.mantledillusion.data.epiphy.exception.InterruptedPropertyPathException;
import com.mantledillusion.data.epiphy.tree.AbstractTreeModelPropertyTest;
import com.mantledillusion.data.epiphy.tree.TreeModelProperties;

public class SetTreeModelPropertyTest extends AbstractTreeModelPropertyTest {

	@Test
	public void testSetProperty() {
		TreeModelProperties.MODELID.set(this.model, "reset");
		assertEquals(this.model.modelId, TreeModelProperties.MODELID.get(this.model));
	}

	@Test
	public void testSetPropertyIntermediateNull() {
		this.model.sub.subSub = null;
		assertThrows(InterruptedPropertyPathException.class, () -> {
			TreeModelProperties.SUBSUBID.set(this.model, "newId");
		});
	}
}
