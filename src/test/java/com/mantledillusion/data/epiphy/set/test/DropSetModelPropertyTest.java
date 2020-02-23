package com.mantledillusion.data.epiphy.set.test;

import com.mantledillusion.data.epiphy.exception.InterruptedPropertyPathException;
import com.mantledillusion.data.epiphy.exception.UnknownDropableElementException;
import com.mantledillusion.data.epiphy.set.AbstractSetModelPropertyTest;
import com.mantledillusion.data.epiphy.set.SetModelProperties;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DropSetModelPropertyTest extends AbstractSetModelPropertyTest {

	@Test
	public void testDrop() {
		assertEquals(ELEMENT_A, SetModelProperties.ELEMENTSET.drop(this.model, ELEMENT_A));
		assertEquals(1, this.model.getObjects().size());
		assertSame(ELEMENT_B, this.model.getObjects().iterator().next());
	}

	@Test
	public void testDropUnknown() {
		assertThrows(UnknownDropableElementException.class, () -> {
			SetModelProperties.ELEMENTSET.drop(this.model, NEW_ELEMENT);
		});
	}

	@Test
	public void testDropInterrupted() {
		this.model.setObjects(null);
		assertThrows(InterruptedPropertyPathException.class, () -> {
			SetModelProperties.ELEMENTSET.drop(this.model, ELEMENT_A);
		});
	}
}
