package com.mantledillusion.data.epiphy.node.tests;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.mantledillusion.data.epiphy.context.PropertyRoute;
import com.mantledillusion.data.epiphy.context.impl.DefaultContext;
import com.mantledillusion.data.epiphy.exception.InterruptedPropertyPathException;
import com.mantledillusion.data.epiphy.exception.OutboundPropertyPathException;
import com.mantledillusion.data.epiphy.node.AbstractNodeModelPropertyTest;
import com.mantledillusion.data.epiphy.node.NodeModelProperties;

public class GetNodeModelPropertyTest extends AbstractNodeModelPropertyTest {

	@Test
	public void testGetNodedProperty() {
		DefaultContext context = DefaultContext.of(PropertyRoute.of(NodeModelProperties.NODE, 1, 0));
		assertSame(this.model.rootNode.leaves.get(1).leaves.get(0), NodeModelProperties.NODE.get(this.model, context));
	}

	@Test
	public void testGetNodedPropertyIntermediateNull() {
		this.model.rootNode.leaves.add(null);
		DefaultContext context = DefaultContext.of(PropertyRoute.of(NodeModelProperties.NODE, 2, 0));
		assertThrows(InterruptedPropertyPathException.class, () -> {
			NodeModelProperties.NODE.get(this.model, context);
		});
	}

	@Test
	public void testGetNodedPropertyOutOfBounds() {
		DefaultContext context = DefaultContext.of(PropertyRoute.of(NodeModelProperties.NODE, 2, 0));
		assertThrows(OutboundPropertyPathException.class, () -> {
			NodeModelProperties.NODE.get(this.model, context);
		});
	}
}
