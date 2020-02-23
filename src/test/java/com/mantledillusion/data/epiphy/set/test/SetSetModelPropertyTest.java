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

public class SetSetModelPropertyTest extends AbstractSetModelPropertyTest {

	@Test
	public void testSet() {
		Context context = Context.of(PropertyKey.ofSet(SetModelProperties.ELEMENT, ELEMENT_A));
		SetModelProperties.ELEMENTSET_TO_ELEMENT.set(this.model, NEW_ELEMENT, context);
		context = Context.of(PropertyKey.ofSet(SetModelProperties.ELEMENT, NEW_ELEMENT));
		assertSame(NEW_ELEMENT, SetModelProperties.ELEMENTSET_TO_ELEMENT.get(this.model, context));
	}

	@Test
	public void testSetInterrupted() {
		this.model.setObjects(null);
		Context context = Context.of(PropertyKey.ofSet(SetModelProperties.ELEMENT, ELEMENT_A));
		assertThrows(InterruptedPropertyPathException.class, () -> {
			SetModelProperties.ELEMENTSET_TO_ELEMENT.set(this.model, NEW_ELEMENT, context);
		});
	}

	@Test
	public void testSetUnreferenced() {
		assertThrows(UnreferencedPropertyPathException.class, () -> {
			SetModelProperties.ELEMENTSET_TO_ELEMENT.set(this.model, NEW_ELEMENT);
		});
	}

	@Test
	public void testSetOutbound() {
		Context context = Context.of(PropertyKey.ofSet(SetModelProperties.ELEMENT, NEW_ELEMENT));
		assertThrows(OutboundPropertyPathException.class, () -> {
			SetModelProperties.ELEMENTSET_TO_ELEMENT.set(this.model, NEW_ELEMENT, context);
		});
	}
}
