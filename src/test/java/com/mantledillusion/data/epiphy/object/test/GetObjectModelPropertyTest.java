package com.mantledillusion.data.epiphy.object.test;

import com.mantledillusion.data.epiphy.exception.InterruptedPropertyPathException;
import com.mantledillusion.data.epiphy.object.AbstractObjectModelPropertyTest;
import com.mantledillusion.data.epiphy.object.ObjectModelProperties;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class GetObjectModelPropertyTest extends AbstractObjectModelPropertyTest {

	@Test
	public void testGet() {
		assertEquals(this.model.getModelId(), ObjectModelProperties.MODELID.get(this.model));
	}

	@Test
	public void testGetInterrupted() {
		this.model.getSub().setSubSub(null);
		assertThrows(InterruptedPropertyPathException.class, () -> {
			ObjectModelProperties.MODEL_TO_SUBSUBID.get(this.model);
		});
	}

	@Test
	public void testGetAllowInterrupted() {
		this.model.getSub().setSubSub(null);
		assertEquals(null, ObjectModelProperties.MODEL_TO_SUBSUBID.get(this.model, true));
	}
}