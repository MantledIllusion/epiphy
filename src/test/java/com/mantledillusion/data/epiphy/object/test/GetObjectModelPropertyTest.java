package com.mantledillusion.data.epiphy.object.test;

import com.mantledillusion.data.epiphy.exception.InterruptedPropertyPathException;
import com.mantledillusion.data.epiphy.object.AbstractObjectModelPropertyTest;
import com.mantledillusion.data.epiphy.object.ObjectModelProperties;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class GetObjectModelPropertyTest extends AbstractObjectModelPropertyTest {

	@Test
	public void testGetProperty() {
		assertEquals(this.model.modelId, ObjectModelProperties.MODELID.get(this.model));
	}

	@Test
	public void testGetPropertyIntermediateNull() {
		this.model.sub.subSub = null;
		assertThrows(InterruptedPropertyPathException.class, () -> {
			ObjectModelProperties.MODEL_TO_SUBSUBID.get(this.model);
		});
	}

	@Test
	public void testGetPropertyAllowIntermediateNull() {
		this.model.sub.subSub = null;
		assertEquals(null, ObjectModelProperties.MODEL_TO_SUBSUBID.get(this.model, true));
	}
}