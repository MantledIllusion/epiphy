package com.mantledillusion.data.epiphy.object.test;

import com.mantledillusion.data.epiphy.exception.InterruptedPropertyPathException;
import com.mantledillusion.data.epiphy.object.AbstractObjectModelPropertyTest;
import com.mantledillusion.data.epiphy.object.ObjectModelProperties;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SetObjectModelPropertyTest extends AbstractObjectModelPropertyTest {

	@Test
	public void testSet() {
		ObjectModelProperties.MODELID.set(this.model, "reset");
		assertEquals(this.model.modelId, ObjectModelProperties.MODELID.get(this.model));
	}

	@Test
	public void testSetInterrupted() {
		this.model.sub.subSub = null;
		assertThrows(InterruptedPropertyPathException.class, () -> {
			ObjectModelProperties.MODEL_TO_SUBSUBID.set(this.model, "newId");
		});
	}
}
