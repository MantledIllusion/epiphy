package com.mantledillusion.data.epiphy.set.test;

import com.mantledillusion.data.epiphy.context.Context;
import com.mantledillusion.data.epiphy.context.reference.PropertyKey;
import com.mantledillusion.data.epiphy.exception.InterruptedPropertyPathException;
import com.mantledillusion.data.epiphy.exception.OutboundPropertyPathException;
import com.mantledillusion.data.epiphy.exception.UnreferencedPropertyPathException;
import com.mantledillusion.data.epiphy.set.AbstractSetModelPropertyTest;
import com.mantledillusion.data.epiphy.set.SetModelProperties;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class GetSetModelPropertyTest extends AbstractSetModelPropertyTest {

	@Test
	public void testGet() {
		Context context = Context.of(PropertyKey.ofSet(SetModelProperties.ELEMENT, ELEMENT_A));
		assertSame(ELEMENT_A, SetModelProperties.ELEMENTSET_TO_ELEMENT.get(this.model, context));
	}

	@Test
	public void testGetInterrupted() {
		this.model.setObjects(null);
		Context context = Context.of(PropertyKey.ofSet(SetModelProperties.ELEMENT, ELEMENT_B));
		assertThrows(InterruptedPropertyPathException.class, () -> {
			SetModelProperties.ELEMENTSET_TO_ELEMENT.get(this.model, context);
		});
	}

	@Test
	public void testGetUnreferenced() {
		assertThrows(UnreferencedPropertyPathException.class, () -> {
			SetModelProperties.ELEMENTSET_TO_ELEMENT.get(this.model);
		});
	}

	@Test
	public void testGetOutbound() {
		Context context = Context.of(PropertyKey.ofSet(SetModelProperties.ELEMENT, NEW_ELEMENT));
		assertThrows(OutboundPropertyPathException.class, () -> {
			SetModelProperties.ELEMENTSET_TO_ELEMENT.get(this.model, context);
		});
	}
}
