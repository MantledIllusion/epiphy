package com.mantledillusion.data.epiphy.tree.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.mantledillusion.data.epiphy.exception.InterruptedPropertyPathException;
import com.mantledillusion.data.epiphy.tree.AbstractTreeModelPropertyTest;
import com.mantledillusion.data.epiphy.tree.TreeModelProperties;

public class GetTreeModelPropertyTest extends AbstractTreeModelPropertyTest {

	@Test
	public void testGetProperty() {
		assertEquals(this.model.modelId, TreeModelProperties.MODELID.get(this.model));
	}

	@Test
	public void testGetPropertyIntermediateNull() {
		this.model.sub.subSub = null;
		assertThrows(InterruptedPropertyPathException.class, () -> {
			TreeModelProperties.SUBSUBID.get(this.model);
		});
	}

	@Test
	public void testGetPropertyAllowIntermediateNull() {
		this.model.sub.subSub = null;
		assertEquals(null, TreeModelProperties.SUBSUBID.get(this.model, true));
	}
}