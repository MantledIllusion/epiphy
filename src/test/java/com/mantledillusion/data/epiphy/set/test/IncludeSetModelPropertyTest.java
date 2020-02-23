package com.mantledillusion.data.epiphy.set.test;

import com.mantledillusion.data.epiphy.exception.InterruptedPropertyPathException;
import com.mantledillusion.data.epiphy.set.AbstractSetModelPropertyTest;
import com.mantledillusion.data.epiphy.set.SetModelProperties;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class IncludeSetModelPropertyTest extends AbstractSetModelPropertyTest {

	@Test
	public void testInclude() {
		assertEquals(NEW_ELEMENT, SetModelProperties.ELEMENTSET.include(this.model, NEW_ELEMENT));
		assertEquals(3, this.model.getObjects().size());
		assertTrue(this.model.getObjects().contains(NEW_ELEMENT));
	}

	@Test
	public void testIncludeInterrupted() {
		this.model.setObjects(null);
		assertThrows(InterruptedPropertyPathException.class, () -> {
			SetModelProperties.ELEMENTSET.include(this.model, NEW_ELEMENT);
		});
	}
}
