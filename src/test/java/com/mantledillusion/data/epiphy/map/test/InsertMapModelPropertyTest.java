package com.mantledillusion.data.epiphy.map.test;

import com.mantledillusion.data.epiphy.context.Context;
import com.mantledillusion.data.epiphy.context.reference.PropertyKey;
import com.mantledillusion.data.epiphy.exception.InterruptedPropertyPathException;
import com.mantledillusion.data.epiphy.exception.OutboundInsertableReferenceException;
import com.mantledillusion.data.epiphy.exception.OutboundPropertyPathException;
import com.mantledillusion.data.epiphy.exception.UnreferencedPropertyPathException;
import com.mantledillusion.data.epiphy.map.AbstractMapModelPropertyTest;
import com.mantledillusion.data.epiphy.map.MapModelProperties;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class InsertMapModelPropertyTest extends AbstractMapModelPropertyTest {

	@Test
	public void testInsert() {
		Context context = Context.of(PropertyKey.of(MapModelProperties.ELEMENTMAP, "A"));
		MapModelProperties.ELEMENTMAP.insert(this.model, NEW_ELEMENT, "C", context);
		assertEquals(3, this.model.get("A").size());
		assertSame(NEW_ELEMENT, this.model.get("A").get("C"));
	}

	@Test
	public void testInsertInterrupted() {
		this.model.put("A", null);
		Context context = Context.of(PropertyKey.of(MapModelProperties.ELEMENTMAP, "A"));
		assertThrows(InterruptedPropertyPathException.class, () -> {
			MapModelProperties.ELEMENTMAP.insert(this.model, NEW_ELEMENT, "A", context);
		});
	}

	@Test
	public void testInsertUnreferenced() {
		assertThrows(UnreferencedPropertyPathException.class, () -> {
			MapModelProperties.ELEMENTMAP.insert(this.model, NEW_ELEMENT, "A");
		});
	}

	@Test
	public void testInsertOutbound() {
		Context context = Context.of(PropertyKey.of(MapModelProperties.ELEMENTMAP, "C"));
		assertThrows(OutboundPropertyPathException.class, () -> {
			MapModelProperties.ELEMENTMAP.insert(this.model, NEW_ELEMENT, "A", context);
		});
	}
}
