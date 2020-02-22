package com.mantledillusion.data.epiphy.map.test;

import com.mantledillusion.data.epiphy.context.Context;
import com.mantledillusion.data.epiphy.context.reference.PropertyKey;
import com.mantledillusion.data.epiphy.exception.InterruptedPropertyPathException;
import com.mantledillusion.data.epiphy.exception.OutboundPropertyPathException;
import com.mantledillusion.data.epiphy.exception.UnknownDropableElementException;
import com.mantledillusion.data.epiphy.exception.UnreferencedPropertyPathException;
import com.mantledillusion.data.epiphy.map.AbstractMapModelPropertyTest;
import com.mantledillusion.data.epiphy.map.MapModelProperties;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DropMapModelPropertyTest extends AbstractMapModelPropertyTest {

	@Test
	public void testDrop() {
		Context context = Context.of(PropertyKey.of(MapModelProperties.ELEMENTMAP, "A"));
		assertEquals("A", MapModelProperties.ELEMENTMAP.drop(this.model, ELEMENT_A_ELEMENT_A, context));
		assertEquals(1, this.model.get("A").size());
		assertSame(ELEMENT_A_ELEMENT_B, this.model.get("A").values().iterator().next());
	}

	@Test
	public void testDropInterrupted() {
		this.model.put("A", null);
		Context context = Context.of(PropertyKey.of(MapModelProperties.ELEMENTMAP, "A"));
		assertThrows(InterruptedPropertyPathException.class, () -> {
			MapModelProperties.ELEMENTMAP.drop(this.model, ELEMENT_A_ELEMENT_B, context);
		});
	}

	@Test
	public void testDropUnreferenced() {
		assertThrows(UnreferencedPropertyPathException.class, () -> {
			MapModelProperties.ELEMENTMAP.drop(this.model, ELEMENT_A_ELEMENT_B);
		});
	}

	@Test
	public void testDropOutbound() {
		Context context = Context.of(PropertyKey.of(MapModelProperties.ELEMENTMAP, "C"));
		assertThrows(OutboundPropertyPathException.class, () -> {
			MapModelProperties.ELEMENTMAP.drop(this.model, ELEMENT_A_ELEMENT_B, context);
		});
	}
}
