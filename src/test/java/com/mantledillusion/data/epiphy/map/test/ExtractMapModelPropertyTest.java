package com.mantledillusion.data.epiphy.map.test;

import com.mantledillusion.data.epiphy.context.Context;
import com.mantledillusion.data.epiphy.context.reference.PropertyKey;
import com.mantledillusion.data.epiphy.exception.InterruptedPropertyPathException;
import com.mantledillusion.data.epiphy.exception.OutboundPropertyPathException;
import com.mantledillusion.data.epiphy.exception.UnreferencedPropertyPathException;
import com.mantledillusion.data.epiphy.map.AbstractMapModelPropertyTest;
import com.mantledillusion.data.epiphy.map.MapModelProperties;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ExtractMapModelPropertyTest extends AbstractMapModelPropertyTest {

	@Test
	public void testExtract() {
		Context context = Context.of(PropertyKey.ofMap(MapModelProperties.ELEMENTMAP, "A"));
		assertSame(ELEMENT_A_ELEMENT_B, MapModelProperties.ELEMENTMAP.extract(this.model, "B", context));
		assertEquals(1, this.model.get("A").size());
		assertSame(ELEMENT_A_ELEMENT_A, this.model.get("A").get("A"));
	}

	@Test
	public void testExtractInterrupted() {
		this.model.put("A", null);
		Context context = Context.of(PropertyKey.ofMap(MapModelProperties.ELEMENTMAP, "A"));
		assertThrows(InterruptedPropertyPathException.class, () -> {
			MapModelProperties.ELEMENTMAP.extract(this.model, "A", context);
		});
	}

	@Test
	public void testExtractUnreferenced() {
		assertThrows(UnreferencedPropertyPathException.class, () -> {
			MapModelProperties.ELEMENTMAP.extract(this.model, "A");
		});
	}

	@Test
	public void testExtractOutbound() {
		Context context = Context.of(PropertyKey.ofMap(MapModelProperties.ELEMENTMAP, "C"));
		assertThrows(OutboundPropertyPathException.class, () -> {
			MapModelProperties.ELEMENTMAP.extract(this.model, "A", context);
		});
	}
}
