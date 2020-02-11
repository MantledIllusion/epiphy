package com.mantledillusion.data.epiphy.map.test;

import com.mantledillusion.data.epiphy.context.Context;
import com.mantledillusion.data.epiphy.context.reference.PropertyKey;
import com.mantledillusion.data.epiphy.exception.InterruptedPropertyPathException;
import com.mantledillusion.data.epiphy.exception.OutboundPropertyPathException;
import com.mantledillusion.data.epiphy.exception.UnreferencedPropertyPathException;
import com.mantledillusion.data.epiphy.map.AbstractMapModelPropertyTest;
import com.mantledillusion.data.epiphy.map.MapModelProperties;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SetMapModelPropertyTest extends AbstractMapModelPropertyTest {

	@Test
	public void testSet() {
		Context context = Context.of(PropertyKey.of(MapModelProperties.ELEMENTMAP, "A"),
				PropertyKey.of(MapModelProperties.ELEMENT, "A"));
		MapModelProperties.ELEMENTMAP_TO_ELEMENT.set(this.model, NEW_ELEMENT, context);
		assertSame(NEW_ELEMENT, MapModelProperties.ELEMENTMAP_TO_ELEMENT.get(this.model, context));
	}

	@Test
	public void testSetInterrupted() {
		this.model.put("A", null);
		Context context = Context.of(PropertyKey.of(MapModelProperties.ELEMENTMAP, "A"),
				PropertyKey.of(MapModelProperties.ELEMENT, "A"));
		assertThrows(InterruptedPropertyPathException.class, () -> {
			MapModelProperties.ELEMENTMAP_TO_ELEMENT.set(this.model, NEW_ELEMENT, context);
		});
	}

	@Test
	public void testSetUnreferenced() {
		assertThrows(UnreferencedPropertyPathException.class, () -> {
			MapModelProperties.ELEMENTMAP_TO_ELEMENT.set(this.model, NEW_ELEMENT);
		});
	}

	@Test
	public void testSetOutbound() {
		Context context = Context.of(PropertyKey.of(MapModelProperties.ELEMENTMAP, "A"),
				PropertyKey.of(MapModelProperties.ELEMENT, "C"));
		assertThrows(OutboundPropertyPathException.class, () -> {
			MapModelProperties.ELEMENTMAP_TO_ELEMENT.set(this.model, NEW_ELEMENT, context);
		});
	}
}
