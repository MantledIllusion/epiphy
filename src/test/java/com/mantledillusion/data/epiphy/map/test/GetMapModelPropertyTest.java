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

public class GetMapModelPropertyTest extends AbstractMapModelPropertyTest {

	@Test
	public void testGet() {
		Context context = Context.of(PropertyKey.of(MapModelProperties.ELEMENTMAP, "A"),
				PropertyKey.of(MapModelProperties.ELEMENT, "A"));
		assertSame(this.model.get("A").get("A"), MapModelProperties.ELEMENTMAP_TO_ELEMENT.get(this.model, context));
	}

	@Test
	public void testGetInterrupted() {
		this.model.put("A", null);
		Context context = Context.of(PropertyKey.of(MapModelProperties.ELEMENTMAP, "A"),
				PropertyKey.of(MapModelProperties.ELEMENT, "B"));
		assertThrows(InterruptedPropertyPathException.class, () -> {
			MapModelProperties.ELEMENTMAP_TO_ELEMENT.get(this.model, context);
		});
	}

	@Test
	public void testGetUnreferenced() {
		assertThrows(UnreferencedPropertyPathException.class, () -> {
			MapModelProperties.ELEMENTMAP_TO_ELEMENT.get(this.model);
		});
	}

	@Test
	public void testGetOutbound() {
		Context context = Context.of(PropertyKey.of(MapModelProperties.ELEMENTMAP, "A"),
				PropertyKey.of(MapModelProperties.ELEMENT, "C"));
		assertThrows(OutboundPropertyPathException.class, () -> {
			MapModelProperties.ELEMENTMAP_TO_ELEMENT.get(this.model, context);
		});
	}
}
